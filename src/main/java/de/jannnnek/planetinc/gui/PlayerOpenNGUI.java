package de.jannnnek.planetinc.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

/**
 * @author NbhdTV
 */
public class PlayerOpenNGUI extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private Player player;
    private InventoryView inventoryView;


    public Player getPlayer() {
        return player;
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

    public PlayerOpenNGUI(String player, InventoryView inventoryView) {
        this.player = Bukkit.getPlayer(player);
        this.inventoryView = inventoryView;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
