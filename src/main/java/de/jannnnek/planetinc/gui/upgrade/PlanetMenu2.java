package de.jannnnek.planetinc.gui.upgrade;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.advancements.events.PlayerBuyPlanetEvent;
import de.jannnnek.planetinc.gui.ItemBuilder;
import de.jannnnek.planetinc.gui.NGUI;
import de.jannnnek.planetinc.planet.Planet;
import de.jannnnek.planetinc.util.PlanetUser;
import de.nbhd.nevadyapi.mysql.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static de.nbhd.nevadyapi.messages.Message.send;

/**
 * @author NbhdTV
 */
public class PlanetMenu2 extends NGUI implements Listener {

    static int[] buildingSlotsRank1 = {10,11,12,13,14,15,16};
    static int[] buildingSlotsRank2 = {19,20,21,22,23,24,25};
    static int[] buildingSlotsRank3 = {28,29,30,31,32,33,34,37,38,39,40,41,42,43};
    static int[][] buildingSlotList = {buildingSlotsRank1, buildingSlotsRank2, buildingSlotsRank3};
    int buildingNumber;

    public static String guiName = "§a§f\uE012\uE012\uE012\uE012\uE012\uE012\uE012\uE012\uE014";
    public static NGUI getGUI(Player p) {
        NGUI ngui = new NGUI().setSize(54).setDisplayName(guiName).cancelClicks().ownListener(new PlanetMenu2()).prepareInventory();
        ngui.getInventory().setItem(8, new ItemBuilder(Material.POPPED_CHORUS_FRUIT).setName("§cSchließen").setCustomModelData(9).build());
        int i = 28;
        for (int[] slotList : buildingSlotList) {
            for (int slot : slotList) {
                Planet planet = Arrays.stream(Planet.values()).toList().get(PlanetUser.users.get(p.getUniqueId()).getBuilding(i));
                ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT, (planet.equals(Planet.PLUTO) ? 1 : planet.getLevel())).setCustomModelData(planet.getCustomModelData()).setName(planet.getName()).setLore(planet.getLore()).build());
                i++;
            }
        }
        ngui.getInventory().setItem(49, new ItemBuilder(Material.POPPED_CHORUS_FRUIT).setCustomModelData(10).setName("§dZurück").build());
        return ngui;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().equals(guiName)) {
            Player p = (Player) e.getWhoClicked();
            if(!e.isShiftClick()) {
                int clickedSlot = e.getSlot();
                switch (clickedSlot) {
                    case 8:
                        p.closeInventory();
                        break;
                    case 49:
                        if (p.getCooldown(Material.DIAMOND_AXE) == 0) {
                            PlanetMenu.getGUI(p).open(p);
                            p.setCooldown(Material.DIAMOND_AXE, 10);
                        }
                        break;
                    default:
                        if (Arrays.stream(buildingSlotsRank1).filter(x -> x == clickedSlot).toArray().length >= 1) {
                            if (!RankManager.getRank(p.getUniqueId().toString()).equals("SPIELER")) {
                                buyPlanet(p, buildingSlotsRank1, clickedSlot);
                            }
                        } else if (Arrays.stream(buildingSlotsRank2).filter(x -> x == clickedSlot).toArray().length >= 1) {
                            if (!RankManager.getRank(p.getUniqueId().toString()).equals("SPIELER") && !RankManager.getRank(p.getUniqueId().toString()).equals("MOON")) {
                                buyPlanet(p, buildingSlotsRank2, clickedSlot);
                            } else {
                                send(p, "§7Du benötigst mindestens " + RankManager.Ranks.valueOf("STAR").prefix + " §7um diesen Planeten kaufen zu können.");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.5f, 1);
                                p.closeInventory();
                            }
                        } else if (Arrays.stream(buildingSlotsRank3).filter(x -> x == clickedSlot).toArray().length >= 1) {
                            if (!RankManager.getRank(p.getUniqueId().toString()).equals("SPIELER") && !RankManager.getRank(p.getUniqueId().toString()).equals("MOON") && !RankManager.getRank(p.getUniqueId().toString()).equals("STAR")) {
                                buyPlanet(p, buildingSlotsRank3, clickedSlot);
                            } else {
                                send(p, "§7Du benötigst mindestens " + RankManager.Ranks.valueOf("SUN").prefix + " §7um diesen Planeten kaufen zu können.");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.5f, 1);
                                p.closeInventory();
                            }
                        }
                        break;
                }
            }
        }
    }

    private void buyPlanet(Player p, int[] buildingSlotsList, int clickedSlot) {
        PlanetUser user = PlanetUser.users.get(p.getUniqueId());
        int x;
        if (Arrays.equals(buildingSlotsList, buildingSlotsRank1)) {
            x = 28;
        } else if (Arrays.equals(buildingSlotsList, buildingSlotsRank2)) {
            x = 35;
        } else {
            x = 42;
        }
        Planet _planet = null;
        for (int i : buildingSlotsList) {
            if (clickedSlot == i) {
                buildingNumber = x;
                _planet = Arrays.stream(Planet.values()).toList().get(PlanetUser.users.get(p.getUniqueId()).getBuilding(i));
            }
            x++;
        }
        int price = 0;
        for (Planet planet : Planet.values()) {
            if ((user.getBuilding(buildingNumber) + 1) == planet.getLevel()) {
                price = planet.getCosts();
            }
        }
        if (price != 0) {
            if (user.getPlunas() >= price) {
                user.setPlunas(user.getPlunas() - price);
                user.setBuilding(buildingNumber, user.getBuilding(buildingNumber) + 1);
                user.setBuildings(user.getBuildings()+1);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
                PlanetMenu2.getGUI(p).openQuiet(p);
                Bukkit.getPluginManager().callEvent(new PlayerBuyPlanetEvent(user, _planet));
            }
            else {
                send(p, "§7Für diesen Planeten fehlen dir §b" + PlanetInc.simplifyNumber(price- user.getPlunas()) + " §f\uE013");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.5f, 1);
                p.closeInventory();
            }
        }
    }
}
