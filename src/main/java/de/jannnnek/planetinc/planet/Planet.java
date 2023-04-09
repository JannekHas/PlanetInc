package de.jannnnek.planetinc.planet;

public enum Planet {

    PLUTO("§7Pluto", 0, 0, 0),
    MERCURY("§7Mercury", 2500 , 1, 1),
    VENUS("§6Venus", 5000, 2, 2),
    EARTH("§9Earth", 10000, 4, 3),
    MARS("§cMars", 32500, 7, 4),
    JUPITER("§eJupiter", 175000, 11, 5),
    SATURN("§6Saturn", 1250000, 16, 6),
    URANUS("§bUranus", 10000000, 22, 7),
    NEPTUNE("§1Neptune", 50000000, 29, 8);

    private String name;
    private int costs;
    private int produce;
    private int level;

    Planet(String name, int costs, int produce, int level) {
        this.name = name;
        this.costs = costs;
        this.produce = produce;
        this.level = level;
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
