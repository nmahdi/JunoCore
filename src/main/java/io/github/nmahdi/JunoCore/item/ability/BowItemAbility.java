package io.github.nmahdi.JunoCore.item.ability;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;

public abstract class BowItemAbility extends ItemAbility{

	private boolean cancelArrow;

	public BowItemAbility(JCore main, GameItem item, int manaCost, int coolDownTicks, boolean cancelArrow) {
		super(main, item, manaCost, coolDownTicks);
		this.cancelArrow = cancelArrow;
	}

	@EventHandler
	public void onShoot(EntityShootBowEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getProjectile() instanceof Arrow)) return;
		GamePlayer player = playerManager.getPlayer((Player)e.getEntity());
		if(player.getHeldItem() == null) return;
		if(!player.getHeldItem().getId().equalsIgnoreCase(item.getId())) return;
		e.setCancelled(cancelArrow);
		activateAbility(player, player.getHeldItem());
	}

	public abstract void activateAbility(GamePlayer player, GameItem item);

}
