package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.ability.AbilityListener;
import io.github.nmahdi.JunoCore.item.ability.abilities.*;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;

public class ItemManager implements JunoManager {

	private boolean debugMode;
	private JCore main;

	public ItemManager(JCore main){
		this.debugMode = main.getConfig().getBoolean("debug-mode.item");
		this.main = main;
		//Item Abilities
		GameItem.MagicWand.setItemAbility(new MagicWand(main));
		GameItem.BloodthirstyDagger.setItemAbility(new BloodThirstyDagger(main));
		GameItem.LumberjacksLegacy.setItemAbility(new LumberJacksLegacy(main));
		GameItem.HealingWand.setItemAbility(new HealingWand(main));

		//Equipment sets
		new RookieSet();
		JLogger.log("Items have been initialized.");
	}

	public void postInit(){
		new AbilityListener(main);
	}

	@Override
	public void setDebugMode(boolean mode) {
		debugMode = mode;
	}

	@Override
	public boolean isDebugging() {
		return debugMode;
	}

	@Override
	public void onDisable() {

	}


}
