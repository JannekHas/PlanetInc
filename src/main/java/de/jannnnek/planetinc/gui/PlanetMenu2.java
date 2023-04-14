package de.jannnnek.planetinc.gui;

import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static de.nbhd.nevadyapi.messages.Message.send;

/**
 * @author NbhdTV
 */
public class PlanetMenu2 extends NGUI implements Listener {

    static int[] buildingSlotsRank1 = {10,11,12,13,14,15,16};
    static int[] buildingSlotsRank2 = {19,20,21,22,23,24,25};
    static int[] buildingSlotsRank3 = {28,29,30,31,32,33,34,37,38,39,40,41,42,43};
    static int[][] buildingSlotList = {buildingSlotsRank1, buildingSlotsRank2, buildingSlotsRank3};

    public static String guiName = "§a§f\uE012\uE012\uE012\uE012\uE012\uE012\uE012\uE012\uE014";
    public static NGUI getGUI(Player p) {
        NGUI ngui = new NGUI().setSize(54).setDisplayName(guiName).cancelClicks().ownListener(new PlanetMenu2()).prepareInventory();
        ngui.getInventory().setItem(8, new ItemBuilder(Material.POPPED_CHORUS_FRUIT).setName("§cSchließen").setCustomModelData(9).build());
        int i = 28;
        for (int[] slotList : buildingSlotList) {
            // Check ob der Spieler den Mindestrang für die Liste hat
            for (int slot : slotList) {
                switch (PlanetUser.users.get(p.getUniqueId()).getBuilding(i)) {
                    case 0:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT)
                                .setCustomModelData(12).setName("§f\uE015§7 §aMerkur kaufen").setLore("§7Preis: 2.500§f\uE013").build());
                        break;
                    case 1:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,1)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§7Merkur").setLore("§7§f\uE013§7 pro Sekunde: 1", "", "§7§f\uE015§7 Kaufe §6Venus","§7Preis: 5.000§f\uE013", "§7Erzeugt 2 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 2:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,2)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§6Venus").setLore("§7§f\uE013§7 pro Sekunde: 2", "", "§7§f\uE015§7 Kaufe §9Erde","§7Preis: 10.000§f\uE013", "§7Erzeugt 4 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 3:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,3)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§9Erde").setLore("§7§f\uE013§7 pro Sekunde: 4", "", "§7§f\uE015§7 Kaufe §cMars","§7Preis: 32.500§f\uE013", "§7Erzeugt 7 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 4:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,4)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§cMars").setLore("§7§f\uE013§7 pro Sekunde: 7", "", "§7§f\uE015§7 Kaufe §eJupiter","§7Preis: 175.000§f\uE013", "§7Erzeugt 11 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 5:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,5)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§eJupiter").setLore("§7§f\uE013§7 pro Sekunde: 11", "", "§7§f\uE015§7 Kaufe §6Saturn","§7Preis: 1.250.000§f\uE013", "§7Erzeugt 16 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 6:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,6)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§6Saturn").setLore("§7§f\uE013§7 pro Sekunde: 16", "", "§7§f\uE015§7 Kaufe §bUranus","§7Preis: 10.000.000§f\uE013", "§7Erzeugt 21 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 7:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,7)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§bUranus").setLore("§7§f\uE013§7 pro Sekunde: 21", "", "§7§f\uE015§7 Kaufe §1Neptun","§7Preis: 50.000.000§f\uE013", "§7Erzeugt 28 §f\uE013§7 pro Sekunde").build());
                        break;
                    case 8:
                        ngui.getInventory().setItem(slot, new ItemBuilder(Material.POPPED_CHORUS_FRUIT,8)
                                .setCustomModelData(PlanetUser.users.get(p.getUniqueId())
                                        .getBuilding(i)).setName("§1Neptun").setLore("§7§f\uE013§7 pro Sekunde: 29").build());
                        break;
                }
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
//                        if (Arrays.stream(buildingSlotsRank1).filter(x -> x == clickedSlot).toArray().length >= 1) {
//                            // Building kaufen, wenn Rank
//                        } else if (Arrays.stream(buildingSlotsRank2).filter(x -> x == clickedSlot).toArray().length >= 1) {
//                            // Building kaufen, wenn Rank
//                        } else if (Arrays.stream(buildingSlotsRank3).filter(x -> x == clickedSlot).toArray().length >= 1) {
//                            // Building kaufen, wenn Rank
//                        }
                        // Gebäude Stats
                        send(p,"§7Diese Planeten werden bald freigeschaltet.");
                        p.closeInventory();
                        break;
                }
            }
        }
    }
}
