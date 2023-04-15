package de.jannnnek.planetinc.advancements.click;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import de.jannnnek.planetinc.advancements.events.PlayerClickPlanetEvent;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TenThousandClickAdvancement extends BaseAdvancement {
    public TenThousandClickAdvancement(String key, AdvancementDisplay display, Advancement parent, int maxProgression) {
        super(key, display, parent, maxProgression);

        registerEvent(PlayerClickPlanetEvent.class, e -> {
            PlanetUser user = e.getPlanetUser();
            if(user == null) return;
            Player p = Bukkit.getPlayer(user.getName());
            if(p == null) return;
            if(!isVisible(p)) return;
            if(user.getClicks() >= 10700) {
                incrementProgression(p);
            }
        });
    }

    @Override
    public void giveReward(Player player) {

    }
}
