package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.util.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetSpawnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Message.send((Player) commandSender, "§7Die SpawnLocation wurde auf deine aktuelle " +
                    "Position gesetzt!");
            PlanetInc.getYamlConfiguration().set("spawnLocation",
                    saveLocationToString(((Player) commandSender).getLocation()));
            try {
                PlanetInc.getYamlConfiguration().save(PlanetInc.getConfigFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public String saveLocationToString(Location location) {
        return location.getWorld().getName() + "; " + location.getX() + "; " + location.getY() + "; "
                + location.getZ() + "; " + location.getYaw() + "; " + location.getPitch();
    }
}
