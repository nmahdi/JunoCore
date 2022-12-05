package io.github.nmahdi.JunoCore.item.ability;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BlockBreakItemAbility extends ItemAbility{

	protected ResourceManager resourceManager;

	public BlockBreakItemAbility(JCore main, GameItem item, int manaCost, int cooldownTicks) {
		super(main, item, manaCost, cooldownTicks);
		this.resourceManager = main.getResourceManager();
	}

	public void activate(Player player, NBTGameItem gameItem, Block block){
		if(gameItem == null) return;
		if(!gameItem.hasID() || !gameItem.hasID()) return;
		if(!gameItem.getID().equals(item.getId())) return;
		onBlockBreak(player, gameItem, block);
	}

	public abstract void onBlockBreak(Player player, NBTGameItem item, Block block);

}
