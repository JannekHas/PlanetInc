package de.jannnnek.planetinc;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import de.jannnnek.planetinc.advancements.building.*;
import de.jannnnek.planetinc.advancements.click.*;
import de.jannnnek.planetinc.advancements.planet.*;
import de.jannnnek.planetinc.command.*;
import de.jannnnek.planetinc.gui.GuiListener;
import de.jannnnek.planetinc.listener.ClickListener;
import de.jannnnek.planetinc.listener.ConnectionListener;
import de.jannnnek.planetinc.listener.EventListener;
import de.jannnnek.planetinc.listener.HotbarListener;
import de.jannnnek.planetinc.planet.PlanetJoin;
import de.jannnnek.planetinc.tasks.AFKTask;
import de.jannnnek.planetinc.tasks.PlunaTask;
import de.jannnnek.planetinc.tasks.RankingTask;
import de.jannnnek.planetinc.tasks.SaveTask;
import de.jannnnek.planetinc.util.Hologram;
import de.jannnnek.planetinc.util.PlanetUser;
import de.nbhd.nevadyapi.messages.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static de.nbhd.nevadyapi.messages.Message.sendEnableMessage;

public class PlanetInc extends JavaPlugin {

    private static PlanetInc instance;
    public static Map<UUID, Location> userLocation = new HashMap<>();
    private static File file;
    private static YamlConfiguration yamlConfiguration;
    public static boolean serverStarting = false;

    public static UltimateAdvancementAPI ultimateAdvancementAPI;

