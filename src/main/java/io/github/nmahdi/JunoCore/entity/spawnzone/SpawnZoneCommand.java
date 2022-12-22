package io.github.nmahdi.JunoCore.entity.spawnzone;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnZoneCommand implements CommandExecutor {

	private JCore main;
	private GameEntityManager entityManager;
	private SpawnZoneManager spawnZoneManager;

	public SpawnZoneCommand(JCore main){
		this.main = main;
		this.entityManager = main.getEntityManager();
		this.spawnZoneManager = entityManager.getSpawnZoneManager();
		main.getCommand("spawnzone").setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnzone")) {

			if (args[0].equalsIgnoreCase("create")) {
				if(sender instanceof Player) {
					//jentity create ID EntityID SpawnNumber SpawnDelay
					spawnZoneManager.createNewSpawnZone((Player)sender, args[1], GameEntity.getEntity(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "You aren't a player!");
					return true;
				}
			}

		}
		return false;
	}
}
