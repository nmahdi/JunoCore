package io.github.nmahdi.JunoCore.player.stats;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoinCommand implements CommandExecutor {

    private PlayerManager playerManager;

    public CoinCommand(JCore main){
        this.playerManager = main.getPlayerManager();
        main.getCommand("coins").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("coins")){
            if(args.length > 0) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (Bukkit.getPlayer(args[1]) == null) {
                        sender.sendMessage(ChatColor.RED + "Invalid Player!");
                        return true;
                    }

                    GamePlayer player = playerManager.getPlayer((Player)sender);
                    player.setCoins(Long.parseLong(args[2]));
                    sender.sendMessage(ChatColor.GREEN + player.getPlayerObject().getName() + "'s coins have been set to " + args[2]);
                    return true;
                }

                sender.sendMessage(ChatColor.RED + "Something went wrong.");
                return true;
            }
        }
        return false;
    }
}
