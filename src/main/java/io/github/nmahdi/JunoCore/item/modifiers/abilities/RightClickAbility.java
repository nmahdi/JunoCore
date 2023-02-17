package io.github.nmahdi.JunoCore.item.modifiers.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public interface RightClickAbility{

	ArrayList<Component> rightClickDescription = new ArrayList<>();

	default void addRightClickAbilityDescription(Component component) {
		rightClickDescription.add(component);
	}

	default ArrayList<Component> getRightClickDescription(){
		return rightClickDescription;
	}

	void onRightClick(JCore main, GamePlayer player, NBTGameItem item);

	String getRightClickAbilityName();

	int getRightClickManaCost();

}
