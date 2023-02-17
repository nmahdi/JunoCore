package io.github.nmahdi.JunoCore.item.items.set;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.player.GamePlayer;

public class EterniteSet extends SetEffect {

	public EterniteSet(ItemManager itemManager, GameItem... items) {
		super(itemManager, "Miner's Dream", items);
	}

	@Override
	public SetEffect onEquip(GamePlayer player) {
		player.sendMessage("Equipped!!!");
		return this;
	}

	@Override
	public SetEffect tick(GamePlayer player) {
		return this;
	}

	@Override
	public SetEffect onUnequip(GamePlayer player) {
		player.sendMessage("Unequipped!");
		return this;
	}

}
