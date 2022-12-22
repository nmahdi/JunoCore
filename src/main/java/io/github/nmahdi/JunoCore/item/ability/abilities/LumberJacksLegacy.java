package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.generation.BlockBreakHelper;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.item.BlockBreakItemAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.*;

public class LumberJacksLegacy extends BlockBreakItemAbility implements BlockBreakHelper {

    private final int range = 20;

    public LumberJacksLegacy(JCore main) {
        super(main, "Timber's Yell", GameItem.LumberjacksLegacy, 0, 0);
    }


    @Override
    public void onBlockBreak(GamePlayer player, NBTGameItem item, Block block) {

        ArrayList<Block> toScan = new ArrayList<>(getImmediateBlocks(block));
        ArrayList<Block> toRemove = new ArrayList<>();

        if(!block.hasMetadata("resource")) return;
        ResourceType resourceType;

        if(block.hasMetadata("resource")){
            resourceType = ResourceType.getType(block.getMetadata("resource").get(0).asString());
        }else{
            resourceType = ResourceType.getType(block);
        }

        if(resourceType == null) return;

        if(toScan.isEmpty()) return;

        int amountBroken = 0;

        while(!toScan.isEmpty()){
            if(amountBroken == range) break;

            for(int i = 0; i < toScan.size(); i++){
                if(amountBroken == range) break;

                Block b = toScan.get(i);

                ArrayList<Block> temp = getImmediateBlocks(b);

                for(Block tempBlock : temp){
                    if(!toScan.contains(tempBlock) && !tempBlock.getLocation().toString().equals(block.getLocation().toString())){
                        toScan.add(tempBlock);
                    }
                }

                breakBlock(main, player, resourceType, b, null);

                amountBroken++;

                toRemove.add(b);
            }

            toScan.removeAll(toRemove);

        }

    }

    private ArrayList<Block> getImmediateBlocks(Block center){
        ArrayList<Block> blocks = new ArrayList<>();
        for(Face face : Face.values()){
            Block current = center.getRelative(face.getBlockFace());

            if(isSameBlock(center, current)){
                blocks.add(current);
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
    public ArrayList<Component> addAbilityDescrption() {
        ArrayList<Component> description = new ArrayList<>();
        description.add(Component.text("Break a single log and destroy ").color(TextColors.GRAY_DESCRIPTION));
        description.add(Component.text(range + " logs that surround it.").color(TextColors.GRAY_DESCRIPTION));
        return description;
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
