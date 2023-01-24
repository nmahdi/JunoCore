package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.item.RightClickItemAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerStats;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class HealingWand extends RightClickItemAbility {

	private final int baseHeal = 20;
	private final int percentageHeal = 1;

	public HealingWand(JCore main) {
		super(main, "Life Essence", GameItem.HealingWand, 50, 60);
	}

	@Override
	public ArrayList<Component> addAbilityDescrption() {
		ArrayList<Component> description = new ArrayList<>();
		description.add(Component.text("Heals for " + baseHeal).color(TextColors.GRAY_DESCRIPTION)
				.append(Component.text(PlayerStat.Health.getSymbol()).color(PlayerStat.MaxHealth.getColor()))
				.append(Component.text(" +").color(TextColors.GRAY_DESCRIPTION)));
		description.add(Component.text(percentageHeal + "% of your total health").color(TextColors.GRAY_DESCRIPTION));
		return description;
	}

	@Override
	public void activateAbility(GamePlayer player, NBTGameItem gameItem) {
		PlayerStats stats = player.getStats();
		double amount = baseHeal + (stats.getMaxHealth()/100);
		stats.plusHealth(amount);
		player.getActionBar().heal(amount);
	}
	
}
