package io.github.nmahdi.JunoCore.item.ability.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;

public abstract class AttackItemAbility extends ItemAbility{

	public AttackItemAbility(JCore main, String displayName, GameItem item, int manaCost, int coolDownTicks) {
		super(main, displayName, item, manaCost, coolDownTicks);
	}

	public abstract void activate(GamePlayer gamePlayer, StatsTrait stats);

}
