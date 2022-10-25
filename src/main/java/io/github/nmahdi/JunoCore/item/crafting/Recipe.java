package io.github.nmahdi.JunoCore.item.crafting;

import io.github.nmahdi.JunoCore.item.JItem;

import java.util.HashMap;

public enum Recipe {
    RookieSword(JItem.RookieSword, JItem.IronBar, JItem.IronBar, JItem.CompactedIron),
    TreeFeller(JItem.TreeFeller, JItem.CompactedGold, JItem.CompactedIron)
    ;


    private HashMap<JItem, Integer> items = new HashMap<>();
    private String id;
    private JItem result;

    Recipe(JItem... items){
        this.result = items[0];
        this.id = result.getId();
        for(int i = 1; i < items.length; i++){
            if(!this.items.containsKey(items[i])){
                this.items.put(items[i], 1);
            }else{
                this.items.put(items[i], this.items.get(items[i])+1);
            }
        }
    }

    public HashMap<JItem, Integer> getItems() {
        return items;
    }

    public String getId() {
        return id;
    }

    public JItem getResult() {
        return result;
    }

}
