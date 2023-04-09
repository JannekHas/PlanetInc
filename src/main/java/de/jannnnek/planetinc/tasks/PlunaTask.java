package de.jannnnek.planetinc.tasks;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.listener.ClickListener;
import de.jannnnek.planetinc.scoreboard.ScoreboardBuilder;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class PlunaTask extends BukkitRunnable {
    @Override
    public void run() {
        ClickListener.cps.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlanetUser user = PlanetUser.users.get(p.getUniqueId());
            if(ClickListener.lastClicked.containsKey(p.getName())) {
                if(ClickListener.lastClicked.get(p.getName()) <= System.currentTimeMillis()) {
                    ClickListener.addedPlunas.remove(p.getName());
                }
            }
            user.setPlunas(user.getPlunas() + user.getPlunasPerSecond());
            ScoreboardBuilder.getScoreboard(p.getName()).updateScoreboardWithIdentifier(" §f\uE013", " §f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunas()));
            ScoreboardBuilder.getScoreboard(p.getName()).updateScoreboardWithIdentifier(" §6§f\uE013", " §6§f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerSecond()));
            ScoreboardBuilder.getScoreboard(p.getName()).updateScoreboardWithIdentifier(" §5§f\uE013", " §5§f\uE013 §b"+ PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerClick()));
        }
    }
}
