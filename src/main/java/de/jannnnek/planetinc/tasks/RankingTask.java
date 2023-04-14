package de.jannnnek.planetinc.tasks;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.util.Hologram;
import de.jannnnek.planetinc.util.PlanetUser;
import de.nbhd.nevadyapi.mysql.ranks.RankManager;
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

        Hologram holo2 = new Hologram(2).create(new Location(Bukkit.getWorld("world"), 1.5, 44.5, 16.5));
        holo2.setTitle("§f\uE015 §7Plunas sammeln");
        holo2.addLine("§f\uE016 §7Planeten kaufen");

        Hologram holo3 = new Hologram(3).create(new Location(Bukkit.getWorld("world"), 1.5, 44, 0.5));
        holo3.setTitle("§6Bestenliste: §f\uE015");
        Map<Integer, String> ranking2 = RankingTask.getKlickRanking();
        for(int i = 10; i >= 1; i--) {
            if(ranking2.containsKey(i)) {
                holo3.addLine("§b"+i+". §a"+ranking2.get(i)+" §8[§6"+PlanetInc.simplifyNumber(PlanetUser.users.get(Bukkit.getOfflinePlayer(ranking2.get(i)).getUniqueId()).getClicks())+" §f\uE015§8]");
            } else {
                holo3.addLine("§b"+i+". §7-");
            }
        }

        Hologram holo4 = new Hologram(4).create(new Location(Bukkit.getWorld("world"), 1.5, 44, 32.5));
        holo4.setTitle("§6Free to play - Bestenliste: §f\uE013");
        Map<Integer, String> ranking3 = RankingTask.getFreeToPlayPlunaRanking();
        for(int i = 10; i >= 1; i--) {
            if(ranking3.containsKey(i)) {
                holo4.addLine("§b"+i+". §a"+ranking3.get(i)+" §8[§6"+PlanetInc.simplifyNumber(PlanetUser.users.get(Bukkit.getOfflinePlayer(ranking3.get(i)).getUniqueId()).getPlunas())+" §f\uE013§8]");
            } else {
                holo4.addLine("§b"+i+". §7-");
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

    public static Map<Integer, String> getKlickRanking() {
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<Integer, String> finished = new HashMap<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if(PlanetUser.users.containsKey(player.getUniqueId())) {
                map.put(player.getName(), PlanetUser.users.get(player.getUniqueId()).getClicks());
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(PlanetUser.users.containsKey(player.getUniqueId())) {
                map.put(player.getName(), PlanetUser.users.get(player.getUniqueId()).getClicks());
            }
        }

        Map<String, Integer> sortedMap =
                map.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        final int[] count = {1};
        sortedMap.forEach((s, i) -> {
            finished.put(count[0], s);
            count[0]++;
        });
        return finished;
    }

    public static Map<Integer, String> getFreeToPlayPlunaRanking() {
        HashMap<String, Double> map = new HashMap<>();
        HashMap<Integer, String> finished = new HashMap<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (RankManager.getRank(player.getUniqueId().toString()).equals("SPIELER")) {
                if(PlanetUser.users.containsKey(player.getUniqueId())) {
                    map.put(player.getName(), PlanetUser.users.get(player.getUniqueId()).getPlunas());
                }
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (RankManager.getRank(player.getUniqueId().toString()).equals("SPIELER")) {
                if(PlanetUser.users.containsKey(player.getUniqueId())) {
                    map.put(player.getName(), PlanetUser.users.get(player.getUniqueId()).getPlunas());
                }
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
