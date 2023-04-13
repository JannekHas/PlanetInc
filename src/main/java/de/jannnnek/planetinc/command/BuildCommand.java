package de.jannnnek.planetinc.command;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static de.nbhd.nevadyapi.messages.Message.*;

public class BuildCommand implements CommandExecutor {

    public static ArrayList<Player> allowed = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (allowed.contains(p)) {
            allowed.remove(p);
            p.setGameMode(GameMode.SURVIVAL);
            send(p, "§7Du kannst nun nicht mehr bauen.");
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
        }
        else {
            allowed.add(p);
            p.setGameMode(GameMode.CREATIVE);
            send(p, "§7Du kannst nun bauen.");
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
        }
        return false;
    }

}
