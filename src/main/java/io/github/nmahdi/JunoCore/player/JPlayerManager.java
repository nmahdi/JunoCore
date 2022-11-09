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
        NBTPlayer nPlayer = new NBTPlayer(player, true);
        //Juno MISC
        for (SkillID skill : SkillID.values()) {
            if (!newPlayer) {
                nPlayer.getSkills().getOrCreateCompound(skill.getId()).setInteger("level", c.getInt("skills."+ skill.getId() + ".level"));
                nPlayer.getSkills().getOrCreateCompound(skill.getId()).setLong("xp", c.getLong("skills." + skill.getId() + ".xp"));
            }else{
                nPlayer.getSkills().getOrCreateCompound(skill.getId()).setInteger("level", 1);
                nPlayer.getSkills().getOrCreateCompound(skill.getId()).setLong("xp", 0L);
            }
        }
        for(PlayerStatID stat : PlayerStatID.values()){
            nPlayer.getStats().setInteger(stat.getId(), (int)stat.getBaseValue());
        }
        player.setHealth(20);
        player.setFoodLevel(20);
        actionBars.put(player, Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
            ActionBar bar = new ActionBar(player);
            @Override
            public void run() {
                bar.send();
            }
        }, 20, 20));
    }

    public void logout(Player player){
        if(userFolder.length() < 0){
            System.out.println("NO USERS REGISTERED!");
        }
        if(!actionBars.isEmpty()) {
            Bukkit.getServer().getScheduler().cancelTask(actionBars.get(player));
            actionBars.remove(player);
        }
        for(File f : userFolder.listFiles()){
            if(f.getName().replace(".yml", "").equalsIgnoreCase(player.getUniqueId().toString())) {
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                save(player, c, f);
            }
        }
    }

    private void save(Player player, FileConfiguration c, File f){
        NBTPlayer nPlayer = new NBTPlayer(player);
        c.set("coins", nPlayer.getJuno().getInteger("coins"));
        for(SkillID skill : SkillID.values()){
            c.set("skills."+skill.getId()+".level", nPlayer.getSkills().getCompound(skill.getId()).getInteger("level"));
            c.set("skills."+skill.getId()+".xp", nPlayer.getSkills().getCompound(skill.getId()).getLong("xp"));
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
