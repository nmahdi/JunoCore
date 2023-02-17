package io.github.nmahdi.JunoCore.item.crafting;

import io.github.nmahdi.JunoCore.utils.JunoManager;

import java.util.ArrayList;

public class CraftingManager implements JunoManager {

	private boolean debugMode = false;
	private ArrayList<Recipe> recipes = new ArrayList<>();

	public CraftingManager(){

	}

	public void addRecipe(Recipe recipe){
		this.recipes.add(recipe);
	}

	public Recipe getRecipe(String id){
		for(Recipe recipe : recipes){
			if(recipe.getId().equalsIgnoreCase(id)) return recipe;
		}
		return null;
	}

	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}

	@Override
	public void setDebugMode(boolean mode) {
		this.debugMode = mode;
	}

	@Override
	public boolean isDebugging() {
		return debugMode;
	}

	@Override
	public void onDisable() {

	}

}
