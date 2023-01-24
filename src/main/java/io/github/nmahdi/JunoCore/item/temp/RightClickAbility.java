package io.github.nmahdi.JunoCore.item.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.item.builder.DescriptionBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;

public interface RightClickAbility{

	ArrayList<Component> description = new ArrayList<>();

	default void addRightClickAbilityDescription(Component component) {
		description.add(component);
	}

	default ArrayList<Component> getDescription(){
		return description;
	}

	void onRightClick(JCore main, GamePlayer player, NBTGameItem item);

	String getRightClickAbilityName();

}
