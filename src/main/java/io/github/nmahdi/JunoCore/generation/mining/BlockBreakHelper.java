package io.github.nmahdi.JunoCore.generation.mining;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.BlockBreakItemAbility;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public interface BlockBreakHelper {

	default void breakBlock(JCore main, GamePlayer player, ResourceType resourceType, Block block, BlockBreakItemAbility ability){
		if(ability != null){
			ability.activate(player.getPlayerObject(), player.getNBTHeldItem(), block);
		}
		block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 0.5f, 0);
		block.setType(resourceType.getReplacement());
		if(resourceType == ResourceType.Cobblestone || resourceType == ResourceType.Stone){
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					if(block.getType().equals(resourceType.getReplacement())) {
						block.setType(Material.STONE);
					}
				}

			}, 60);
		}

		GameItem drop = resourceType.getDrop();

		if (player.getNBTHeldItem() != null) {
			if (player.getHeldItem().canApplyRunes() && player.getNBTHeldItem().hasRunes()) {
				if (player.getNBTHeldItem().getRunes().containsKey(Rune.Smelting)) {
					if (resourceType.getSmelted() != null) drop = resourceType.getSmelted();
				}

				if(player.getNBTHeldItem().getRunes().containsKey(Rune.Silktouch)){
					if(resourceType.getSilkTouch() != null) drop = resourceType.getSilkTouch();
				}

			}
		}

		HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(ItemBuilder.buildGameItem(drop));
		if (!leftOver.isEmpty()) {
			for (ItemStack itemStack : leftOver.values()) {
				player.getWorld().dropItemNaturally(block.getLocation(), itemStack);
			}
		}

	}

}
