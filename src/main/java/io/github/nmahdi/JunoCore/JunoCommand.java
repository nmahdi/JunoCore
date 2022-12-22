package io.github.nmahdi.JunoCore;

import io.github.nmahdi.JunoCore.utils.JunoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class JunoCommand implements CommandExecutor {

	private JCore main;

	public JunoCommand(JCore main){
		this.main = main;
		main.getCommand("juno").setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
		if(cmd.getName().equalsIgnoreCase("juno")) {

			if (args.length == 2) {

				if (args[0].equalsIgnoreCase("color")) {
					String msg = "";
					for (int i = 1; i < args.length; i++) {
						msg += args[i] + " ";
					}
					sender.sendMessage(Component.text(msg).color(TextColor.color(Integer.decode(args[1]))).decorate(TextDecoration.BOLD));
					return true;
				}

				return true;
			}
			if (args.length == 3){

				if(args[0].equalsIgnoreCase("debug")){

					if(setDebugMode(args[1], Boolean.getBoolean(args[2]))){
						sender.sendMessage(ChatColor.GREEN + "Debug mode for " + args[1] + " manager has been set to " + args[2]);
					}else{
						sender.sendMessage(ChatColor.RED + "Failed to set manager " + args[1] + " manager to " + args[2]);
					}

				}

			}
		}
		return false;
	}

	private boolean setDebugMode(String manager, boolean debug){
		JunoManager junoManager = switch (manager.toUpperCase()) {
			case "SQL" -> main.getSQLManager();
			case "PACKET" -> main.getPacketManager();
			case "HOLOGRAM" -> main.getHologramsManager();
			case "PLAYER" -> main.getPlayerManager();
			case "SCOREBOARD" -> main.getPlayerManager().getScoreboardManager();
			case "ENTITY" -> main.getEntityManager();
			case "SPAWN-ZONE" -> main.getEntityManager().getSpawnZoneManager();
			case "RESOURCE" -> main.getResourceManager();
			case "ITEM" -> main.getItemManager();
			default -> null;
		};
		if(junoManager != null) {
			main.getConfig().set("debug-mode."+manager, debug);
			main.saveConfig();
			junoManager.setDebugMode(debug);
			return true;
		}
		return false;
	}

}
