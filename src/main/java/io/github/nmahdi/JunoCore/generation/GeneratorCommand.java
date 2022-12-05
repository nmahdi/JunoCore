package io.github.nmahdi.JunoCore.generation;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.WorldEditManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GeneratorCommand implements CommandExecutor {

    private JCore main;
    private ResourceManager resourceManager;

    public GeneratorCommand(JCore main){
        this.main = main;
        this.resourceManager = main.getResourceManager();
        main.getCommand("generator").setExecutor(this);
    }

    // /generator <name> <type> <resource>
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("generator")){
            if(sender instanceof Player){
                GeneratorType generatorType = GeneratorType.getType(args[1]);
                if(generatorType == null){
                    sender.sendMessage(ChatColor.RED + "Invalid Generator Type");
                    return true;
                }
                ResourceType resourceType = ResourceType.getType(args[2]);
                if(resourceType == null){
                    sender.sendMessage(ChatColor.RED + "Invalid Resource Type");
                    return true;
                }

                if(generatorType == GeneratorType.Tree){
                    Region region = WorldEditManager.getSelectedRegion((Player) sender);
                    main.getRegionManager().addRegion(new ProtectedCuboidRegion(args[0], region.getMinimumPoint(), region.getMaximumPoint()));
                }

                resourceManager.createGenerator(((Player) sender), args[0], generatorType, resourceType);
                sender.sendMessage(ChatColor.GREEN + "Successfully created generator " + args[0] + ". Type: " + generatorType.getId() +" Resource: " + resourceType.getId());

                return true;
            }
            sender.sendMessage(ChatColor.RED + "You need to be a player to execute this command.");
            return true;
        }
        return false;
    }

}
