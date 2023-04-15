package de.jannnnek.planetinc.advancements.planet;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import de.jannnnek.planetinc.advancements.events.PlayerBuyPlanetEvent;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NeptunAdvancement extends BaseAdvancement {
    public NeptunAdvancement(String key, AdvancementDisplay display, Advancement parent, int maxProgression) {
        super(key, display, parent, maxProgression);

        registerEvent(PlayerBuyPlanetEvent.class, e -> {
            PlanetUser user = e.getPlanetUser();
            if(user == null) return;
            Player p = Bukkit.getPlayer(user.getName());
            if(p == null) return;
            if(!isVisible(p)) return;
            if(user.getHighestBuilding() >= 8) {
                incrementProgression(p);
            }
        });
    }

    @Override
    public void giveReward(Player player) {

    }
}
