package de.jannnnek.planetinc.listener;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.command.SpawnCommand;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;

import static de.nbhd.nevadyapi.messages.Message.send;

public class ConnectionListener implements Listener {

    private static final ArrayList<String> playerHider = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        setEffects(p);
        hideOnJoin(p);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setGameMode(GameMode.SURVIVAL);
        p.getInventory().clear();
        p.getInventory().setItem(8, createItemStack(Material.IRON_INGOT, "§dSpieler verstecken", "§7Rechts-Klick um Spieler auszublenden", 1));
        e.setJoinMessage("§f\ue001 §2» §7" + p.getName());
        spawnTeleport(p);
        Bukkit.getScheduler().runTaskLater(PlanetInc.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlanetInc.setScoreboard(p);
                tutorialSpawn(p);
            }
        }, 20);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(PlanetInc.serverStarting) e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cDer Server startet gerade...");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§f\ue001 §4« §7" + e.getPlayer().getName());
    }

    public static void setEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, -1 , 1, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, -1, 0, false, false));
    }

    public static void hideOnJoin(Player p) {
        if (playerHider != null) {
            for (String playerName : playerHider) {
                Bukkit.getPlayer(playerName).hidePlayer(p);
            }
        }
    }

    public static void spawnTeleport(Player player) {
        Location location = SpawnCommand.locationFromString(PlanetInc.getYamlConfiguration().getString("spawnLocation"));
        player.teleport(location);
    }

    public void tutorialSpawn(Player p) {
        PlanetUser user = PlanetUser.users.get(p.getUniqueId());
        if (user.getHighestBuilding() == 0) {
            send(p, "§d§kasdfghjklqwertzui");
            send(p, " ");
            send(p, "§7Willkommen bei §dPlanet§fInc§7!");
            send(p, "§7Um deine galaktische Reise zu starten, musst du zunächst");
            send(p, "§7deinen ersten Planeten beim Pluto-Kopf, in der Mitte der");
            send(p, "§7Map, kaufen. Anschließend kannst du durch aktives Klicken");
            send(p, "§7auf diesen Kopf oder durch passives Einkommen durch");
            send(p, "§7gekaufte Planeten weiteres Plunas §f\uE013 §7sammeln und");
            send(p, "§7der reichste kosmische Spieler auf ganz Pluna werden.");
            send(p, " ");
            send(p, "§d§kasdfghjklqwertzui");
        }
    }

    public static ItemStack createItemStack(Material material, String displayname, String lore, int customModelData) {
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayname);
        itemMeta.setLore(Collections.singletonList(lore));
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ArrayList<String> getPlayerHider() {
        return playerHider;
    }
}
