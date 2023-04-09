package de.jannnnek.planetinc.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NbhdTV
 */
public class GuiListener implements Listener {
    public static List<String> cancelClicks = new ArrayList<>();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null) return;
        if(cancelClicks.contains(e.getView().getTitle())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerOpenGUI(PlayerOpenNGUI e) {
        Player p = e.getPlayer();
        InventoryView inventory = e.getInventoryView();
        if(inventory == null || p == null) return;

    }
}