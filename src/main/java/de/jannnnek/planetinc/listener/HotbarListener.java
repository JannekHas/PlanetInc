package de.jannnnek.planetinc.listener;

import de.jannnnek.planetinc.PlanetInc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class HotbarListener implements Listener {

    private final ItemStack hidePlayer = createItemStack(Material.IRON_INGOT, "§dSpieler verstecken", "§7Rechts-Klick um Spieler auszublenden", 1);
    private final ItemStack showPlayer = createItemStack(Material.IRON_INGOT, "§dSpieler anzeigen", "§7Rechts-Klick um Spieler anzuzeigen", 2);

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Action action = e.getAction();
        Player p = e.getPlayer();
        ItemStack handItem = p.getInventory().getItemInMainHand();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (handItem.equals(hidePlayer)) {
                hidePlayer(p);
                e.setCancelled(true);
            } else if (handItem.equals(showPlayer)) {
                showPlayer(p);
                e.setCancelled(true);
            }
        }
    }

    private void hidePlayer(Player p) {
        if (p.getCooldown(hidePlayer.getType()) == 0) {
            ConnectionListener.getPlayerHider().add(p.getName());
            for (Player player : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(PlanetInc.getInstance(), player);
            }
            p.getInventory().setItem(8, showPlayer);
            p.setCooldown(showPlayer.getType(), 20);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
        }
    }

    private void showPlayer(Player p) {
        if (p.getCooldown(showPlayer.getType()) == 0) {
            ConnectionListener.getPlayerHider().remove(p.getName());
            for (Player player : Bukkit.getOnlinePlayers()) {
                p.showPlayer(PlanetInc.getInstance(), player);
            }
            p.getInventory().setItem(8, hidePlayer);
            p.setCooldown(hidePlayer.getType(), 20);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
        }
    }

    private ItemStack createItemStack(Material material, String displayname, String lore, int customModelData) {
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayname);
        itemMeta.setLore(Collections.singletonList(lore));
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
