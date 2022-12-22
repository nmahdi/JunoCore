package io.github.nmahdi.JunoCore.item.ability;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.item.BowItemAbility;
import io.github.nmahdi.JunoCore.item.ability.item.ItemAbility;
import io.github.nmahdi.JunoCore.item.ability.item.RightClickItemAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {

	private JCore main;
	private PlayerManager playerManager;

	public AbilityListener(JCore main){
		this.main = main;
		this.playerManager = main.getPlayerManager();
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	//Clicking Abilities
	@EventHandler
	public void clickEvent(PlayerInteractEvent e){
		if(InventoryHelper.isAirOrNull(e.getItem())) return;

		NBTGameItem gameItem = new NBTGameItem(e.getItem());
		if(!gameItem.hasJuno() || !gameItem.hasID()) return;

		GameItem item = GameItem.getItem(gameItem.getID());
		if(item == null) return;

		if(!item.hasItemAbility()) return;

		GamePlayer player = playerManager.getPlayer(e.getPlayer());

		ItemAbility ability = item.getItemAbility();
		if(e.getAction().isRightClick()) {

			if(!(ability instanceof RightClickItemAbility)) return;

			if (!ability.isOnCooldown(e.getPlayer())) {

				if (player.getStats().getMana() < ability.getManaCost()) {
					e.getPlayer().sendMessage(ChatColor.RED + "No mana.");
					return;
				}
				player.getStats().minusMana(ability.getManaCost());
				((RightClickItemAbility)ability).activateAbility(player, gameItem);
				ability.setOnCooldown(e.getPlayer());
				return;
			}
			if (ability.getCoolDownTicks() > 20) e.getPlayer().sendMessage(ChatColor.RED + "Item is on cooldown.");

		}
	}

	//Shooting Abilities
	@EventHandler
	public void onShoot(EntityShootBowEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getProjectile() instanceof Arrow)) return;

		GamePlayer player = playerManager.getPlayer((Player)e.getEntity());

		NBTGameItem gameItem = new NBTGameItem(e.getBow());
		if(!gameItem.hasID()) return;

		GameItem item = GameItem.getItem(gameItem.getID());
		if(item == null) return;

		if(!item.hasItemAbility()) return;

		if(item.getItemAbility() instanceof BowItemAbility) {
			BowItemAbility ability = (BowItemAbility) item.getItemAbility();

			e.setCancelled(ability.willCancelArrow());

			ability.activateAbility(player, player.getHeldItem());
		}

	}

}
