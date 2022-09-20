package com.junocore.item;

import com.junocore.item.builder.NBTItemStackBuilder;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JItemCommand implements CommandExecutor {

    private JItemManager itemManager;

    public JItemCommand(JItemManager itemManager){
        this.itemManager = itemManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("jitem")){

            if(!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "You aren't a player!");
                return true;
            }

            if(args.length < 1){
                sender.sendMessage(ChatColor.RED + "Not enough arguments!");
                return true;
            }

            if(args.length == 1){
                if(itemManager.getItemByID(args[0]) == null){
                    sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                    return true;
                }
                ((Player)sender).getInventory().addItem(new NBTItemStackBuilder(itemManager.getItemByID(args[0])).getStack());
                return true;
            }

            Player player = (Player) sender;
            NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());

            if(!item.hasCustomNbtData() && !item.hasKey("juno")){
                sender.sendMessage(ChatColor.RED + "This isn't a valid item!");
                return true;
            }

            if(args.length > 2){
                if(!args[0].equalsIgnoreCase("edit")){
                    sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                    return true;
                }

                NBTCompound juno = item.getCompound("juno");
                if(!(juno.getCompound("stats").hasKey(args[1])) && itemManager.getIdByName(args[1]) == null){
                    sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                    return true;
                }

                player.getInventory().setItemInMainHand(
                        new NBTItemStackBuilder(item, itemManager.getItemByID(juno.getString("id"))).setStat(itemManager.getIdByName(args[1]), args[2]).buildStack());
                player.sendMessage(ChatColor.GREEN + "Item's " + args[1] + " has been set to " + args[2]);
                return true;
            }
        }
        return false;
    }



}
