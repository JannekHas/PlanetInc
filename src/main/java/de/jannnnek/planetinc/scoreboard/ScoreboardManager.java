package de.jannnnek.planetinc.scoreboard;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardManager implements Listener {
    public static void setScoreboard(Player p) {
        new ScoreboardBuilder()
                .setTitle("§dPlanet§fInc")
                .addScore("§d", 9)
                .addScore("§7Deine Plunas: ", 8)
                .addScore(" §f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunas()), 7)
                .addScore("§c", 6)
                .addScore("§7Deine Plunas pro Sekunde: ", 5)
                .addScore(" §6§f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerSecond()), 4)
                .addScore("§a", 3)
                .addScore("§7Deine Plunas pro Klick: ", 2)
                .addScore(" §5§f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerClick()), 1)
                .addScore("§b", 0)
                .apply(p);
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setScoreboard(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ScoreboardBuilder.getScoreboard(e.getPlayer().getName()).removeExisting(e.getPlayer());
    }
}
