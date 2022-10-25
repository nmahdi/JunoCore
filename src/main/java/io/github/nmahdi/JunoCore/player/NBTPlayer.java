package io.github.nmahdi.JunoCore.player;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.entity.Player;


public class NBTPlayer extends NBTEntity {

    private NBTCompound juno;
    private NBTCompound stats;
    private NBTCompound skills;

    public NBTPlayer(Player player) {
        super(player);
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
     * @return Juno -> Skills NBT Compound
     */
    public NBTCompound getSkills(){
        return skills;
    }

    /**
     *
     * @param id
     * @return Specific stat based on the ID provided
     */
    public int getStat(PlayerStatID id){
        return getStat(id.getId());
    }

    /**
     *
     * @param id
     * @return Specific stat based on the ID provided
     */
    public int getStat(String id){
        return getStats().getInteger(id);
    }


    /**
     * Sets a player's stat based on the PlayerStatID provided
     *
     * @param id
     * @param stat
     */
    public void setStat(PlayerStatID id, int stat){
        setStat(id.getId(), stat);
    }

    /**
     * Sets a player's stat based on the String provided
     *
     * @param id
     * @param stat
     */
    public void setStat(String id, int stat){
        getStats().setInteger(id, stat);
    }

    public void plusStat(PlayerStatID id, int amount){
        plusStat(id.getId(), amount);
    }

    public void plusStat(String id, int amount){
        setStat(id, getStat(id)+amount);
    }

    public void minusStat(PlayerStatID id, int amount){
        minusStat(id.getId(), amount);
    }

    public void minusStat(String id, int amount){
        setStat(id, getStat(id)-amount);
    }


}
