package io.github.nmahdi.JunoCore.item.ability.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class RightClickItemAbility extends ItemAbility{

	public RightClickItemAbility(JCore main, String displayName, GameItem item, int manaCost, int cooldownTicks) {
		super(main, displayName, item, manaCost, cooldownTicks);
	}

	public abstract void activateAbility(GamePlayer player, NBTGameItem gameItem);

}
