package io.github.nmahdi.JunoCore.gui;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.DescriptionBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTCraftingItem;
import io.github.nmahdi.JunoCore.item.crafting.CraftingManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
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

	private ItemManager itemManager;
	private CraftingManager craftingManager;

	public CraftingGUI(JCore main, String name, int size, GUI previousMenu) {
		super(main, name, size, previousMenu);
		this.itemManager = main.getItemManager();
		this.craftingManager = itemManager.getCraftingManager();
	}

	@Override
	public void onInvClick(InventoryClickEvent e) {
		e.setCancelled(true);
		if(!e.getCurrentItem().hasItemMeta()) return;
		NBTCraftingItem item = new NBTCraftingItem(e.getCurrentItem());

		if(!item.hasJuno() && !item.isRecipeItem()) return;
		Recipe recipe = craftingManager.getRecipe(item.getRecipe());
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
	public void setItems(Inventory inventory, Player player) {
		int index = 0;

		for(GameItem gameItem : itemManager.getItems()){
			if(gameItem instanceof Crafttable){
				for(Recipe recipe : ((Crafttable)gameItem).getRecipes()){
					inventory.setItem(index, buildItem(recipe));
				}
			}
		}

		insertFiller(inventory);
		insertBack(inventory);
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
