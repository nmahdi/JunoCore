package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.item.AttackItemAbility;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class BloodThirstyDagger extends AttackItemAbility {

	private final int procChance = 30;

	public BloodThirstyDagger(JCore main) {
		super(main, "Vampire's Kiss", GameItem.BloodthirstyDagger, 0, 0);
	}

	@Override
	public void activate(GamePlayer gamePlayer, StatsTrait stats) {
		int chance = random.nextInt(100)+1;
		if(chance <= procChance){
			double healedAmount = (double)gamePlayer.getDamage(random)/10;
			gamePlayer.getStats().plusHealth((int)healedAmount);
		}

	}

	@Override
	public ArrayList<Component> addAbilityDescrption() {
		ArrayList<Component> descrption = new ArrayList<Component>();
		descrption.add(Component.text("Has a " + procChance + "% chance to heal for 10%").color(TextColors.GRAY_DESCRIPTION));
		descrption.add(Component.text("of your total damage when attacking").color(TextColors.GRAY_DESCRIPTION));
		return descrption;
	}
}
