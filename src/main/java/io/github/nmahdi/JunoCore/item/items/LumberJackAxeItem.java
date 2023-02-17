package io.github.nmahdi.JunoCore.item.items;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.BlockBreakHelper;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.BlockBreakAbility;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

public class LumberJackAxeItem extends StatItem implements Runeable, BlockBreakAbility, BlockBreakHelper {

	private final int range = 20;

	public LumberJackAxeItem(ItemManager itemManager) {
		super(itemManager, "lumber_jack_axe", "Lumber-Jack Axe", Material.GOLDEN_AXE, Rarity.Unique, ItemType.Axe);
		setDamage(10).setHarvestingSpeed(100).setFortune(25);
		blockBreakDescription.add(Component.text("Break a single log and destroy").color(TextColors.GRAY_DESCRIPTION));
		blockBreakDescription.add(Component.text(range).color(TextColors.NEGATIVE).append(Component.text(" logs that surround it.").color(TextColors.GRAY_DESCRIPTION)));
	}

	@Override
	public void onBlockBreak(JCore main, Block block, GamePlayer player, NBTGameItem item) {
		ArrayList<Block> toScan = new ArrayList<>(getImmediateBlocks(block));
		ArrayList<Block> toRemove = new ArrayList<>();

		ResourceType resourceType;

		if(block.hasMetadata("resource")){
			resourceType = main.getResourceManager().getResourceType(block.getMetadata("resource").get(0).asString());
		}else{
			resourceType = main.getResourceManager().getResourceType(block);
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
	public String getBlockBreakAbilityName() {
		return "Timber's Yell";
	}

	@Override
	public int getMaxRunes() {
		return 5;
	}

	public enum Face{
		NORTH(BlockFace.NORTH),
		EAST(BlockFace.EAST),
		SOUTH(BlockFace.SOUTH),
		WEST(BlockFace.WEST),
		UP(BlockFace.UP),
		Down(BlockFace.DOWN);

		private final BlockFace blockFace;

		Face(BlockFace blockFace){
			this.blockFace = blockFace;
		}

		public BlockFace getBlockFace() {
			return blockFace;
		}
	}
}
