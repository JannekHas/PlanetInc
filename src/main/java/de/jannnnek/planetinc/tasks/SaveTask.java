package de.jannnnek.planetinc.tasks;

import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SaveTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getPlayer().getUniqueId();
            if(PlanetUser.users.containsKey(uuid)){
                PlanetUser.users.get(uuid).save();
            }
        }
    }
}
