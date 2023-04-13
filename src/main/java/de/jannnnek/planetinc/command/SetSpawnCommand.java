package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.PlanetInc;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static de.nbhd.nevadyapi.messages.Message.send;

public class SetSpawnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            send((Player) commandSender, "§7Die SpawnLocation wurde auf deine aktuelle " +
                    "Position gesetzt!");
            PlanetInc.getYamlConfiguration().set("spawnLocation",
                    saveLocationToString(((Player) commandSender).getLocation()));
            ((Player) commandSender).playSound(((Player) commandSender).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 1.0F);
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
