package de.jannnnek.planetinc.listener;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.command.BuildCommand;
import de.jannnnek.planetinc.command.SpawnCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(Bukkit.getOnlinePlayers().size()+1);
        e.setMotd("§7[§bClosed Alpha§7] §dPluna§r - §6Netzwerk §7[§a1.19.3§7]§r\n§7           Discord: §ddc.plunamc.com");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!BuildCommand.allowed.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!BuildCommand.allowed.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){
        if (!BuildCommand.allowed.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickUp(EntityPickupItemEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!BuildCommand.allowed.contains(p)) {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFramePlace(HangingPlaceEvent e) {
        if(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting) {
            if (!BuildCommand.allowed.contains(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemFrame(HangingBreakByEntityEvent e) {
        if(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting) {
            if (!BuildCommand.allowed.contains((Player) e.getRemover())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)){
                Location location = SpawnCommand.locationFromString(PlanetInc.getYamlConfiguration().getString("spawnLocation"));
                e.getEntity().teleport(location);
            }
        }
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Firework)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!BuildCommand.allowed.contains((Player) e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            if (!BuildCommand.allowed.contains(e.getPlayer())) {
                if (e.getClickedBlock().getType().equals(Material.SPRUCE_TRAPDOOR)) {
                    e.setCancelled(true);
                }
                if (e.getClickedBlock().getType().equals(Material.NOTE_BLOCK)) {
                    e.setCancelled(true);
                }
                if (e.getClickedBlock().getType().equals(Material.BARREL)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

}
