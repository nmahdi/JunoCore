package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.admin.GameItemGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameItemCommand implements CommandExecutor {
    
    private GameItemGUI gameItemGUI;

    public GameItemCommand(JCore main){
        gameItemGUI = new GameItemGUI(main);
        main.getCommand("gameitem").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("gameitem")){

            if(!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "You aren't a player!");
                return true;
            }

            if(args.length < 1){
                sender.sendMessage(ChatColor.RED + "Not enough arguments!");
                return true;
            }

            if(args.length == 1){
                if(args[0].equalsIgnoreCase("open")){
                    gameItemGUI.openInventory((Player)sender);
                    return true;
                }
                if(GameItem.getItem(args[0]) != null){
                    ((Player)sender).getInventory().addItem(ItemBuilder.buildGameItem(GameItem.getItem(args[0])));
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                return true;
            }

            Player player = (Player) sender;
            NBTGameItem item = new NBTGameItem(player.getInventory().getItemInMainHand());

            if(!item.hasCustomNbtData() && !item.hasJuno()){
                sender.sendMessage(ChatColor.RED + "This isn't a valid item!");
                return true;
            }
        }
        return false;
    }



}
