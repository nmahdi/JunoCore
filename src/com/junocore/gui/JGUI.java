package com.junocore.gui;

import com.junocore.item.builder.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class JGUI {

    public static final ItemStack EMPTY = new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();

    private String name;
    private int size;
    private HashMap<String, Integer> items = new HashMap<>();
    private boolean filler;

    public JGUI(String name, int sizeInRows, boolean filler){
        this.name = name;
        this.size = sizeInRows*9;
        this.filler = filler;
    }

    public JGUI addItem(String item, int slot){
        this.items.put(item, slot);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public boolean hasFiller() {
        return filler;
    }

    public void setFiller(boolean filler) {
        this.filler = filler;
    }
}
