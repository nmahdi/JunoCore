package io.github.nmahdi.JunoCore.item.ability.equipment;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;

import java.util.HashMap;

public interface AppliesWeaponBuff {

	HashMap<PlayerStat, Integer> getBuff(GameItem item, NBTGameItem gameItem);

	GameItem getWeapon();

}
