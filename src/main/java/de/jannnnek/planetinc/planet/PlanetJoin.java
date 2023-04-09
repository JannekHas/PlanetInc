package de.jannnnek.planetinc.planet;

import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlanetJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        PlanetUser user = PlanetUser.users.containsKey(uuid) ? PlanetUser.users.get(uuid) : new PlanetUser(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if(PlanetUser.users.containsKey(uuid)){
            PlanetUser.users.get(uuid).save();
        }
    }

}
