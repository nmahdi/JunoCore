package io.github.nmahdi.JunoCore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ColorTesterCommand implements CommandExecutor {

	public ColorTesterCommand(JCore main){
		main.getCommand("color").setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
		if(cmd.getName().equalsIgnoreCase("color")){
			String msg = "";
			for(int i = 1; i < args.length; i++){
				msg+=args[i] + " ";
			}
			Bukkit.broadcast(Component.text(msg).color(TextColor.color(Integer.decode(args[0]))).decorate(TextDecoration.BOLD));
		}
		return false;
	}
}
