package de.jannnnek.planetinc.util;

import de.jannnnek.planetinc.planet.Planet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlanetUser {

    public static Map<UUID, PlanetUser> users = new HashMap<>();

    private float plunas;
    private int clicks;
    private int buildings;
    private int building0, building1, building2, building3, building4, building5, building6, building7, building8, building9,
            building10, building11, building12, building13, building14, building15, building16, building17, building18,
            building19, building20, building21, building22, building23, building24, building25, building26, building27,
            building28, building29, building30, building31, building32, building33, building34, building35, building36,
            building37, building38, building39, building40, building41, building42, building43, building44, building45,
            building46, building47, building48, building49, building50, building51, building52, building53, building54,
            building55;

    private FileBuilder f;

    Integer[] buildingInt = {building0, building1, building2, building3, building4, building5, building6, building7,
            building8, building9, building10, building11, building12, building13, building14, building15, building16,
            building17, building18, building19, building20, building21, building22, building23, building24, building25,
            building26, building27, building28, building29, building30, building31, building32, building33, building34,
            building35, building36, building37, building38, building39, building40, building41, building42, building43,
            building44, building45, building46, building47, building48, building49, building50, building51, building52,
            building53, building54, building55};

    String[] buildingString = {"building0", "building1", "building2", "building3", "building4", "building5",
            "building6", "building7", "building8", "building9", "building10", "building11", "building12", "building13",
            "building14", "building15", "building16", "building17", "building18", "building19", "building20",
            "building21", "building22", "building23", "building24", "building25", "building26", "building27",
            "building28", "building29", "building30", "building31", "building32", "building33", "building34",
            "building35", "building36", "building37", "building38", "building39", "building40", "building41",
            "building42", "building43", "building44", "building45", "building46", "building47", "building48",
            "building49", "building50", "building51", "building52", "building53", "building54", "building55"};

    private final int clickMultiplikator = 5;


    public PlanetUser(UUID uuid){
        f = new FileBuilder("plugins//PlanetInc//playerdata//", uuid.toString() + ".yml");

        this.plunas = f.exist() ? f.getInt("Plunas") : 2500;
        this.clicks = f.exist() ? f.getInt("Klicks") : 0;
        this.buildings = f.exist() ? f.getInt("Buildings") : 0;
        for (int i = 0; i < buildingInt.length; i++) {
            this.buildingInt[i] = f.exist() ? f.getInt(buildingString[i]) : 0;
        }

        this.users.put(uuid, this);
    }

    public void save(){
        f.setValue("Plunas", plunas);
        f.setValue("Klicks", clicks);
        f.setValue("Buildings", buildings);
        for (int i = 0; i < buildingInt.length; i++) {
            f.setValue(buildingString[i], buildingInt[i]);
        }
        f.save();
    }

    public float getPlunas(){
        return this.plunas;
    }

    public void setPlunas(float plunas) {
        this.plunas = plunas;
    }

    public int getBuildings() {
        return buildings;
    }

    public void setBuildings(int buildings) {
        this.buildings = buildings;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getBuilding(int building) {
        return this.buildingInt[building];
    }

    public void setBuilding(int building, int level) {
        this.buildingInt[building] = level;
    }

    public int getHighestBuilding() {
        int max = 0;
        for (int i = 0; i < buildingInt.length; i++) {
            if (buildingInt[i] > max) {
                max = buildingInt[i];
            }
        }
        return max;
    }

    public int getPlunasPerClick() {
        for (Planet planet : Planet.values()) {
            if (getHighestBuilding() == planet.getLevel()) {
                return planet.getProduce()*clickMultiplikator;
            }
        }
        return 0;
    }

    public int getPlunasPerSecond() {
        int pps = 0;
        for (int buildingWithLevel : buildingInt) {
            for (Planet planet : Planet.values()) {
                if (planet.getLevel() == buildingWithLevel) {
                    pps += planet.getProduce();
                }
            }
        }
        return pps;
    }

}
