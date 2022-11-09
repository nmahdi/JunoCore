package io.github.nmahdi.JunoCore.item.stats;

import java.util.HashMap;

public class StatContainer {

    /*
        Stat container
        A stat container that contains all stats instead of just one.
        Said container can take in ItemStatID EquipmentSlotID & ConsumableID
        A system to parse stats


        NBT Items
        Make an interface tree INBTItem -> IMenuItem || IGameItem -> IWeaponItem || IArmorItem || IConsumableItem etc...
        Said interfaces will make it easier with item checks and sorting items.


     */

    private boolean dismantlable = false;
    private WeaponType weapon;
    private EquipmentSlotID equipmentSlot;

    private HashMap<ItemStatID, String> stats = new HashMap<>();
    private HashMap<ConsumableID, Integer> consumableStats = new HashMap<>();

    //Stats

    public StatContainer setHealth(int value){
        return setInt(ItemStatID.MaxHealth, value);
    }

    public int getHealth(){
        return getInt(ItemStatID.MaxHealth);
    }

    public StatContainer setMana(int value){
        return setInt(ItemStatID.MaxMana, value);
    }

    public int getMana(){
        return getInt(ItemStatID.MaxMana);
    }

    public StatContainer setDefense(int value){
        return setInt(ItemStatID.Defense, value);
    }

    public int getDefense(){
        return getInt(ItemStatID.Defense);
    }

    public StatContainer setFireDefense(int value){
        return setInt(ItemStatID.FireDefense, value);
    }

    public int getFireDefense(){
        return getInt(ItemStatID.FireDefense);
    }

    public StatContainer setWaterDefense(int value){
        return setInt(ItemStatID.WaterDefense, value);
    }

    public int getWaterDefense(){
        return getInt(ItemStatID.WaterDefense);
    }

    public StatContainer setLightningDefense(int value){
        return setInt(ItemStatID.LightningDefense, value);
    }

    public int getLightningDefense(){
        return getInt(ItemStatID.LightningDefense);
    }

    public StatContainer setIceDefense(int value){
        return setInt(ItemStatID.IceDefense, value);
    }

    public int getIceDefense(){
        return getInt(ItemStatID.IceDefense);
    }

    public StatContainer setSpeed(int value){
        return setInt(ItemStatID.Speed, value);
    }

    public int getSpeed(){
        return getInt(ItemStatID.Speed);
    }

    public StatContainer setDamage(int value){
        return setInt(ItemStatID.Damage, value);
    }

    public int getDamage(){
        return getInt(ItemStatID.Damage);
    }

    public StatContainer setStrength(int value){
        return setInt(ItemStatID.Strength, value);
    }

    public int getStrength(){
        return getInt(ItemStatID.Strength);
    }

    public StatContainer setCritChance(int value){
        return setInt(ItemStatID.CritChance, value);
    }

    public int getCritChance(){
        return getInt(ItemStatID.CritChance);
    }

    public StatContainer setCritDamage(int value){
        return setInt(ItemStatID.CritDamage, value);
    }

    public int getCritDamage(){
        return getInt(ItemStatID.CritDamage);
    }

    public StatContainer setBreakingPower(int value){
        return setInt(ItemStatID.BreakingPower, value);
    }

    public int getBreakingPower(){
        return getInt(ItemStatID.BreakingPower);
    }

    //Equipment

    public boolean isEquipment(){
        return equipmentSlot != null;
    }

    public StatContainer setEquipmentSlot(EquipmentSlotID equipmentSlot){
        this.equipmentSlot = equipmentSlot;
        return this;
    }

    public EquipmentSlotID getEquipmentSlot(){
        return equipmentSlot;
    }

    //Weapon

    public boolean isWeapon() {
        return weapon != null;
    }

    public StatContainer setWeaponType(WeaponType weaponType){
        this.weapon = weaponType;
        return this;
    }

    public WeaponType getWeaponType(){
        return weapon;
    }

    //Dismantle

    public boolean isDismantlable() {
        return dismantlable;
    }

    public StatContainer setDismantlable(){
        dismantlable = true;
        return this;
    }

    //Consumable

    public boolean isConsumable() {
        return !getConsumableStats().isEmpty();
    }

    public StatContainer setConsumable(ConsumableID id, int value){
        consumableStats.put(id, value);
        return this;
    }

    public int getConsumableStat(ConsumableID consumableID){
        return consumableStats.get(consumableID);
    }

    public boolean hasConsumableStat(ConsumableID consumableID){
        return consumableStats.containsKey(consumableID);
    }

    public HashMap<ConsumableID, Integer> getConsumableStats() {
        return consumableStats;
    }


    //Helper methods

    public boolean hasStat(ItemStatID id){
        return stats.containsKey(id);
    }

    public HashMap<ItemStatID, String> getStats() {
        return stats;
    }

    public String getStat(ItemStatID id){
        return stats.get(id);
    }

    private StatContainer setInt(ItemStatID id, int value){
        stats.put(id, String.valueOf(value));
        return this;
    }

    private int getInt(ItemStatID id){
        return Integer.parseInt(getStat(id));
    }

}
