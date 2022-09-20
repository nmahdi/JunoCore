package com.junocore.player;

import com.junocore.JCore;
import com.junocore.player.skills.SkillID;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class JPlayerManager {

    private File userFolder;

    public JPlayerManager(JCore jCore){
        userFolder = new File(jCore.getDataFolder(), "players");
        if(!userFolder.exists()) userFolder.mkdirs();
    }

    public void login(Player player){
        if(userFolder.length() < 0){
            System.out.println("NO USERS REGISTERED!");
        }
        for(File f : userFolder.listFiles()){
            if(f.getName().replace(".yml", "").equalsIgnoreCase(player.getUniqueId().toString())){
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                load(player, c, false);
                return;
            }
        }
        File f = new File(userFolder, player.getUniqueId().toString()+".yml");
        try {
            f.createNewFile();
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            load(player, c, true);
            save(player, c, f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load(Player player, FileConfiguration c, boolean newPlayer){
        NBTEntity nPlayer = new NBTEntity(player);
        NBTCompound juno = nPlayer.getPersistentDataContainer().getOrCreateCompound("juno");
        NBTCompound skills = juno.getOrCreateCompound("skills");
        NBTCompound stats = juno.getOrCreateCompound("stats");
        //Juno MISC
        for (SkillID skill : SkillID.values()) {
            if (!newPlayer) {
                skills.getCompound(skill.getId()).setInteger("level", calcXP(c.getInt("skills." + skill.getId() + ".level") - 1));
                skills.getCompound(skill.getId()).setLong("xp", c.getLong("skills." + skill.getId() + ".xp"));
            }else{
                skills.getCompound(skill.getId()).setInteger("level", 1);
                skills.getCompound(skill.getId()).setLong("xp", 0L);
            }
        }
        //Stats
        stats.setInteger(PlayerStatID.Health.getId(), 100);
        stats.setInteger(PlayerStatID.MaxHealth.getId(), 100);
        stats.setInteger(PlayerStatID.Mana.getId(), 100);
        stats.setInteger(PlayerStatID.MaxMana.getId(), 100);
        stats.setInteger(PlayerStatID.Defense.getId(), 0);
        stats.setInteger(PlayerStatID.FireDefense.getId(), 0);
        stats.setInteger(PlayerStatID.WaterDefense.getId(), 0);
        stats.setInteger(PlayerStatID.IceDefense.getId(), 0);
        stats.setInteger(PlayerStatID.Speed.getId(), 100);
        stats.setDouble(PlayerStatID.Damage.getId(), 1d);
        stats.setInteger(PlayerStatID.Strength.getId(), 0);
        stats.setInteger(PlayerStatID.CritChance.getId(), 10);
        stats.setInteger(PlayerStatID.CritDamage.getId(), 20);
    }

    public void logout(Player player){
        if(userFolder.length() < 0){
            System.out.println("NO USERS REGISTERED!");
        }
        for(File f : userFolder.listFiles()){
            if(f.getName().replace(".yml", "").equalsIgnoreCase(player.getUniqueId().toString())) {
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                save(player, c, f);
            }
        }
    }

    private void save(Player player, FileConfiguration c, File f){
        NBTEntity nPlayer = new NBTEntity(player);
        NBTCompound juno = nPlayer.getPersistentDataContainer().getOrCreateCompound("juno");
        NBTCompound skills = juno.getOrCreateCompound("skills");
        NBTCompound stats = juno.getOrCreateCompound("stats");
        c.set("coins", juno.getInteger("coins"));
        for(SkillID skill : SkillID.values()){
            c.set("skills."+skill.getId()+".level", skills.getCompound(skill.getId()).getInteger("level"));
            c.set("skills."+skill.getId()+".xp", skills.getCompound(skill.getId()).getInteger("xp"));
        }
        try {
            c.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int calcXP(int nextLevel){
        if(nextLevel == 40) return nextLevel*100000;
        if(nextLevel >= 30) return nextLevel*10000;
        if(nextLevel >= 20) return nextLevel*5000;
        if(nextLevel >= 10) return nextLevel*2000;
        return nextLevel*1000;
    }

}
