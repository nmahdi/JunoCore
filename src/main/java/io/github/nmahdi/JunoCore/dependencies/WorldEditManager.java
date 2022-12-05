package io.github.nmahdi.JunoCore.dependencies;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import io.github.nmahdi.JunoCore.JCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WorldEditManager {

    public static void pasteSchematic(JCore main, String schmatic, World world, Location location){
        File file = new File(main.getResourceManager().getFolder(), schmatic+".schem");
        Clipboard clipboard;

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))){
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))){
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Location> getLocationsFromSelection(Player player, Material blockType){
        ArrayList<Location> locations = new ArrayList<>();
        Region region = getSelectedRegion(player);

        if(region == null) return locations;

        for (BlockVector3 point : region) {
            if (player.getWorld().getBlockAt(point.getBlockX(), point.getBlockY(), point.getBlockZ()).getType() == blockType) {
                locations.add(new Location(player.getWorld(), point.getBlockX(), point.getBlockY(), point.getBlockZ()));
            }
        }
        return locations;
    }

    public static Region getSelectedRegion(Player player){
        Region region = null;
        com.sk89q.worldedit.entity.Player actor = BukkitAdapter.adapt(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(actor);
        com.sk89q.worldedit.world.World selectionWorld = session.getSelectionWorld();
        try {
            if (selectionWorld == null) throw new IncompleteRegionException();
            region = session.getSelection(selectionWorld);
        } catch (IncompleteRegionException ex) {
            actor.printError(TextComponent.of("Please make a region selection first."));
        }
        return region;
    }

}
