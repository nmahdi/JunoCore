package io.github.nmahdi.JunoCore.item.ability;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class RightClickItemAbility extends ItemAbility{

	public RightClickItemAbility(JCore main, GameItem item, int manaCost, int cooldownTicks) {
		super(main, item, manaCost, cooldownTicks);
	}

	@EventHandler
	public void clickEvent(PlayerInteractEvent e){
		if(InventoryHelper.isAirOrNull(e.getItem())) return;
		NBTGameItem gameItem = new NBTGameItem(e.getItem());
		if(!gameItem.hasJuno() || !gameItem.hasID()) return;
		if(!gameItem.getID().equalsIgnoreCase(item.getId())) return;
		if(!e.getAction().isRightClick()) return;
		if(!isOnCooldown(e.getPlayer())){
			GamePlayer player = playerManager.getPlayer(e.getPlayer());
			if(player.getStats().getMana() < getManaCost()){
				e.getPlayer().sendMessage(ChatColor.RED + "No mana.");
				return;
			}
			player.getStats().minusMana(manaCost);
			activateAbility(player, gameItem);
			setOnCooldown(e.getPlayer());
			return;
		}
		if(coolDownTicks > 20) e.getPlayer().sendMessage(ChatColor.RED + "Item is on cooldown.");
	}

	public abstract void activateAbility(GamePlayer player, NBTGameItem gameItem);

}
