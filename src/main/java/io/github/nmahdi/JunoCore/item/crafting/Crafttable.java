package io.github.nmahdi.JunoCore.item.crafting;

import java.util.ArrayList;

public interface Crafttable {

	ArrayList<Recipe> recipes = new ArrayList<>();

	default ArrayList<Recipe> getRecipes(){
		return recipes;
	}

}
