package de.jannnnek.planetinc.gui;

import de.jannnnek.planetinc.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    protected ItemStack itemStack;

    protected ItemMeta itemMeta;

    public ItemBuilder(ItemStack is) {
        this.itemStack = is.clone();
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material m) {
        this.itemStack = new ItemStack(m);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material m, int amount) {
        this.itemStack = new ItemStack(m, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public static ItemStack getPlaceholderRed() {
        ItemStack i = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(" ");
        i.setItemMeta(m);
        return i;
    }

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder addTippedPotionEffect(PotionType potionType, boolean extended, boolean upgraded) {
        ((PotionMeta) this.itemMeta).setBasePotionData(new PotionData(potionType, extended, upgraded));
        return this;
    }

    public ItemBuilder setGoatHornType(MusicInstrument goatHornType) {
        ((MusicInstrumentMeta) this.itemMeta).setInstrument(goatHornType);
        return this;
    }

    public ItemBuilder addBannerPattern(Pattern... pattern) {
        for(Pattern pat : pattern) {
            ((BannerMeta) this.itemMeta).addPattern(pat);
        }
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        this.itemMeta.setCustomModelData(customModelData);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        this.itemMeta.addItemFlags(new ItemFlag[] { itemFlag });
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchant, int lvl) {
        this.itemMeta.addEnchant(enchant, lvl, true);
        return this;
    }

    public ItemBuilder setEnchanted() {
        this.itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder addStoredEnchantment(Enchantment enchant, int lvl) {
        ((EnchantmentStorageMeta) this.itemMeta).addStoredEnchant(enchant, lvl, true);
        return this;
    }

    public ItemBuilder setColor(Color c) {
        ((LeatherArmorMeta)this.itemMeta).setColor(c);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

    public static ItemStack getPlaceholder() {
        ItemStack i = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(" ");
        i.setItemMeta(m);
        return i;
    }

    public static ItemStack getPlaceholderBlue() {
        ItemStack i = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(" ");
        i.setItemMeta(m);
        return i;
    }

    public static ItemStack getPlaceholderLight() {
        ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(" ");
        i.setItemMeta(m);
        return i;
    }

    public static ItemStack getSkull(String SkullName, String SkullOwner) {
        if(Bukkit.getOfflinePlayer(SkullOwner).hasPlayedBefore() || SkullOwner.startsWith("MHF_") || Bukkit.getPlayer(SkullOwner) != null) {
            ItemStack i = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta m = (SkullMeta) i.getItemMeta();
            m.setDisplayName(SkullName);
            m.setOwningPlayer(Bukkit.getOfflinePlayer(SkullOwner));
            i.setItemMeta((ItemMeta) m);
            return i;
        } else {
            return new ItemStack(Material.PLAYER_HEAD);
        }
    }

    public static ItemStack getSkull(String SkullName, String SkullOwner, String... lore) {
        if(Bukkit.getOfflinePlayer(SkullOwner).hasPlayedBefore() || SkullOwner.startsWith("MHF_") || Bukkit.getPlayer(SkullOwner) != null) {
            ItemStack i = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta m = (SkullMeta) i.getItemMeta();
            m.setDisplayName(SkullName);
            if (lore != null)
                m.setLore(Arrays.asList(lore));
            m.setOwningPlayer(Bukkit.getOfflinePlayer(SkullOwner));
            i.setItemMeta((ItemMeta) m);
            return i;
        } else {
            return new ItemStack(Material.PLAYER_HEAD);
        }
    }
    public static ItemStack getForceSkull(String SkullName, String SkullOwner) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta m = (SkullMeta) i.getItemMeta();
        m.setDisplayName(SkullName);
        m.setOwningPlayer(Bukkit.getOfflinePlayer(SkullOwner));
        i.setItemMeta((ItemMeta) m);
        return i;
    }

    public static ItemStack getForceSkull(String SkullName, String SkullOwner, String... lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta m = (SkullMeta) i.getItemMeta();
        m.setDisplayName(SkullName);
        if (lore != null)
            m.setLore(Arrays.asList(lore));
        m.setOwningPlayer(Bukkit.getOfflinePlayer(SkullOwner));
        i.setItemMeta((ItemMeta) m);
        return i;
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

    public static ItemStack getSkullFromValueWithLoreList(String Name, String base64, String[] Lore) {
        ItemStack is = SkullCreator.itemWithBase64(SkullCreator.createSkull(), base64);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Name);
        if (Lore != null)
            im.setLore(Arrays.asList(Lore));
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getSkullFromValueWithoutLore(String Name, String base64) {
        ItemStack is = SkullCreator.itemWithBase64(SkullCreator.createSkull(), base64);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Name);
        is.setItemMeta(im);
        return is;
    }
}
