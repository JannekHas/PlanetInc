package de.jannnnek.planetinc.tasks;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.util.Hologram;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RankingTask extends BukkitRunnable {
    @Override
    public void run() {
        Hologram.deleteAll();
        Hologram.lineCount = 999;
        Hologram holo = new Hologram(1).create(new Location(Bukkit.getWorld("world"), -14.5, 44, 16.5));
        holo.setTitle("§6Bestenliste: §f\uE013");
        Map<Integer, String> ranking = RankingTask.getPlunaRanking();
        for(int i = 10; i >= 1; i--) {
            if(ranking.containsKey(i)) {
                holo.addLine("§b"+i+". §a"+ranking.get(i)+" §8[§6"+PlanetInc.simplifyNumber(PlanetUser.users.get(Bukkit.getOfflinePlayer(ranking.get(i)).getUniqueId()).getPlunas())+" §f\uE013§8]");
            } else {
                holo.addLine("§b"+i+". §7-");
            }
        }
    }

    public static Map<Integer, String> getPlunaRanking() {
        HashMap<String, Double> map = new HashMap<>();
        HashMap<Integer, String> finished = new HashMap<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if(PlanetUser.users.containsKey(player.getUniqueId())) {
                map.put(player.getName(), PlanetUser.users.get(player.getUniqueId()).getPlunas());
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(PlanetUser.users.containsKey(player.getUniqueId())) {
                map.put(player.getName(), PlanetUser.users.get(player.getUniqueId()).getPlunas());
            }
        }

        Map<String, Double> sortedMap =
                map.entrySet().stream()
                        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        final int[] count = {1};
        sortedMap.forEach((s, i) -> {
            finished.put(count[0], s);
            count[0]++;
        });
        return finished;
    }
}
