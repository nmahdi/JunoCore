package io.github.nmahdi.JunoCore.gui;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.DescriptionBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTCraftingItem;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public abstract class CraftingGUI extends GUI{

	public CraftingGUI(JCore main, String name, int size, GUI previousMenu) {
		super(main, name, size, previousMenu);
	}

	@Override
	public void onInvClick(InventoryClickEvent e) {
		e.setCancelled(true);
		if(!e.getCurrentItem().hasItemMeta()) return;
		NBTCraftingItem item = new NBTCraftingItem(e.getCurrentItem());

		if(!item.hasJuno() && !item.isRecipeItem()) return;
		Recipe recipe = Recipe.getRecipe(item.getRecipe());
		if(recipe == null) return;
		if(InventoryHelper.hasItems((Player)e.getWhoClicked(), recipe)){

			InventoryHelper.removeItems((Player)e.getWhoClicked(), recipe);
			e.getWhoClicked().getInventory().addItem(ItemBuilder.buildGameItem(recipe.getResult(), recipe.getAmount()));
			e.getWhoClicked().sendMessage(ChatColor.GREEN + "Successfully crafted " + recipe.getResult().getDisplayName() + ".");
			return;
		}
		e.getWhoClicked().sendMessage(ChatColor.RED + "You don't have the required items!");
	}

	@Override
	public void openInventory(Player player) {
		Inventory inventory = createInventory();

		int index = 0;

		for(Recipe recipe : Recipe.values()){
			if(recipe.getMenu() == getMenuType()){
				inventory.setItem(index, buildItem(recipe));
				index++;
			}
		}


		insertFiller(inventory);
		insertBack(inventory);

		player.openInventory(inventory);
	}

	public abstract Recipe.Menu getMenuType();

	protected ItemStack buildItem(Recipe recipe){
		NBTCraftingItem nbt = new NBTCraftingItem(ItemBuilder.buildGameItem(recipe.getResult()));
		nbt.setRecipe(recipe);
		ItemMeta meta = nbt.getItem().getItemMeta();

		ArrayList<Component> description = new ArrayList<>(meta.lore());

		DescriptionBuilder builder = new DescriptionBuilder(true);

		builder.append("Recipe:", TextColors.GRAY_DESCRIPTION).endLine();
		for(Map.Entry<GameItem, Integer> items : recipe.getRecipe().entrySet()) {
			builder.append("- " + items.getKey().getDisplayName() + " x" + items.getValue(), TextColors.GRAY_DESCRIPTION);
		}
		description.addAll(builder.getList());
		meta.lore(description);
		ItemStack temp = nbt.getItem();
		temp.setItemMeta(meta);
		return temp;
	}

}
