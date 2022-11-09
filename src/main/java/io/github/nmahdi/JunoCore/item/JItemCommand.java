package io.github.nmahdi.JunoCore.item;

import de.tr7zw.nbtapi.NBTCompound;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JItemCommand implements CommandExecutor {

    public JItemCommand(){
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
                if(JItem.getItemByID(args[0]) != null){
                    ((Player)sender).getInventory().addItem(ItemBuilder.buildItem(JItem.getItemByID(args[0])));
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                return true;
            }

            Player player = (Player) sender;
            NBTJItem item = new NBTJItem(player.getInventory().getItemInMainHand());

            if(!item.hasCustomNbtData() && !item.hasJuno()){
                sender.sendMessage(ChatColor.RED + "This isn't a valid item!");
                return true;
            }

            if(args.length > 2){
                if(!args[0].equalsIgnoreCase("edit")){
                    sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                    return true;
                }


                if(!(item.getStats().hasKey(args[1])) || ItemStatID.getID(args[1]) == null){
                    sender.sendMessage(ChatColor.RED + "Invalid arguments!");
                    return true;
                }

                player.getInventory().setItemInMainHand(ItemBuilder.editItem(item.getItem(), ItemStatID.getID(args[1]), args[2]));
                player.sendMessage(ChatColor.GREEN + "Item's " + args[1] + " has been set to " + args[2]);
                return true;
            }
        }
        return false;
    }



}
