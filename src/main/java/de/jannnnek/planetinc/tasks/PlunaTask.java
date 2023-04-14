package de.jannnnek.planetinc.tasks;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.listener.ClickListener;
import de.jannnnek.planetinc.util.PlanetUser;
import de.nbhd.nevadyapi.ScoreboardManager;
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
            if(p.getScoreboard().getObjective("dummy") != null) {
                if (!p.getScoreboard().getObjective("dummy").getDisplayName().equals("dummy")) {
                    user.setPlunas(user.getPlunas() + user.getPlunasPerSecond());
                    ScoreboardManager.updateScoreboardWithIdentifier(p, "§7» §f\uE013", "§7» §f\uE013 §b" + PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunas()));
                    ScoreboardManager.updateScoreboardWithIdentifier(p, "§7» §6§f\uE013", "§7» §6§f\uE013 §b" + PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerSecond()));
                    ScoreboardManager.updateScoreboardWithIdentifier(p, "§7» §5§f\uE013", "§7» §5§f\uE013 §b" + PlanetInc.simplifyNumber(PlanetUser.users.get(p.getUniqueId()).getPlunasPerClick()));
                }
            }
        }
    }
}
