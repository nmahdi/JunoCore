package io.github.nmahdi.JunoCore.item.builder.nbt;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class ItemNBT extends NBTItem {

	public final String JUNO = "juno";
	public final String ID = "id";
	public final String UUID = "uuid";
	public final String RUNE = "runes";
	public final String RECIPE = "recipe";
	public final String SHOP = "shop";

	public ItemNBT(ItemStack item) {
		super(item);
	}

	public void init(String id){
		addCompound(JUNO).setString(ID, id);
	}

	public boolean hasJuno(){
		return hasTag(JUNO);
	}

	public NBTCompound getJuno(){
		return getOrCreateCompound(JUNO);
	}

}
