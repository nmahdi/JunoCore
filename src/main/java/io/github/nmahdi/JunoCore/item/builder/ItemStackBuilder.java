package io.github.nmahdi.JunoCore.item.builder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;
    private ArrayList<String> lore = new ArrayList<>();

    public ItemStackBuilder(ItemStack stack){
        this.itemStack = stack.clone();
        meta = itemStack.getItemMeta();
        if(meta.hasLore()){
            lore.addAll(meta.getLore());
        }
    }

    public ItemStackBuilder(Material material) {
        itemStack = new ItemStack(material);
        meta = itemStack.getItemMeta();
    }
    public ItemStackBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        meta = itemStack.getItemMeta();
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public ItemStackBuilder addLore(String... strings){
        for (String  s : strings) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    public ItemStackBuilder addLore(String s) {
        lore.add(ChatColor.translateAlternateColorCodes('&', s));
        return this;
    }

    public ItemStackBuilder skipLore(){
        lore.add("");
        return this;
    }

    public ItemStackBuilder clearLore(){
        lore.clear();
        return this;
    }

    public ItemStackBuilder addLore(ArrayList<String> lore) {
        for (String s : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    public ItemStackBuilder spaceLore() {
        lore.add(" ");
        return this;
    }

    public ItemStackBuilder addFlag(ItemFlag itemFlag) {
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