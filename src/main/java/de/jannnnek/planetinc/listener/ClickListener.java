package de.jannnnek.planetinc.listener;

import de.jannnnek.planetinc.PlanetInc;
import de.jannnnek.planetinc.gui.upgrade.PlanetMenu;
import de.jannnnek.planetinc.util.PlanetUser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.HashMap;

import static de.nbhd.nevadyapi.messages.Message.send;
import static de.nbhd.nevadyapi.messages.Message.sendActionbar;

public class ClickListener implements Listener {

    public static HashMap<String, Integer> cps = new HashMap<>();
    public static HashMap<String, Double> addedPlunas = new HashMap<>();
    public static HashMap<String, Long> lastClicked = new HashMap<>();

    @EventHandler
    public void onHeadClick(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Block clickedBlock = e.getClickedBlock();
            Player p = e.getPlayer();
            PlanetUser user = PlanetUser.users.get(p.getUniqueId());
            if (clickedBlock != null) {
                if (clickedBlock.getType().equals(Material.PLAYER_HEAD)) {
                    if (clickedBlock.getLocation().equals(new Location(p.getWorld(), 1, 43, 16)) || clickedBlock.getLocation().equals(new Location(p.getWorld(), 7, 36, 16))) {
                        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                            PlanetMenu.getGUI(e.getPlayer()).open(e.getPlayer());
                        } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                            if (user.getPlunasPerClick() == 0.0) {
                                send(p, "§7Du musst zuerst einen Planeten kaufen. Mache dafür einen §f\uE016 §7auf den Planeten.");
                                return;
                            }
                            if(!cps.containsKey(p.getName())) cps.put(p.getName(), 0);
                            cps.put(p.getName(), cps.get(p.getName())+1);
                            if(cps.get(p.getName()) > 7) return;
                            if(!addedPlunas.containsKey(p.getName())) addedPlunas.put(p.getName(), 0.0);
                            addedPlunas.put(p.getName(), addedPlunas.get(p.getName())+user.getPlunasPerClick());
                            lastClicked.put(p.getName(), System.currentTimeMillis()+2000L);
                            sendActionbar(p, "§f\uE013 §l§d"+ PlanetInc.simplifyNumber(addedPlunas.get(p.getName())) + " §7(§b+" + PlanetInc.simplifyNumber(user.getPlunasPerClick()) + "/Klick§7)");
                            if(user.getPlunasPerClick()+user.getPlunas() < Integer.MAX_VALUE) user.setPlunas(user.getPlunas() + user.getPlunasPerClick());
                            user.setClicks(user.getClicks()+1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
        PlanetMenu.getGUI(e.getPlayer()).open(e.getPlayer());
    }

}
