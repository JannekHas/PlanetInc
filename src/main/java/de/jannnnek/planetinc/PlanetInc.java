package de.jannnnek.planetinc;

import de.jannnnek.planetinc.command.*;
import de.jannnnek.planetinc.gui.GuiListener;
import de.jannnnek.planetinc.listener.ClickListener;
import de.jannnnek.planetinc.listener.ConnectionListener;
import de.jannnnek.planetinc.listener.EventListener;
import de.jannnnek.planetinc.listener.HotbarListener;
import de.jannnnek.planetinc.planet.PlanetJoin;
import de.jannnnek.planetinc.scoreboard.ScoreboardManager;
import de.jannnnek.planetinc.tasks.AFKTask;
import de.jannnnek.planetinc.tasks.PlunaTask;
import de.jannnnek.planetinc.tasks.RankingTask;
import de.jannnnek.planetinc.tasks.SaveTask;
import de.jannnnek.planetinc.util.Hologram;
import de.jannnnek.planetinc.util.Message;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static de.jannnnek.planetinc.util.Message.sendEnableMessage;
import static de.jannnnek.planetinc.util.Message.sendLog;

public class PlanetInc extends JavaPlugin {

    private static PlanetInc instance;
    public static Map<UUID, Location> userLocation = new HashMap<>();
    private static File file;
    private static YamlConfiguration yamlConfiguration;
    public static boolean serverStarting = false;

    @Override
    public void onEnable() {
        serverStarting = true;
        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new HotbarListener(), this);
        getServer().getPluginManager().registerEvents(new PlanetJoin(), this);
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardManager(), this);
        getCommand("gethead").setExecutor(new GetPlanetHeadCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("debug").setExecutor(new Message());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("stats").setTabCompleter(new StatsCommand());
        getCommand("hub").setExecutor(new HubCommand());
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        instance = this;

        (new AFKTask()).runTaskTimer(getInstance(), 300*20, 300*20);
        (new PlunaTask()).runTaskTimer(getInstance(), 20, 20);
        (new SaveTask()).runTaskTimer(getInstance(), 300*20, 600*20);
        Bukkit.getScheduler().runTaskLater(getInstance(), new Runnable() {
            @Override
            public void run() {
                sendEnableMessage(getDescription());
                for (OfflinePlayer all : Bukkit.getOfflinePlayers()) {
                    if(!PlanetUser.users.containsKey(all.getUniqueId())) new PlanetUser(all.getUniqueId());
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    ScoreboardManager.setScoreboard(p);
                    if(!PlanetUser.users.containsKey(p.getUniqueId())) new PlanetUser(p.getUniqueId());
                    ConnectionListener.setEffects(p);
                    ConnectionListener.hideOnJoin(p);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    ConnectionListener.spawnTeleport(p);
                }
                (new RankingTask()).runTaskTimer(getInstance(), 0, 600*20);
                serverStarting = false;
            }
        }, 10);

        initConfig();
    }

    @Override
    public void onDisable() {
        Hologram.deleteAll();
    }

    private void initConfig() {
        if (getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.options().copyDefaults(true);
        yamlConfiguration.addDefault("spawnLocation", "null");
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendPlayerToServer(Player p, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            p.sendPluginMessage(PlanetInc.getInstance(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception e) {
            Message.send(p, "§cDieser Server befindet sich aktuell in Wartungsarbeiten.");
        }
    }

    public static PlanetInc getInstance() {
        return instance;
    }

    public static Map<UUID, Location> getUserLocation() {
        return userLocation;
    }

    public static String simplifyNumber(float number) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(number);
    }

    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public static File getConfigFile() {
        return file;
    }
}
