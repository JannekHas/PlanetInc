package de.jannnnek.planetinc.gui;

import de.jannnnek.planetinc.planet.Planet;
import de.jannnnek.planetinc.util.Message;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

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
                            .setCustomModelData(12).setName("§aMerkur kaufen").setLore("§7Preis: 2500§f\uE013").build());
                    break;
                case 1:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT, 1)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§7Merkur").setLore("§f\uE013§7 pro Sekunde: 1", "", "§7(Klicke) Kaufe §6Venus","§7Preis: 5000§f\uE013", "§7Erzeugt 2 §f\uE013§7 pro Sekunde").build());
                    break;
                case 2:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT, 2)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§6Venus").setLore("§f\uE013§7 pro Sekunde: 2", "", "§7(Klicke) Kaufe §9Erde","§7Preis: 10000§f\uE013", "§7Erzeugt 4 §f\uE013§7 pro Sekunde").build());
                    break;
                case 3:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,3)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§9Erde").setLore("§f\uE013§7 pro Sekunde: 4", "", "§7(Klicke) Kaufe §cMars","§7Preis: 32500§f\uE013", "§7Erzeugt 7 §f\uE013§7 pro Sekunde").build());
                    break;
                case 4:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,4)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§cMars").setLore("§f\uE013§7 pro Sekunde: 7", "", "§7(Klicke) Kaufe §eJupiter","§7Preis: 175000§f\uE013", "§7Erzeugt 11 §f\uE013§7 pro Sekunde").build());
                    break;
                case 5:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,5)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§eJupiter").setLore("§f\uE013§7 pro Sekunde: 11", "", "§7(Klicke) Kaufe §6Saturn","§7Preis: 1250000§f\uE013", "§7Erzeugt 16 §f\uE013§7 pro Sekunde").build());
                    break;
                case 6:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,6)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§6Saturn").setLore("§f\uE013§7 pro Sekunde: 16", "", "§7(Klicke) Kaufe §bUranus","§7Preis: 10000000§f\uE013", "§7Erzeugt 21 §f\uE013§7 pro Sekunde").build());
                    break;
                case 7:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,7)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§bUranus").setLore("§f\uE013§7 pro Sekunde: 21", "", "§7(Klicke) Kaufe §1Neptun","§7Preis: 50000000§f\uE013", "§7Erzeugt 28 §f\uE013§7 pro Sekunde").build());
                    break;
                case 8:
                    ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,8)
                            .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                    .getBuilding(i)).setName("§1Neptun").setLore("§f\uE013§7 pro Sekunde: 29").build());
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
                    case 8 -> p.closeInventory();
//                    case 49 -> PlanetMenu2.getGUI(p).open(p);
                    case 49 -> Message.send(p, "§cKommt später");
                    default -> {
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
                                    PlanetMenu.getGUI(p).open(p);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
