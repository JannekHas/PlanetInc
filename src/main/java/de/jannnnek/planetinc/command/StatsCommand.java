package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.util.Message;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatsCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.getCooldown(Material.DIAMOND_HOE) > 0) {
                float remainingTime = (float) (p.getCooldown(Material.DIAMOND_HOE) / 20.0);
                Message.send(p, "§cDu musst noch " + round(remainingTime, 1) + " Sekunden warten.");
                return false;
            }
            if (args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                UUID uuid = target.getUniqueId();
                if (PlanetUser.users.containsKey(uuid)) {
                    PlanetUser user = PlanetUser.users.get(uuid);
                    Message.send(p, "§7Statistiken von §d" + target.getName() + "§7:");
                    Message.send(p, "§7Klicks: §b" + user.getClicks());
                    Message.send(p, "§7Anzahl gekaufter Planeten: §b" + user.getBuildings());
                    Message.send(p, "§f\uE013§7 pro Klick: §b" + user.getPlunasPerClick());
                    Message.send(p, "§f\uE013§7 pro Sekunde: §b" + user.getPlunasPerSecond());
                } else {
                    Message.send(p, "§7Der Spieler §d" + args[0] + " §7hat noch nie PlanetInc gespielt.");
                }
            } else if (args.length == 0) {
                PlanetUser user = PlanetUser.users.get(p.getUniqueId());
                Message.send(p, "§7Deine Statistiken:");
                Message.send(p, "§7Klicks: §b" + user.getClicks());
                Message.send(p, "§7Anzahl gekaufter Planeten: §b" + user.getBuildings());
                Message.send(p, "§f\uE013§7 pro Klick: §b" + user.getPlunasPerClick());
                Message.send(p, "§f\uE013§7 pro Sekunde: §b" + user.getPlunasPerSecond());
            } else {
                Message.send(p, "§7Bitte nutze §d/stats <Spieler>");
            }
            p.setCooldown(Material.DIAMOND_HOE, 60);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> names = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[0], names, new ArrayList<>());
        }
        return new ArrayList<>();
    }

    private static float round (float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
}
