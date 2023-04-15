package de.jannnnek.planetinc.planet;

import java.util.Arrays;

public enum Planet {

    PLUTO("§f\uE015§7 §aMerkur kaufen", 0, 0, 0, 16, "§7Preis: 2.500§f\uE013"),
    MERCURY("§7Mercury", 2500 , 1, 1, 1, "§71 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §6Venus","§7Preis: 5.000§f\uE013", "§7Erzeugt 2 §f\uE013§7/s"),
    VENUS("§6Venus", 5000, 2, 2, 2, "§72 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §9Erde","§7Preis: 10.000§f\uE013", "§7Erzeugt 4 §f\uE013§7/s"),
    EARTH("§9Earth", 10000, 4, 3, 3, "§74 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §cMars","§7Preis: 32.500§f\uE013", "§7Erzeugt 7 §f\uE013§7/s"),
    MARS("§cMars", 32500, 7, 4, 4,"§77 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §eJupiter","§7Preis: 175.000§f\uE013", "§7Erzeugt 11 §f\uE013§7/s" ),
    JUPITER("§eJupiter", 175000, 11, 5, 5, "§711 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §6Saturn","§7Preis: 1.250.000§f\uE013", "§7Erzeugt 16 §f\uE013§7/s"),
    SATURN("§6Saturn", 1250000, 16, 6, 6, "§716 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §bUranus","§7Preis: 10.000.000§f\uE013", "§7Erzeugt 21 §f\uE013§7/s"),
    URANUS("§bUranus", 10000000, 22, 7, 7,"§721 §f\uE013§7/s", "", "§7§f\uE015§7 Kaufe §1Neptun","§7Preis: 50.000.000§f\uE013", "§7Erzeugt 28 §f\uE013§7/s" ),
    NEPTUNE("§1Neptune", 50000000, 29, 8, 8,"§729 §f\uE013§7/s" );

    private String name;
    private int costs;
    private int produce;
    private int level;
    private int customModelData;
    private String[] lore;

    Planet(String name, int costs, int produce, int level, int customModelData, String... lore) {
        this.name = name;
        this.costs = costs;
        this.produce = produce;
        this.level = level;
        this.customModelData = customModelData;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public Integer getCosts() {
        return costs;
    }

    public Integer getProduce() {
        return produce;
    }

    public int getLevel() {
        return level;
    }
}
