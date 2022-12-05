package io.github.nmahdi.JunoCore.item.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemStackBuilder implements IItemBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;
    private ArrayList<Component> lore = new ArrayList<>();

    public ItemStackBuilder(ItemStack stack){
        this.itemStack = stack.clone();
        this.meta = itemStack.getItemMeta();
        if(meta.hasLore()) lore.addAll(meta.lore());
    }

    public ItemStackBuilder(Material material){
        this.itemStack = new ItemStack(material);
        this.meta = itemStack.getItemMeta();
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
