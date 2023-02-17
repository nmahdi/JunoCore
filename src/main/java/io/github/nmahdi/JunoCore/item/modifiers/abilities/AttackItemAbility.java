package io.github.nmahdi.JunoCore.item.modifiers.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;

import java.util.ArrayList;

public interface AttackItemAbility {

	ArrayList<Component> attackDescription = new ArrayList<>();

	default void addAttackAbilityDescription(Component component) {
		attackDescription.add(component);
	}

	default ArrayList<Component> getAttackDescription(){
		return attackDescription;
	}

	void onAttack(JCore main, StatsTrait entityStats, GamePlayer player, NBTGameItem item);

	String getAttackAbilityName();

}
