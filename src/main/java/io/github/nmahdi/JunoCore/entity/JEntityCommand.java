package io.github.nmahdi.JunoCore.entity;

import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class JEntityCommand implements CommandExecutor {

    private JEntityManager entityManager;

    public JEntityCommand(JEntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("jentity")){
            if(! (sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "You aren't a player!");
                return true;
            }
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "Invalid arguments.");
                return true;
            }
            if(args[0].equalsIgnoreCase("spawn")){
                if(!entityManager.getSpawnZones().isEmpty()){
                    for(SpawnZone zone : entityManager.getSpawnZones()){
                        zone.spawn(entityManager);
                    }
                }
            }
            if(args[0].equalsIgnoreCase("create")){
                //jentity create ID EntityID SpawnNumber SpawnDelay
                entityManager.createNewSpawnZone((Player)sender, ((Player) sender).getWorld(), args[1], JEntity.valueOf(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            }
        }
        return false;
    }

}
