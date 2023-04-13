package de.jannnnek.planetinc.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class Hologram {
    public static HashMap<Integer, ArmorStand> holograms = new HashMap<>();

    private int id;
    public static int lineCount = 999;

    public Hologram(int id) {
        this.id = id;
    }

    public Hologram create(Location loc) {
        ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setInvulnerable(true);
        as.setInvisible(true);
        as.setSmall(true);
        as.setGravity(false);
        as.setMarker(true);
        as.setCustomName("<no title set>");
        as.setCustomNameVisible(true);
        holograms.put(id, as);
        return new Hologram(id);
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        ArmorStand as = holograms.get(id);
        if(as != null) {
            if(as.getCustomName() != null) {
                return as.getCustomName();
            }
        }
        return null;
    }

    public boolean isTitleSet() {
        ArmorStand as = holograms.get(id);
        if(as != null) {
            if(as.getCustomName() != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean exists(int id) {
        return holograms.containsKey(id);
    }

    public void move(Location loc) {
        ArmorStand as = holograms.get(id);
        if(as != null) {
            as.teleport(loc);
        }
    }

    public Hologram setTitle(String title) {
        ArmorStand as = holograms.get(id);
        if(as != null) {
            as.setCustomName(title);
            as.setCustomNameVisible(true);
        }
        return new Hologram(id);
    }

    public void addLine(String line) {
        lineCount++;
        Hologram newHolo = new Hologram(lineCount).create(Hologram.holograms.get(id).getLocation());
        new Hologram(id).move(holograms.get(id).getLocation().add(0, 0.35, 0));
        newHolo.setTitle(line);
    }

    public void delete() {
        ArmorStand as = holograms.get(id);
        if(as != null) {
            holograms.remove(id);
            as.remove();
        }
    }

    public static void deleteAll() {
        holograms.forEach((id, as) ->{
            as.remove();
        });
        holograms.clear();
    }
    public static boolean isInUse(int id) {
        return holograms.containsKey(id);
    }
    public static Integer getIDByArmorStand(ArmorStand as) {
        final int[] returnvalue = {0};
        holograms.forEach((id, armorstand) -> {
            if(armorstand.equals(as)) {
                returnvalue[0] = id;
            }
        });
        return returnvalue[0];
    }

}
