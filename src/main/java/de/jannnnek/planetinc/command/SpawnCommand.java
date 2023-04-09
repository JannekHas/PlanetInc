package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Location location = locationFromString(PlanetInc.getYamlConfiguration().getString("spawnLocation"));
            ((Player) commandSender).teleport(location);
            Message.send((Player) commandSender, "§7Du wurdest zum Spawn teleportiert!");
        }
        return true;
    }

    public static Location locationFromString(String locationString) {
        String[] arrayString = locationString.split(";");
        World world = Bukkit.getWorld(arrayString[0]);
        double x = Double.parseDouble(arrayString[1]);
        double y = Double.parseDouble(arrayString[2]);
        double z = Double.parseDouble(arrayString[3]);
        float yaw = Float.parseFloat(arrayString[4]);
        float pitch = Float.parseFloat(arrayString[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
