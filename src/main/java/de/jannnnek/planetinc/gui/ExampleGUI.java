package de.jannnnek.planetinc.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author NbhdTV
 */
public class ExampleGUI extends NGUI implements Listener {
    public static String guiName = "§bExample";
    public static NGUI getGUI() {
        NGUI ngui = new NGUI().setSize(27).setDisplayName(guiName).cancelClicks().prepareInventory();
        ngui.fillPlaceholders();
        return ngui;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().equals(guiName)) {
            Player p = (Player) e.getWhoClicked();
            if(!e.isShiftClick()) {
                //TODO: ClickEvent
            }
        }
    }
}
