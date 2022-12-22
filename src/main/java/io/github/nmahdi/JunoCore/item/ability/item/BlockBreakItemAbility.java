package io.github.nmahdi.JunoCore.item.ability.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BlockBreakItemAbility extends ItemAbility{

	protected ResourceManager resourceManager;

	public BlockBreakItemAbility(JCore main, String displayName, GameItem item, int manaCost, int cooldownTicks) {
		super(main, displayName, item, manaCost, cooldownTicks);
		this.resourceManager = main.getResourceManager();
	}

	public void activate(GamePlayer player, NBTGameItem gameItem, Block block){
		if(gameItem == null) return;
		if(!gameItem.hasID() || !gameItem.hasID()) return;
		if(!gameItem.getID().equals(item.getId())) return;
		onBlockBreak(player, gameItem, block);
	}

	public abstract void onBlockBreak(GamePlayer player, NBTGameItem item, Block block);

}
