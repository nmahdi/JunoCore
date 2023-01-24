package io.github.nmahdi.JunoCore.gui.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class CollectionGUI extends GUI {

	public CollectionGUI(JCore main, String name, int size, GUI previousMenu) {
		super(main, name, size, previousMenu);
	}

	@Override
	public void onInvClick(InventoryClickEvent e) {

	}

	@Override
	public void setItems(Inventory inventory, Player player) {

	}

	@Override
	public void onInvClose(Inventory inventory, Player player) {

	}
}
