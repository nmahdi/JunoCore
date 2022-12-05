package io.github.nmahdi.JunoCore.item.builder.nbt;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NBTGameItem extends ItemNBT {

    public NBTGameItem(ItemStack item) {
        super(item);
    }


    public boolean hasUUID(){
        return getJuno().hasKey(UUID);
    }

    public String getUUID(){
        return getJuno().getString(UUID);
    }

    public void setUUID(String uuid){
        getJuno().setString(UUID, uuid);
    }


    public String getID(){
        return getJuno().getString(ID);
    }

    public boolean hasID(){
        return getJuno().hasKey(ID);
    }



    //Runes
    public boolean hasRunes(){
        return getJuno().hasKey(RUNE);
    }

    public void createRunes(){
        getJuno().addCompound(RUNE);
    }

    public HashMap<Rune, Integer> getRunes(){
        HashMap<Rune, Integer> map = new HashMap<>();
        for(Rune rune : Rune.values()){
            if(getJuno().getCompound(RUNE).hasKey(rune.toString()))
                map.put(rune, getJuno().getCompound(RUNE).getInteger(rune.toString()));
        }
        return map;
    }

    public int getRunesUsed(){
        int total = 0;
        for(Map.Entry<Rune, Integer> runes : getRunes().entrySet()){
            total+= runes.getKey().getCost()*runes.getValue();
        }
        return total;
    }

    public void addRune(Rune rune){
        if(getRune().hasKey(rune.toString())) {
            getRune().setInteger(rune.toString(), getRune().getInteger(rune.toString())+1);
        }else{
            getRune().setInteger(rune.toString(), 1);
        }
    }

    private NBTCompound getRune(){
        return getJuno().getCompound(RUNE);
    }

}
