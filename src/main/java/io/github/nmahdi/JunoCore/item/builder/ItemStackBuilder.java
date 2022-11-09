package io.github.nmahdi.JunoCore.item.builder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemStackBuilder implements IItemBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;
    private ArrayList<String> lore = new ArrayList<>();

    public ItemStackBuilder(ItemStack stack){
        this.itemStack = stack.clone();
        this.meta = itemStack.getItemMeta();
        if(meta.hasLore()) lore.addAll(meta.getLore());
    }

    public ItemStackBuilder(Material material){
        this.itemStack = new ItemStack(material);
        this.meta = itemStack.getItemMeta();
    }

    @Override
    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    @Override
    public ItemStackBuilder setName(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    @Override
    public ItemStackBuilder addLore(String... lore) {
        for (String  s : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    @Override
    public ItemStackBuilder addLore(String s) {
        lore.add(ChatColor.translateAlternateColorCodes('&', s));
        return this;
    }

    @Override
    public ItemStackBuilder addLore(ArrayList<String> lore) {
        for (String s : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    @Override
    public ItemStackBuilder skipLore() {
        lore.add("");
        return this;
    }

    @Override
    public ItemStackBuilder clearLore() {
        lore.clear();
        return this;
    }

    @Override
    public ItemStackBuilder spaceLore() {
        lore.add(" ");
        return this;
    }

    @Override
    public ItemStackBuilder addFlag(ItemFlag itemFlag) {
        meta.addItemFlags(itemFlag);
        return this;
    }

    @Override
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
