package io.github.nmahdi.JunoCore;

import io.github.nmahdi.JunoCore.item.builder.SkullItemBuilder;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomSkullCommand implements CommandExecutor {

    public CustomSkullCommand(JCore main){
        main.getCommand("customskull").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("customskull")){
            if (sender instanceof Player) {
                if (args.length == 3) {
                    ((Player) sender).getInventory().addItem(new SkullItemBuilder(args[0]).build());
                    return true;
                }
            }
        }
        return false;

    }
}
