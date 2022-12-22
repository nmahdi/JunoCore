package io.github.nmahdi.JunoCore.item.ability.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;

public abstract class BowItemAbility extends ItemAbility{

	private boolean cancelArrow;

	public BowItemAbility(JCore main, String displayName, GameItem item, int manaCost, int coolDownTicks, boolean cancelArrow) {
		super(main, displayName, item, manaCost, coolDownTicks);
		this.cancelArrow = cancelArrow;
	}

	public abstract void activateAbility(GamePlayer player, GameItem item);

	public boolean willCancelArrow() {
		return cancelArrow;
	}
}
