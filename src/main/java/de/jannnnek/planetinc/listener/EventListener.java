package de.jannnnek.planetinc.listener;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.command.BuildCommand;
import de.jannnnek.planetinc.command.SpawnCommand;
import de.jannnnek.planetinc.util.PlanetUser;
import de.nbhd.nevadyapi.mysql.ranks.RankManager;
import de.nbhd.nevadyapi.npcs.Conversation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.BlockInventoryHolder;

import static de.jannnnek.planetinc.command.SpawnCommand.locationFromString;
import static de.nbhd.nevadyapi.messages.Message.*;

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
    public void onSneak(PlayerToggleSneakEvent e) {
        if(e.isSneaking()) {
            if (e.getPlayer().getLocation().clone().getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)) {
                Location loc = findNextBlock(e.getPlayer().getLocation().clone().subtract(0, 1, 0), Material.DAYLIGHT_DETECTOR, true);
                if (loc != null) {
                    Location newLoc = loc.clone();
                    newLoc.setYaw(-130.0f);
                    e.getPlayer().teleport(newLoc.add(0.5, 0.375, 0.5));
                    sendTitleWithTimesWithSound(e.getPlayer(), "", "§c↓", 0, 40, 10, Sound.ENTITY_PLAYER_LEVELUP);
                } else {
                    sendTitleWithTimesWithSound(e.getPlayer(), "§7", "§4❌", 0, 40, 10, Sound.BLOCK_NOTE_BLOCK_BASS);
                }
            }
        }
    }

    public static Location findNextBlock(Location loc, Material mat, boolean negative) {
        if(!negative) {
            for(int y = loc.getBlockY(); y < 319; y++) {
                if(loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType().equals(mat)) {
                    return loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getLocation();
                }
            }
        } else {
            for(int y = loc.getBlockY(); y > -60; y--) {
                if(loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType().equals(mat)) {
                    return loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getLocation();
                }
            }
        }
        return null;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(e.getTo().getBlockY() == 12) {
            e.getPlayer().teleport(locationFromString(PlanetInc.getYamlConfiguration().getString("spawnLocation")));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.5f, 1);
            return;
        }
        if (e.getTo().getBlockY() > e.getFrom().getBlockY()) {
            if (e.getTo().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)) {
                Location loc = findNextBlock(e.getTo().clone().add(0, 2, 0), Material.DAYLIGHT_DETECTOR, false);
                if (loc != null) {
                    Location newLoc = loc.clone();
                    newLoc.setYaw(-130.0f);
                    e.getPlayer().teleport(newLoc.add(0.5, 0.375, 0.5));
                    sendTitleWithTimesWithSound(e.getPlayer(), "§7", "§a↑", 0, 40, 10, Sound.ENTITY_PLAYER_LEVELUP);
                } else {
                    sendTitleWithTimesWithSound(e.getPlayer(), "§7", "§4❌", 0, 40, 10, Sound.BLOCK_NOTE_BLOCK_BASS);
                }
            }
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
                Location location = locationFromString(PlanetInc.getYamlConfiguration().getString("spawnLocation"));
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
                if (e.getClickedBlock().getType().equals(Material.NOTE_BLOCK)
                        || e.getClickedBlock().getState() instanceof BlockInventoryHolder
                        || e.getClickedBlock().getType().name().contains("TRAPDOOR")
                        || e.getClickedBlock().getType().name().contains("BED")
                        || e.getClickedBlock().getType().equals(Material.JUNGLE_DOOR)
                        || e.getClickedBlock().getType().equals(Material.LEVER)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        RankManager.Ranks ranks = RankManager.Ranks.valueOf(RankManager.getRank(p.getUniqueId().toString()));
        String format = ranks.prefix + p.getName() + " §8: §7" + e.getMessage();
        p.sendMessage(format);
        for (Player all : Bukkit.getOnlinePlayers().stream().filter(_player -> !_player.equals(p)).toList()) {
            all.sendMessage(format);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

}
