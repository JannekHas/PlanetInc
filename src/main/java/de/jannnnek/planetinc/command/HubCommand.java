package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.PlanetInc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String string, String[] args) {
        if (s instanceof Player) {
            Player p = (Player) s;
            PlanetInc.sendPlayerToServer(p, "lobby-01");
        }
        return false;
    }
}
