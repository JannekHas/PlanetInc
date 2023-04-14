package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.nbhd.nevadyapi.messages.Message.send;

public class ResetCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                UUID uuid = target.getUniqueId();
                if (PlanetUser.users.containsKey(uuid)) {
                    PlanetUser user = PlanetUser.users.get(uuid);
                    user.reset();
                    send(p, "§7Die Spielerdaten von §b" + args[0] + "§7 wurden zurückgesetzt.");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
                } else {
                    send(p, "§7Der Spieler §b" + args[0] + " §7hat noch nie §dPlanet§fInc§7 gespielt.");
                }
            } else {
                send(p, "§7Bitte nutze §b/reset <Spieler>");
            }
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
}
