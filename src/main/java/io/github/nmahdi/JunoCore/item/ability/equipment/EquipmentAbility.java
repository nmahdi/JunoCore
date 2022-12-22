package io.github.nmahdi.JunoCore.item.ability.equipment;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.DescriptionBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.utils.JLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public abstract class EquipmentAbility {

	protected String displayName;
	protected boolean requiresFullSet;
	protected ArrayList<GameItem> set = new ArrayList<>();
	protected ArrayList<Component> abilityDescription = new ArrayList<>();

	public EquipmentAbility(String displayName, boolean requiresFullSet, GameItem... gameItems){
		this.displayName = displayName;
		this.requiresFullSet = requiresFullSet;
		for(GameItem item : gameItems){
			item.setEquipmentAbility(this);
			set.add(item);
		}
		abilityDescription.add(Component.text("Full Set Ability: " + displayName).color(NamedTextColor.GOLD));
	}

	public abstract void run(GamePlayer player);

	public abstract EquipmentAbility onEquip(GamePlayer gamePlayer);
	public abstract EquipmentAbility onUnEquip(GamePlayer gamePlayer);

	public String getDisplayName() {
		return displayName;
	}

	public boolean requiresFullSet() {
		return requiresFullSet;
	}

	public ArrayList<Component> getAbilityDescription() {
		return abilityDescription;
	}

	public ArrayList<GameItem> getSet() {
		return set;
	}
}
