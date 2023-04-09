package de.jannnnek.planetinc.util;

import de.jannnnek.planetinc.PlanetInc;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/*
 * @author NbhdTV
 */

public class Message implements CommandExecutor {
    private static List<String> debugdisabled = new ArrayList<>();
    static String PREFIX = "§f\ue001 ";

    public static void setPrefix(String pre) {
        PREFIX = pre + " ";
    }

    public static void setPreparedPrefix(String pre) {
        PREFIX = "§8["+pre+"§8] §7";
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static void send(Player p, String m) {
        p.sendMessage(PREFIX + m);
    }

    public static void sendWithSound(Player p, String m, Sound sound) {
        p.sendMessage(PREFIX + m);
        p.playSound(p.getLocation(), sound, 0.5f, 1);
    }

    public static void sendAll(String m) {
        Bukkit.broadcastMessage(PREFIX + m);
    }
    public static void sendAllWithSound(String m, Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(PREFIX+m);
            player.playSound(player.getLocation(), sound, 0.5f, 1);
        }
    }
    public static void sendAllWorld(String m, String world) { Bukkit.getOnlinePlayers().forEach(all -> { if(all.getWorld().getName().equals(world)) all.sendMessage(PREFIX+m); }); }


    public static void sendTitle(Player p, String title, String subtitle) { p.sendTitle(title, subtitle); }

    public static void sendTitleWithTimes(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) { p.sendTitle(title, subtitle, fadeIn, stay, fadeOut); }
    public static void sendTitleWithTimesWithSound(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut, Sound sound) { p.sendTitle(title, subtitle, fadeIn, stay, fadeOut); p.playSound(p.getLocation(), sound, 0.5f, 1);}

    public static void sendAllTitle(String title, String subtitle) { Bukkit.getOnlinePlayers().forEach(all -> { all.sendTitle(title, subtitle); }); }

    public static void sendAllTitleWithTimes(String title, String subtitle, int fadeIn, int stay, int fadeOut) { Bukkit.getOnlinePlayers().forEach(all -> { all.sendTitle(title, subtitle, fadeIn, stay, fadeOut); }); }
    public static void sendAllTitleWithTimesWithSound(String title, String subtitle, int fadeIn, int stay, int fadeOut, Sound sound) { Bukkit.getOnlinePlayers().forEach(all -> { all.sendTitle(title, subtitle, fadeIn, stay, fadeOut); all.playSound(all.getLocation(), sound, 0.5f, 1);}); }


