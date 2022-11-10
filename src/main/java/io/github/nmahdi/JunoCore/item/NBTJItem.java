package io.github.nmahdi.JunoCore.item;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.stats.ConsumableID;
import io.github.nmahdi.JunoCore.item.stats.EquipmentSlotID;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.item.stats.WeaponType;
import org.bukkit.inventory.ItemStack;

public class NBTJItem extends NBTItem {

    public NBTJItem(ItemStack item) {
        super(item);
    }

    //Juno
    public boolean hasJuno(){
        return hasKey("juno");
    }

    public NBTCompound getJuno(){
        return getOrCreateCompound("juno");
    }

    //Stats
    public boolean hasStats(){ return getJuno().hasKey("stats"); }

    public NBTCompound getStats(){
        return getJuno().getOrCreateCompound("stats");
    }

    public boolean isConsumable(){
        return getJuno().hasKey("consumable");
    }

    public NBTCompound getConsumableStats(){
        return getJuno().getOrCreateCompound("consumable");
    }

    //Normal Stats


    //Hidden Stats

    public boolean isWeapon(){
        return getJuno().hasKey(ItemStatID.WeaponType.getID());
    }

    public WeaponType getWeaponType(){
        return WeaponType.getWeaponType(getJuno().getString(ItemStatID.WeaponType.getID()));
    }

    public void setWeaponType(WeaponType weaponType){
        getJuno().setString(ItemStatID.WeaponType.getID(), weaponType.getId());
    }

    public boolean isEquipment(){
        return getJuno().hasKey(ItemStatID.EquipmentSlot.getID());
    }

    public EquipmentSlotID getEquipmentSlot(){
        return EquipmentSlotID.getSlot(getJuno().getString(ItemStatID.EquipmentSlot.getID()));
    }

    public void setEquipmentSlot(EquipmentSlotID slot){
        getJuno().setString(ItemStatID.EquipmentSlot.getID(), slot.getId());
    }

    public boolean canDismantle(){
        return getJuno().hasKey(ItemStatID.Dismantlable.getID());
    }

    public void setDismantlable(){
        getJuno().setBoolean(ItemStatID.Dismantlable.getID(), true);
    }

    public boolean hasUUID(){
        return getJuno().hasKey(ItemStatID.UUID.getID());
    }

    public String getUUID(){
        return getJuno().getString("uuid");
    }

    public void setUUID(String uuid){
        getJuno().setString("uuid", uuid);
    }

    //Consumables
    public void setConsumableStat(ConsumableID id, int value){
        getConsumableStats().setInteger(id.getPlayerID().getId(), value);
    }

    public int getConsumableStat(ConsumableID id){
        return getConsumableStats().getInteger(id.getPlayerID().getId());
    }

    //Crafting
    public boolean isRecipeItem(){
        return getJuno().hasKey("recipe");
    }

    public String getRecipe(){
        return getJuno().getString("recipe");
    }

    //Helper
    public void setStat(ItemStatID stat, String value){
        getStats().setString(stat.getID(), value);
    }

    public String getStat(ItemStatID id){
        return getStat(id.getID());
    }

    public String getStat(String id){
        return getStats().getString(id);
    }

    public int getDamage(){
        return Integer.parseInt(getStat(ItemStatID.Damage));
    }

    public String getID(){
        return getJuno().getString("id");
    }

}
