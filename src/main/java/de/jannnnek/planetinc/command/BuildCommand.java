package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.util.Message;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildCommand implements CommandExecutor {

    public static ArrayList<Player> allowed = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (allowed.contains(p)) {
            allowed.remove(p);
            p.setGameMode(GameMode.SURVIVAL);
            Message.send(p, "§7Du kannst nun nicht mehr bauen.");
        }
        else {
            allowed.add(p);
            p.setGameMode(GameMode.CREATIVE);
            Message.send(p, "§7Du kannst nun bauen.");
        }
        return false;
    }

}
