package io.github.nmahdi.JunoCore.item;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.ArrayList;

public interface Ability {

	String getAbilityName();

	int getManaCost();

	/**
	 * @return Cooldown in ticks
	 */
	int getCoolDown();

	ArrayList<Player> getCooldowns();

	default boolean isOnCooldown(Player player){
		return getCooldowns().contains(player);
	}

	ArrayList<Component> getAbilityDescription();

}
