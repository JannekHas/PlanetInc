package de.jannnnek.planetinc.command;

import de.jannnnek.planetinc.util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GetPlanetHeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.getInventory().addItem(getSkullFromValue("§dPlanet§fInc",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjgxNzlhZjE4YjE2OWY2ODQzYTZkNzE3Y2M4MzkzNDNlYzEwMjExNzY3NWJiZGQxM2Y3ZjIxNTNmNDRlOTRhIn19fQ==", ""));
        }
        return false;
    }

    public static ItemStack getSkullFromValue(String Name, String base64, String... Lore) {
        ItemStack is = SkullCreator.itemWithBase64(SkullCreator.createSkull(), base64);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Name);
        if (Lore != null)
            im.setLore(Arrays.asList(Lore));
        is.setItemMeta(im);
        return is;
    }

}
