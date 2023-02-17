package io.github.nmahdi.JunoCore.item.crafting;

import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.HashMap;

public class Recipe {

	public String id;
	public GameItem result;
	public int amount;
	public Menu menu;
	public HashMap<GameItem, Integer> recipe = new HashMap<>();

	public Recipe(CraftingManager craftingManager, GameItem result, int amount, Recipe.Menu menu){
		this.id = result.getId();
		this.result = result;
		this.amount = amount;
		this.menu = menu;
		craftingManager.addRecipe(this);
	}

	public Recipe addItems(GameItem item, int amount){
		recipe.put(item, amount);
		return this;
	}

	public String getId() {
		return id;
	}

	public GameItem getResult() {
		return result;
	}

	public int getAmount() {
		return amount;
	}

	public Menu getMenu() {
		return menu;
	}

	public HashMap<GameItem, Integer> getRecipe() {
		return recipe;
	}

	public static enum Menu{
		Materials,
		Tools,
		Weapons,

	}

}
