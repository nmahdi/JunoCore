package io.github.nmahdi.JunoCore.item.modifiers.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.listeners.resource.BreakingBlock;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;

import java.util.ArrayList;

public interface BlockBreakAbility {

	ArrayList<Component> blockBreakDescription = new ArrayList<>();

	default void addBlockBreakAbilityDescription(Component component) {
		blockBreakDescription.add(component);
	}

	default ArrayList<Component> getBlockBreakDescription(){
		return blockBreakDescription;
	}

	void onBlockBreak(JCore main, Block block, GamePlayer player, NBTGameItem item);

	String getBlockBreakAbilityName();

}
