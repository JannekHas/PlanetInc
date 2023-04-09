package de.jannnnek.planetinc.gui;

import de.jannnnek.planetinc.PlanetInc;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NbhdTV
 */
public class NGUI {
    private Inventory inventory;
    private int size;
    private String displayName;

    private static List<String> registeredListener = new ArrayList<>();

    public NGUI() {}
    public NGUI(int size, String displayName) {
        this.size = size;
        this.displayName = displayName;
    }

    public int getSize() {
        return size;
    }

    public NGUI setSize(int size) {
        this.size = size;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public NGUI setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public NGUI prepareInventory() {
        Inventory inventory = Bukkit.createInventory(null, this.size, this.displayName);
        this.inventory = inventory;
        this.inventory.setMaxStackSize(120);
        return this;
    }

    public NGUI fillPlaceholders() {
        if(this.inventory != null) for (int i = 0; i < inventory.getSize(); i++) { if(inventory.getItem(i) == null) inventory.setItem(i, ItemBuilder.getPlaceholder()); }
        return this;
    }

    public NGUI ownListener(Listener listener) {
        if(!registeredListener.contains(listener.getClass().getName())) {
            Bukkit.getPluginManager().registerEvents(listener, PlanetInc.getInstance());
            registeredListener.add(listener.getClass().getName());
        }
        return this;
    }

    public NGUI cancelClicks() {
        if(!GuiListener.cancelClicks.contains(getDisplayName())) GuiListener.cancelClicks.add(getDisplayName());
        return this;
    }

    public void open(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_BARREL_OPEN, 0.5f, 1);
        Bukkit.getPluginManager().callEvent(new PlayerOpenNGUI(p.getName(), p.openInventory(getInventory())));
    }
}
