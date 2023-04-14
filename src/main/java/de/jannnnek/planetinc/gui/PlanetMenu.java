package de.jannnnek.planetinc.gui;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.planet.Planet;
import de.jannnnek.planetinc.util.PlanetUser;
import de.nbhd.nevadyapi.mysql.ranks.RankManager;
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
public class PlanetMenu extends NGUI implements Listener {

    static int[] buildingSlotsNormal = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
    int buildingNumber;

    public static String guiName = "§f\uE012\uE012\uE012\uE012\uE012\uE012\uE012\uE012\uE014";
    public static NGUI getGUI(Player p) {
        NGUI ngui = new NGUI().setSize(54).setDisplayName(guiName).cancelClicks().ownListener(new PlanetMenu()).prepareInventory();
        ngui.getInventory().setItem(8, new ItemBuilder(Material.POPPED_CHORUS_FRUIT).setName("§cSchließen").setCustomModelData(9).build());
        int i = 0;
        for (int slot : buildingSlotsNormal) {
            switch (PlanetUser.users.get(p.getUniqueId()).getBuilding(i)) {
                case 0:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT)
                            .setCustomModelData(12).setName("§f\uE015§7 §aMerkur kaufen").setLore("§7Preis: 2.500§f\uE013").build());
                    break;
                case 1:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT, 1)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§7Merkur").setLore("§71 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §6Venus","§7Preis: 5.000§f\uE013", "§7Erzeugt 2 §f\uE013§7/s").build());
                    break;
                case 2:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT, 2)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§6Venus").setLore("§72 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §9Erde","§7Preis: 10.000§f\uE013", "§7Erzeugt 4 §f\uE013§7/s").build());
                    break;
                case 3:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,3)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§9Erde").setLore("§74 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §cMars","§7Preis: 32.500§f\uE013", "§7Erzeugt 7 §f\uE013§7/s").build());
                    break;
                case 4:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,4)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§cMars").setLore("§77 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §eJupiter","§7Preis: 175.000§f\uE013", "§7Erzeugt 11 §f\uE013§7/s").build());
                    break;
                case 5:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,5)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§eJupiter").setLore("§711 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §6Saturn","§7Preis: 1.250.000§f\uE013", "§7Erzeugt 16 §f\uE013§7/s").build());
                    break;
                case 6:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,6)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§6Saturn").setLore("§716 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §bUranus","§7Preis: 10.000.000§f\uE013", "§7Erzeugt 21 §f\uE013§7/s").build());
                    break;
                case 7:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,7)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§bUranus").setLore("§721 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §1Neptun","§7Preis: 50.000.000§f\uE013", "§7Erzeugt 28 §f\uE013§7/s").build());
                    break;
                case 8:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,8)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§1Neptun").setLore("§729 §f\uE013§7/s").build());
                    break;
            }
            i++;
        }
        ngui.getInventory().setItem(49, new ItemBuilder(Material.POPPED_CHORUS_FRUIT).setCustomModelData(11).setName("§dRang Planeten").build());
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
                        if (RankManager.getRank(p.getUniqueId().toString()).equals("SPIELER")) {
                            send(p, "§7Du benötigst einen Rang um auf diese Planeten zugreifen zu können.");
                        } else {
                            if (p.getCooldown(Material.DIAMOND_AXE) == 0) {
                                PlanetMenu2.getGUI(p).open(p);
                                p.setCooldown(Material.DIAMOND_AXE, 10);
                            }
                        }
                        break;
                    default:
                        if (Arrays.stream(buildingSlotsNormal).filter(x -> x == clickedSlot).toArray().length >= 1) {
                            PlanetUser user = PlanetUser.users.get(p.getUniqueId());
                            int x = 0;
                            for (int i : buildingSlotsNormal) {
                                if (clickedSlot == i) {
                                    buildingNumber = x;
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
                                    PlanetMenu.getGUI(p).open(p);
                                } else {
                                    send(p, "§7Für diesen Planeten fehlen dir §b" + PlanetInc.simplifyNumber(price- user.getPlunas()) + " §f\uE013");
                                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.5f, 1);
                                    p.closeInventory();
                                }
                            }
                        }
                }
            }
        }
    }
}
