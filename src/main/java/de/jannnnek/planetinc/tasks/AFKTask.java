package de.jannnnek.planetinc.tasks;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.command.SpawnCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AFKTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlanetInc.userLocation.put(p.getUniqueId(), p.getLocation());
        }
        Bukkit.getScheduler().runTaskLater(PlanetInc.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PlanetInc.userLocation.containsKey(p.getUniqueId())) {
                        if (p.getLocation().equals(PlanetInc.userLocation.get(p.getUniqueId()))) {
                            Location location = SpawnCommand.locationFromString(PlanetInc.getYamlConfiguration().getString("spawnLocation"));
                            p.teleport(location);
                        }
                    }
                }
            }
        }, 5*20);
    }
}
