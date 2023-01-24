package io.github.nmahdi.JunoCore.item.crafting;

import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.HashMap;

public enum Recipe {
    CompactedCobblestone(new RecipeContainer(GameItem.CompactedCobblestone, 1, Menu.Materials).addItems(GameItem.Cobblestone, 160)),
    CompactedIronBar(new RecipeContainer(GameItem.IronBar, 1, Menu.Materials).addItems(GameItem.IronBar, 160))
    ;

    private RecipeContainer recipeContainer;

    Recipe(RecipeContainer recipeContainer){

        this.recipeContainer = recipeContainer;
    }

    public String getId() {
        return recipeContainer.id;
    }

    public GameItem getResult() {
        return recipeContainer.result;
    }

    public int getAmount() {
        return recipeContainer.amount;
    }

    public Menu getMenu() {
        return recipeContainer.menu;
    }

    public HashMap<GameItem, Integer> getRecipe() {
        return recipeContainer.recipe;
    }

    public static Recipe getRecipe(String id){
        for(Recipe recipe : Recipe.values()){
            if(recipe.getId().equals(id)) return recipe;
        }
        return null;
    }

    public static enum Menu{
        Materials,
        Tools,
        Weapons,
        Armor;
    }

}
