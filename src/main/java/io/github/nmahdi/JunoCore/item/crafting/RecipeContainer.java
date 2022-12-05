package io.github.nmahdi.JunoCore.item.crafting;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;

import java.util.HashMap;

public class RecipeContainer {

	public String id;
	public GameItem result;
	public int amount;
	public Recipe.Menu menu;
	public HashMap<GameItem, Integer> recipe = new HashMap<>();

	public RecipeContainer(GameItem result, int amount, Recipe.Menu menu){
		this.id = result.getId();
		this.result = result;
		this.amount = amount;
		this.menu = menu;
	}

	public RecipeContainer addItems(GameItem item, int amount){
		recipe.put(item, amount);
		return this;
	}

}
