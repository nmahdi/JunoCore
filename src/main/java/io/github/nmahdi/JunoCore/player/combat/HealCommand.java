package io.github.nmahdi.JunoCore.player.combat;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

	private JCore main;
	private PlayerManager playerManager;

	public HealCommand(JCore main){
		this.main = main;
		this.playerManager = main.getPlayerManager();
		main.getCommand("heal").setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
		if(cmd.getName().equalsIgnoreCase("heal")){
			if(sender instanceof Player){
				playerManager.getPlayer((Player)sender).heal();
			}
		}
		return false;
	}
}
