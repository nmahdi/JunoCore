package io.github.nmahdi.JunoCore.item.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class SkullItemBuilder {

    private ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);;
    private SkullMeta meta;
    private ArrayList<String> lore = new ArrayList<>();

    public SkullItemBuilder(String texture){
        meta = (SkullMeta) itemStack.getItemMeta();
        meta.setPlayerProfile(Bukkit.createProfile(UUID.randomUUID(), null));
        PlayerProfile profile = meta.getPlayerProfile();
        profile.getProperties().add(new ProfileProperty("textures", texture));
        meta.setPlayerProfile(profile);
    }

    public SkullItemBuilder(Player owner){
        meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwningPlayer(owner);
    }

    public SkullItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public SkullItemBuilder setName(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public SkullItemBuilder addLore(String... strings){
        for (String  s : strings) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    public SkullItemBuilder addLore(String s) {
        lore.add(ChatColor.translateAlternateColorCodes('&', s));
        return this;
    }

    public SkullItemBuilder skipLore(){
        lore.add("");
        return this;
    }

    public SkullItemBuilder clearLore(){
        lore.clear();
        return this;
    }

    public SkullItemBuilder addLore(ArrayList<String> lore) {
        for (String s : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    public SkullItemBuilder spaceLore() {
        lore.add(" ");
        return this;
    }

    public SkullItemBuilder addFlag(ItemFlag itemFlag) {
        meta.addItemFlags(itemFlag);
        return this;
    }

    public ItemStack build() {
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
