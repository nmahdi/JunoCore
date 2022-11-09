package io.github.nmahdi.JunoCore.item.builder;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public interface IItemBuilder {

    public IItemBuilder setAmount(int amount);
    public IItemBuilder setName(String name);
    public IItemBuilder addLore(String... lore);
    public IItemBuilder addLore(String s);
    public IItemBuilder addLore(ArrayList<String> lore);
    public IItemBuilder skipLore();
    public IItemBuilder clearLore();
    public IItemBuilder spaceLore();
    public IItemBuilder addFlag(ItemFlag itemFlag);
    public ItemStack build();

}