    public static void sendActionbar(Player p, String m) { p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(m)); }

    public static void sendAllActionbar(String m) { Bukkit.getOnlinePlayers().forEach(all -> { all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(m)); }); }
    public static void sendAllActionbarWorld(String m, String world) { Bukkit.getOnlinePlayers().forEach(all -> { if(all.getWorld().getName().equals(world)) all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(m)); }); }

    public static void sendFancyTitle(Player p, String msg, String prefix) {
        String toSplit = msg;
        int middle = msg.length()/2;
        StringBuilder stringBuilder = new StringBuilder();
        final String[] downString = {toSplit.substring(0, middle)};
        final String[] upString = {toSplit.substring(middle)};
        for(int i = 0; i < Math.max(toSplit.substring(0, middle).length(), toSplit.substring(middle).length()); i++) {
            Bukkit.getScheduler().runTaskLater(PlanetInc.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(upString[0].length() >= 1) {
                        stringBuilder.append(upString[0].charAt(0));
                        upString[0] = upString[0].substring(1);
                    }
                    if(downString[0].length() >= 1) {
                        stringBuilder.insert(0, downString[0].charAt(downString[0].length() - 1));
                        downString[0] = downString[0].substring(0, downString[0].length() - 1);
                    }
                    sendTitleWithTimesWithSound(p, "§7",prefix+stringBuilder.toString(), 0, 60, 20, Sound.UI_BUTTON_CLICK);
                }
            }, 3L *i);
        }
    }

    public static void sendAllFancyTitle(String title, String subtitle, String prefix) {
        String toSplit = title;
        int middle = title.length() / 2;
        StringBuilder stringBuilder = new StringBuilder();
        final String[] downString = {toSplit.substring(0, middle)};
        final String[] upString = {toSplit.substring(middle)};
        int limit = Math.max(toSplit.substring(0, middle).length(), toSplit.substring(middle).length());
        for (int i = 0; i < limit; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(PlanetInc.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (upString[0].length() >= 1) {
                        stringBuilder.append(upString[0].charAt(0));
                        upString[0] = upString[0].substring(1);
                    }
                    if (downString[0].length() >= 1) {
                        stringBuilder.insert(0, downString[0].charAt(downString[0].length() - 1));
                        downString[0] = downString[0].substring(0, downString[0].length() - 1);
                    }
                    sendAllTitleWithTimesWithSound(prefix+stringBuilder.toString(), (finalI+1 >= limit ? subtitle : "§7"), 0, 60, 20, (finalI+1 >= limit ? Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED : Sound.UI_BUTTON_CLICK));
                }
            }, 3L * i);
        }
    }

    public static void sendAllFancyTitleWithPotionEffect(String title, String subtitle, String prefix, PotionEffectType potionEffectType) {
        String toSplit = title;
        int middle = title.length() / 2;
        StringBuilder stringBuilder = new StringBuilder();
        final String[] downString = {toSplit.substring(0, middle)};
        final String[] upString = {toSplit.substring(middle)};
        int limit = Math.max(toSplit.substring(0, middle).length(), toSplit.substring(middle).length());
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.addPotionEffect(new PotionEffect(potionEffectType, Integer.MAX_VALUE, 255, false, false));
        }
        for (int i = 0; i < limit; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(PlanetInc.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (upString[0].length() >= 1) {
                        stringBuilder.append(upString[0].charAt(0));
                        upString[0] = upString[0].substring(1);
                    }
                    if (downString[0].length() >= 1) {
                        stringBuilder.insert(0, downString[0].charAt(downString[0].length() - 1));
                        downString[0] = downString[0].substring(0, downString[0].length() - 1);
                    }
                    sendAllTitleWithTimesWithSound(prefix+stringBuilder.toString(), (finalI+1 >= limit ? subtitle : "§7"), 0, 60, 20, (finalI+1 >= limit ? Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED : Sound.UI_BUTTON_CLICK));
                    if(finalI+1 >= limit) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.removePotionEffect(potionEffectType);
                        }
                    }
                }
            }, 3L * i);
        }
    }

    public static void sendLog(String m) {
        Bukkit.getConsoleSender().sendMessage(m);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if(all.isOp()) if(!debugdisabled.contains(all.getName())) all.sendMessage("§8[§7Debug§8] §7" + m);
        }
    }

    public static void sendNoPerm(Player p) {
        p.sendMessage(PREFIX + "§cDazu hast du keine Rechte!");
    }

    public static void sendEnableMessage(PluginDescriptionFile pluginDescriptionFile) {
        String s = "";
        for (String author : pluginDescriptionFile.getAuthors()) {
            s += " and "+author;
        }
        s = s.replaceFirst(" and ", "");
        sendLog(pluginDescriptionFile.getName() + " by "+s+" v"+pluginDescriptionFile.getVersion()+" was enabled.");
    }

    public static void sendNotPlayer(CommandSender sender) {
        sender.sendMessage(PREFIX + "§cDu musst ein Spieler sein!");
    }

    public static void sendWrongArguments(Player p) {
        p.sendMessage(PREFIX + "§cUngültige Argumente!");
    }

    public static void sendWrongArguments(Player p, String arg) {
        p.sendMessage(PREFIX + "§cUngültige Argumente! §b["+arg+"]");
    }

    public static boolean enabledDebug(String name) { return !debugdisabled.contains(name); }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0) {
                if (p.hasPermission("commands.debug")) {
                    if (!debugdisabled.contains(p.getName())) {
                        debugdisabled.add(p.getName());
                        sendLog(p.getName() + " disabled Debug!");
                        send(p, "Disabled Debug!");
                    } else if (debugdisabled.contains(p.getName())) {
                        sendLog(p.getName() + " enabled Debug!");
                        debugdisabled.remove(p.getName());
                        send(p, "Enabled Debug!");
                    }
                } else {
                    sendNoPerm(p);
                }
            } else {
                sendWrongArguments(p);
            }
        } else {
            sendNotPlayer(sender);
        }
        return false;
    }
}

