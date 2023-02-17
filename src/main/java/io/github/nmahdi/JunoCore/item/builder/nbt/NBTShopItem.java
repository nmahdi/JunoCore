package io.github.nmahdi.JunoCore.item.builder.nbt;

import io.github.nmahdi.JunoCore.item.GameItem;
import org.bukkit.inventory.ItemStack;

public class NBTShopItem extends ItemNBT{

	public NBTShopItem(ItemStack item) {
		super(item);
	}

	public void setInfo(int amount){
		getJuno().setInteger(SHOP, amount);
	}

	public boolean isShopItem() {
		return getJuno().hasTag(SHOP);
	}

	public String getItemID(){
		return getJuno().getString(ID);
	}

	public int getAmount(){
		return getJuno().getInteger(SHOP);
	}

}
