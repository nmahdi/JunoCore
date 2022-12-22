package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.equipment.AppliesWeaponBuff;
import io.github.nmahdi.JunoCore.item.ability.equipment.EquipmentAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class RookieSet extends EquipmentAbility implements AppliesWeaponBuff {

	private int defense = 100;

	public RookieSet(){
		super("Rookie God", true, GameItem.RookieHelmet, GameItem.RookieChestplate, GameItem.RookieLeggings, GameItem.RookieBoots,
				GameItem.RookieCape, GameItem.RookieBracelet, GameItem.RookieRing, GameItem.RookieNecklace, GameItem.RookieHeadband);
		abilityDescription.add(Component.text("Gives you 100 extra defense.").color(TextColors.GRAY_DESCRIPTION));
		abilityDescription.add(Component.text("Increases the damage of your").color(TextColors.GRAY_DESCRIPTION));
		abilityDescription.add(Component.text(GameItem.RookieSword.getDisplayName()).color(GameItem.RookieSword.getRarity().getColor()).append(Component.text(" by 10%").color(TextColors.GRAY_DESCRIPTION)));
	}

	@Override
	public void run(GamePlayer player) {

	}

	@Override
	public RookieSet onEquip(GamePlayer gamePlayer) {
		gamePlayer.getStats().setDefense(gamePlayer.getStats().getDefense()+defense);
		return this;
	}

	@Override
	public RookieSet onUnEquip(GamePlayer gamePlayer) {
		gamePlayer.getStats().setDefense(gamePlayer.getStats().getDefense()-defense);
		return this;
	}


	@Override
	public HashMap<PlayerStat, Integer> getBuff(GameItem item, NBTGameItem gameItem) {
		HashMap<PlayerStat, Integer> stats = new HashMap<>();
		int damage = item.getDamage();

		if(item.canApplyRunes() && gameItem.hasRunes()){
			for(Map.Entry<Rune, Integer> runes : gameItem.getRunes().entrySet()){
				damage += runes.getKey().getAmount()*runes.getValue();
			}
		}

		stats.put(PlayerStat.Damage, damage/10);

		return stats;
	}

	@Override
	public GameItem getWeapon() {
		return GameItem.RookieSword;
	}

}
