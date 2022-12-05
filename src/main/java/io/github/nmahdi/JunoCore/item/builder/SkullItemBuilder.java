package io.github.nmahdi.JunoCore.item.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class SkullItemBuilder implements IItemBuilder {

    private ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
    private SkullMeta meta;
    private ArrayList<Component> lore = new ArrayList<>();

    public SkullItemBuilder(String texture){
        meta = (SkullMeta) itemStack.getItemMeta();
        meta.setPlayerProfile(Bukkit.createProfile(UUID.fromString("d7befc97-75f6-45cc-b327-f781a207f641"), "Ramiris"));
        PlayerProfile profile = meta.getPlayerProfile();
        profile.getProperties().add(new ProfileProperty("textures", texture));
        meta.setPlayerProfile(profile);
    }

    public SkullItemBuilder(Player owner){
        meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwningPlayer(owner);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public ItemMeta getItemMeta() {
        return meta;
    }

    @Override
    public ArrayList<Component> getLore() {
        return lore;
    }


}
