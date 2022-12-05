package io.github.nmahdi.JunoCore.item.builder.nbt;

import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import org.bukkit.inventory.ItemStack;

public class NBTCraftingItem extends ItemNBT{

	public NBTCraftingItem(ItemStack item) {
		super(item);
	}

	public void setRecipe(Recipe recipe){
		getJuno().setString(RECIPE, recipe.getId());
	}

	public boolean isRecipeItem(){
		return getJuno().hasKey(RECIPE);
	}

	public String getRecipe(){
		return getJuno().getString(RECIPE);
	}
}
