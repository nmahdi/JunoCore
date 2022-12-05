package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.generation.mining.BlockBreakHelper;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.item.ability.BlockBreakItemAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.*;

public class TreeFellerListener extends BlockBreakItemAbility implements BlockBreakHelper {

    private final int range = 10;

    public TreeFellerListener(JCore main, GameItem item) {
        super(main, item, 0, 0);
    }

    //TODO: For loop is running more than it's supposed to.
    @Override
    public void onBlockBreak(Player player, NBTGameItem item, Block block) {
        ArrayList<Location> toScan = new ArrayList<>(getImmediateBlocks(block));

        if(toScan.isEmpty()) return;

        int amount = 0;
        for(Location location : toScan){
            ResourceType resourceType = ResourceType.getType(location.getBlock().getType());
            if(resourceType != null) {
                breakBlock(main, playerManager.getPlayer(player), resourceType, location.getBlock(), null);
                amount++;
            }
        }

        while(amount < range){

            if(toScan.isEmpty()) break;

            for (int i = 0; i < toScan.size(); i++){

                ArrayList<Location> n = getImmediateBlocks(toScan.get(i).getBlock());

                for(int j = 0; j < n.size(); j++){
                    if(n.get(j).equals(block.getLocation())) {
                        n.remove(n.get(j));
                    }else{
                        ResourceType resourceType = ResourceType.getType(n.get(j).getBlock().getType());
                        if(resourceType != null) {
                            breakBlock(main, playerManager.getPlayer(player), resourceType, n.get(j).getBlock(), null);
                            amount++;
                        }
                        if(amount == range){
                            toScan.clear();
                            return;
                        }
                    }
                }

                toScan.addAll(n);

                toScan.remove(toScan.get(i));


            }

        }
    }

    private ArrayList<Location> getImmediateBlocks(Block center){
        ArrayList<Location> blocks = new ArrayList<>();
        for(Face face : Face.values()){
            Block current = center.getRelative(face.getBlockFace());

            if(isSameBlock(center, current)){
                blocks.add(current.getLocation());
            }
        }
        return blocks;
    }

    /**
     * Used to check if the next block is the same type as the old block
     *
     * @param original - Original block
     * @param toBreak  - Next block to break
     * @return
     */
    public boolean isSameBlock(Block original, Block toBreak) {
        return original.getType().equals(toBreak.getType());
    }

    @Override
    public Component addAbilityDescrption() {
        return Component.text("Break a single log and destroy " + range + " logs that surround it.").color(TextColors.GRAY);
    }

    public enum Face{
        NORTH(BlockFace.NORTH),
        EAST(BlockFace.EAST),
        SOUTH(BlockFace.SOUTH),
        WEST(BlockFace.WEST),
        UP(BlockFace.UP),
        Down(BlockFace.DOWN);

        BlockFace blockFace;

        Face(BlockFace blockFace){
            this.blockFace = blockFace;
        }

        public BlockFace getBlockFace() {
            return blockFace;
        }
    }

}
