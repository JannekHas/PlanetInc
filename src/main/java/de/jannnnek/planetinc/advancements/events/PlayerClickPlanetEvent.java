package de.jannnnek.planetinc.advancements.events;

import de.jannnnek.planetinc.planet.Planet;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerClickPlanetEvent extends Event {

    public static final HandlerList HANDLER_LIST = new HandlerList();

    private final PlanetUser planetUser;

    public PlayerClickPlanetEvent(PlanetUser planetUser) {
        this.planetUser = planetUser;
    }

    public PlanetUser getPlanetUser() {
        return planetUser;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
