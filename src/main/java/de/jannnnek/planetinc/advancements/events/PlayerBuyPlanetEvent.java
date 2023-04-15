package de.jannnnek.planetinc.advancements.events;

import de.jannnnek.planetinc.planet.Planet;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBuyPlanetEvent extends Event {

    public static final HandlerList HANDLER_LIST = new HandlerList();

    private final PlanetUser planetUser;
    private final Planet planet;

    public PlayerBuyPlanetEvent(PlanetUser planetUser, Planet planet) {
        this.planetUser = planetUser;
        this.planet = planet;
    }

    public PlanetUser getPlanetUser() {
        return planetUser;
    }

    public Planet getPlanet() {
        return planet;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
