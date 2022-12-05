package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.ability.abilities.MagicWandAbility;
import io.github.nmahdi.JunoCore.item.ability.abilities.TreeFellerListener;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemManager implements JunoManager {

	private JCore main;

	public ItemManager(JCore main){
		new MagicWandAbility(main, GameItem.MagicWand);
		new TreeFellerListener(main, GameItem.TreeFeller);
		this.main = main;
		JLogger.log("Items have been loaded.");
	}

	@Override
	public boolean isDebugging() {
		return false;
	}

	@Override
	public void onDisable() {

	}


}
