package io.github.nmahdi.JunoCore.player;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.skills.SkillID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JPlayerManager {

    private JCore main;
    private File userFolder;
    private HashMap<Player, Integer> actionBars = new HashMap<>();

    public JPlayerManager(JCore main){
        this.main = main;
        userFolder = new File(main.getDataFolder(), "players");
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
                skills.getOrCreateCompound(skill.getId()).setInteger("level", c.getInt("skills."+ skill.getId() + ".level"));
                skills.getOrCreateCompound(skill.getId()).setLong("xp", c.getLong("skills." + skill.getId() + ".xp"));
            }else{
                skills.getOrCreateCompound(skill.getId()).setInteger("level", 1);
                skills.getOrCreateCompound(skill.getId()).setLong("xp", 0L);
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
        stats.setInteger(PlayerStatID.LightningDefense.getId(), 0);
        stats.setInteger(PlayerStatID.IceDefense.getId(), 0);
        stats.setInteger(PlayerStatID.Speed.getId(), 100);
        stats.setInteger(PlayerStatID.Damage.getId(), 1);
        stats.setInteger(PlayerStatID.Strength.getId(), 0);
        stats.setInteger(PlayerStatID.CritChance.getId(), 10);
        stats.setInteger(PlayerStatID.CritDamage.getId(), 20);
        juno.setObject("action_bar", new ActionBar(player));
        actionBars.put(player, Bukkit.getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                juno.getObject("action_bar", ActionBar.class).send();
            }
        },20, 20));
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
            c.set("skills."+skill.getId()+".xp", skills.getCompound(skill.getId()).getLong("xp"));
        }
        try {
            c.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int calcXP(int nextLevel){
        if(nextLevel <= 0) return 100;
        if(nextLevel == 40) return nextLevel*100000;
        if(nextLevel >= 30) return nextLevel*10000;
        if(nextLevel >= 20) return nextLevel*5000;
        if(nextLevel >= 10) return nextLevel*2000;
        return nextLevel*1000;
    }

}