    @Override
    public void onEnable() {
        serverStarting = true;
        setAdvancement();
        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new HotbarListener(), this);
        getServer().getPluginManager().registerEvents(new PlanetJoin(), this);
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getCommand("gethead").setExecutor(new GetPlanetHeadCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("debug").setExecutor(new Message());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("stats").setTabCompleter(new StatsCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("reset").setTabCompleter(new ResetCommand());

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
                    if(!PlanetUser.users.containsKey(p.getUniqueId())) new PlanetUser(p.getUniqueId());
                    ConnectionListener.setEffects(p);
                    ConnectionListener.hideOnJoin(p);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    ConnectionListener.spawnTeleport(p);
                    setScoreboard(p);
                }
                (new RankingTask()).runTaskTimer(getInstance(), 0, 600*20);
                setHologram();
                serverStarting = false;
            }
        }, 10);
        initConfig();
    }

    @Override
    public void onDisable() {
        Hologram.deleteAll();
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getPlayer().getUniqueId();
            if(PlanetUser.users.containsKey(uuid)){
                PlanetUser.users.get(uuid).save();
            }
        }
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

    public static void setScoreboard(Player p) {
        Scoreboard scoreboard = p.getScoreboard();
        Objective objective = scoreboard.getObjective("dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§dPlanet§fInc");
        objective.getScore("§d").setScore(9);
        objective.getScore("§7Plunas: ").setScore(8);
        objective.getScore("§7» §f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunas())).setScore(7);
        objective.getScore("§c").setScore(6);
        objective.getScore("§7Plunas/s: ").setScore(5);
        objective.getScore("§7» §6§f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerSecond())).setScore(4);
        objective.getScore("§a").setScore(3);
        objective.getScore("§7Plunas/Klick: ").setScore(2);
        objective.getScore("§7» §5§f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerClick())).setScore(1);
        objective.getScore("§b").setScore(0);
    }

    public static void setHologram() {
        Hologram holo = new Hologram(2).create(new Location(Bukkit.getWorld("world"), 1.5, 44, 16.5));
        holo.setTitle("§f\uE015 §7Plunas sammeln");
        holo.addLine("§f\uE016 §7Planeten kaufen");
    }

    public void setAdvancement() {
        ultimateAdvancementAPI = UltimateAdvancementAPI.getInstance(this);
        AdvancementTab advancementTab = ultimateAdvancementAPI.createAdvancementTab("planetinc");

        RootAdvancement rootAdvancement = new RootAdvancement(
                advancementTab, "root", new AdvancementDisplay(Material.FIREWORK_ROCKET, "PlanetInc", AdvancementFrameType.CHALLENGE,
                false, false, 0, 1, "Erfolge für PlanetInc"), "textures/block/black_wool.png");

        TenClickAdvancement tenClickAdvancement = new TenClickAdvancement(
                "tenclick", new AdvancementDisplay(Material.PAPER, "X", AdvancementFrameType.TASK,
                true, true, 1, 0, "X"), rootAdvancement, 1);
        ThousandClickAdvancement thousandClickAdvancement = new ThousandClickAdvancement(
                "thousandclick", new AdvancementDisplay(Material.PAPER, "1001 Nacht", AdvancementFrameType.TASK,
                true, true, 2, 0, "Die Zeit verfliegt wie im Märchen"), rootAdvancement, 1);
        TenThousandClickAdvancement tenThousandClickAdvancement = new TenThousandClickAdvancement(
                "tenthousandclick", new AdvancementDisplay(Material.PAPER, "Hilferuf ins All", AdvancementFrameType.TASK,
                true, true, 3, 0, "Du hast einen Hilferuf mit 10700kHz ins All gesendet"), rootAdvancement, 1);
        FiftyThousandClickAdvancement fiftyThousandClickAdvancement = new FiftyThousandClickAdvancement(
                "fiftythousandclick", new AdvancementDisplay(Material.PAPER, "Mathegenie", AdvancementFrameType.TASK,
                true, true, 4, 0, "1^1 + 2^2 + 3^3 + 4^4 + 5^5 + 6^6"), rootAdvancement, 1);
        HundredThousandClickAdvancement hundredThousandClickAdvancement = new HundredThousandClickAdvancement(
                "hundredthousandclick", new AdvancementDisplay(Material.PAPER, "Klickprimus", AdvancementFrameType.TASK,
                true, true, 5, 0, "Du hast die 10000ste Primzahl erreicht"), rootAdvancement, 1);
        FiveHundredThousandClickAdvancement fiveHundredThousandClickAdvancement = new FiveHundredThousandClickAdvancement(
                "fivehundredthousandclick", new AdvancementDisplay(Material.PAPER, "Autoklicker 2.0", AdvancementFrameType.TASK,
                true, true, 6, 0, "Mach die Hacks aus!"), rootAdvancement, 1);
        MillionClickAdvancement millionClickAdvancement = new MillionClickAdvancement(
                "millionclick", new AdvancementDisplay(Material.PAPER, "Zeit für eine neues Maus", AdvancementFrameType.TASK,
                true, true, 7, 0, "maus.plunamc.de"), rootAdvancement, 1);



        SevenPlanetsAdvancement sevenPlanetsAdvancement = new SevenPlanetsAdvancement(
                "sevenplanet", new AdvancementDisplay(Material.SUGAR_CANE, "7 Planeten", AdvancementFrameType.TASK,
                true, true, 1, 1, "Auf Planet 7 im siebten Himmel"), rootAdvancement, 1);
        TwentyEightPlanetsAdvancement twentyEightPlanetsAdvancement = new TwentyEightPlanetsAdvancement(
                "twentyeightplanet", new AdvancementDisplay(Material.SUGAR_CANE, "28 Planeten", AdvancementFrameType.TASK,
                true, true, 2, 1, "Muss hier viel Nickel geben"), rootAdvancement, 1);
        FiftySixPlanetsAdvancement fiftySixPlanetsAdvancement = new FiftySixPlanetsAdvancement(
                "fiftysixplanet", new AdvancementDisplay(Material.SUGAR_CANE, "56 Planeten", AdvancementFrameType.TASK,
                true, true, 3, 1, "Noch 8, dann hast du dein Stack voll"), rootAdvancement, 1);
        EightyFourPlanetsAdvancement eightyFourPlanetsAdvancement = new EightyFourPlanetsAdvancement(
                "eightyfourplanet", new AdvancementDisplay(Material.SUGAR_CANE, "84 Planeten", AdvancementFrameType.TASK,
                true, true, 4, 1, "In 84 Erdjahren hat Uranus dann auch mal die Sonne umkreist"), rootAdvancement, 1);
        HundredTwelvePlanetsAdvancement hundredTwelvePlanetsAdvancement = new HundredTwelvePlanetsAdvancement(
                "hundredtwelveplanet", new AdvancementDisplay(Material.SUGAR_CANE, "112 Planeten", AdvancementFrameType.TASK,
                true, true, 5, 1, "Auch hier ist der Notruf unter 112 erreichbar"), rootAdvancement, 1);
        HundredSixtyEightPlanetsAdvancement hundredSixtyEightPlanetsAdvancement = new HundredSixtyEightPlanetsAdvancement(
                "hundredsixthyeightplanet", new AdvancementDisplay(Material.SUGAR_CANE, "168 Planeten", AdvancementFrameType.TASK,
                true, true, 6, 1, "Am 22.07.168 gab es eine 7 minütige Sonnenfinsternis"), rootAdvancement, 1);
        TwoHundredTwentyFourPlanetsAdvancement twoHundredTwentyFourPlanetsAdvancement = new TwoHundredTwentyFourPlanetsAdvancement(
                "twohundredtwentyfourplanet", new AdvancementDisplay(Material.SUGAR_CANE, "224 Planeten", AdvancementFrameType.TASK,
                true, true, 7, 1, "Keine Reise durchs All ➡ Kein Foto für dich"), rootAdvancement, 1);


        VenusAdvancement venusAdvancement = new VenusAdvancement(
                "venusplanet", new AdvancementDisplay(Material.STONE_AXE, "Venus", AdvancementFrameType.TASK,
                true, true, 1, 2, "460°C am Tag? Regen aus ätzender Schwefelsäure? Lieber schnell weiter!"), rootAdvancement, 1);
        ErdeAdvancement erdeAdvancement = new ErdeAdvancement(
                "erdeplanet", new AdvancementDisplay(Material.IRON_AXE, "Erde", AdvancementFrameType.TASK,
                true, true, 2, 2, "Jetzt bist du knapp 150 Millionen Kilometer von der Sonne entfernt"), rootAdvancement, 1);
        MarsAdvancement marsAdvancement = new MarsAdvancement(
                "marsplanet", new AdvancementDisplay(Material.GOLDEN_AXE, "Mars", AdvancementFrameType.TASK,
                true, true, 3, 2, "Wusstest du, dass hier der höchste Berg des Sonnensystems ist?"), rootAdvancement, 1);
        JupiterAdvancement jupiterAdvancement = new JupiterAdvancement(
                "jupiterplanet", new AdvancementDisplay(Material.DIAMOND_AXE, "Jupiter", AdvancementFrameType.TASK,
                true, true, 4, 2, "Seit 340 Jahren wirbelt schon der große rote Fleck"), rootAdvancement, 1);
        SaturnAdvancement saturnAdvancement = new SaturnAdvancement(
                "saturnplanet", new AdvancementDisplay(Material.NETHERITE_AXE, "Saturn", AdvancementFrameType.TASK,
                true, true, 5, 2, "100m breite Blitze, die 1 Million Mal stärker sind, als auf der Erde? Weiter gehts!"), rootAdvancement, 1);
        UranusAdvancement uranusAdvancement = new UranusAdvancement(
                "uranusplanet", new AdvancementDisplay(Material.DIAMOND_SWORD, "Uranus", AdvancementFrameType.TASK,
                true, true, 6, 2, "Its raining diamonds, hallelujah"), rootAdvancement, 1);
        NeptunAdvancement neptunAdvancement = new NeptunAdvancement(
                "neptunplanet", new AdvancementDisplay(Material.NETHERITE_SWORD, "Neptun", AdvancementFrameType.TASK,
                true, true, 7, 2, "Einfach Eisvulkane die Stickstoff spucken"), rootAdvancement, 1);

        advancementTab.registerAdvancements(rootAdvancement, tenClickAdvancement, thousandClickAdvancement,
                tenThousandClickAdvancement, fiftyThousandClickAdvancement, hundredThousandClickAdvancement,
                fiveHundredThousandClickAdvancement, millionClickAdvancement, sevenPlanetsAdvancement,
                twentyEightPlanetsAdvancement, fiftySixPlanetsAdvancement, eightyFourPlanetsAdvancement,
                hundredTwelvePlanetsAdvancement, hundredSixtyEightPlanetsAdvancement,
                twoHundredTwentyFourPlanetsAdvancement, venusAdvancement, erdeAdvancement, marsAdvancement,
                jupiterAdvancement, saturnAdvancement, uranusAdvancement, neptunAdvancement);

        advancementTab.getEventManager().register(advancementTab, PlayerLoadingCompletedEvent.class, event -> {
            advancementTab.showTab(event.getPlayer());
            advancementTab.grantRootAdvancement(event.getPlayer());
        });
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

    public static String simplifyNumber(double number) {
        number = roundDouble(number);
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(number);
    }

    private static double roundDouble (double value) {
        int scale = (int) Math.pow(10, 1);
        return (double) Math.round(value * scale) / scale;
    }

    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public static File getConfigFile() {
        return file;
    }
}
