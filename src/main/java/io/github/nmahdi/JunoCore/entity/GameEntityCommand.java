package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZone;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZoneManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class GameEntityCommand implements CommandExecutor {

    private GameEntityManager entityManager;
    private SpawnZoneManager spawnZoneManager;

    public GameEntityCommand(JCore main){
        this.entityManager = main.getEntityManager();
        this.spawnZoneManager = entityManager.getSpawnZoneManager();
        main.getCommand("gameentity").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("gameentity")){
            if(! (sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "You aren't a player!");
                return true;
            }
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "Invalid arguments.");
                return true;
            }
            GameEntity e = GameEntity.getEntity(args[0]);
            if(e != null){
                entityManager.spawnEntity(e, ((Player) sender).getLocation());
                sender.sendMessage(ChatColor.GREEN + "Entity Spawned");
                return true;
            }
        }
        return false;
    }

}
