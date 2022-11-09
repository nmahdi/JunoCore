package io.github.nmahdi.JunoCore.player;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.entity.Player;


public class NBTPlayer extends NBTEntity {

    private Player player;
    private NBTCompound juno;
    private NBTCompound stats;
    private NBTCompound skills;

    public NBTPlayer(Player player) {
        super(player);
        this.player = player;
        juno = getPersistentDataContainer().getOrCreateCompound("juno");
        stats = juno.getOrCreateCompound("stats");
        skills = juno.getOrCreateCompound("skills");
    }

    public NBTPlayer(Player player, boolean join){
        super(player);
        if(hasKey("juno") && join) removeKey("juno");
        this.player = player;
        juno = getPersistentDataContainer().getOrCreateCompound("juno");
        stats = juno.getOrCreateCompound("stats");
        skills = juno.getOrCreateCompound("skills");
    }

    /**
     *
     * @return Juno NBT Compound
     */
    public NBTCompound getJuno(){
        return juno;
    }

    /**
     *
     * @return Juno -> Stats NBT Compound
     */
    public NBTCompound getStats(){
        return stats;
    }

    /**
     *
     * @param id
     * @return Total stat value for the specified stat (Base & Equipment)
     */
    public int getStat(PlayerStatID id){
        return getStats().getInteger(id.getId());
    }

    public int getHealth(){
        return getStat(PlayerStatID.Health);
    }

    public void setHealth(int health){
        getStats().setInteger(PlayerStatID.Health.getId(), health);
    }

    public void plusHealth(int amount){
        setHealth(getHealth()+amount);
    }

    public void minusHealth(int amount){
        setHealth(getHealth()-amount);
    }

    public int getMaxHealth(){
        return getStat(PlayerStatID.MaxHealth);
    }

    public int getMana(){
        return getStat(PlayerStatID.Mana);
    }

    public void setMana(int mana){
        getStats().setInteger(PlayerStatID.Mana.getId(), mana);
    }

    public void plusMana(int amount){
        setMana(getMana()+amount);
    }

    public void minusMana(int amount){
        setMana(getMana()-amount);
    }

    public int getMaxMana(){
        return getStat(PlayerStatID.MaxMana);
    }

    public int getDefense(){
        return getStat(PlayerStatID.Defense);
    }

    public int getFireDefense(){
        return getStat(PlayerStatID.FireDefense);
    }

    public int getWaterDefense(){
        return getStat(PlayerStatID.WaterDefense);
    }

    public int getLightningDefense(){
        return getStat(PlayerStatID.LightningDefense);
    }

    public int getIceDefense(){
        return getStat(PlayerStatID.IceDefense);
    }

    public int getSpeed(){
        return getStat(PlayerStatID.Speed);
    }

    public int getDamage(){
        return getStat(PlayerStatID.Damage);
    }

    public int getStrength(){
        return getStat(PlayerStatID.Strength);
    }

    public int getCritDamage(){
        return getStat(PlayerStatID.CritDamage);
    }

    public int getCritChance(){
        return getStat(PlayerStatID.CritChance);
    }

    public int getLuck(){
        return getStat(PlayerStatID.Luck);
    }

    public int getFortune(){
        return getStat(PlayerStatID.Fortune);
    }


    /**
     *
     * @return Juno -> Skills NBT Compound
     */
    public NBTCompound getSkills(){
        return skills;
    }

    public Player getPlayer() {
        return player;
    }
}
