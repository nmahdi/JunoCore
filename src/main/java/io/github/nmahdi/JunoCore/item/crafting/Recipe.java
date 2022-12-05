package io.github.nmahdi.JunoCore.item.crafting;

import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.HashMap;

public enum Recipe {
    RookieSword(new RecipeContainer(GameItem.RookieSword, 1 ,Recipe.Menu.Weapons).addItems(GameItem.CompactedIron, 2).addItems(GameItem.Stick, 1)),

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
