package io.github.nmahdi.JunoCore.gui.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class UpgradeGUI extends GUI implements LinkedGUI{

	private BlacksmithGUI blacksmithGUI;

	public UpgradeGUI(JCore main, BlacksmithGUI blacksmithGUI) {
		super(main, Component.text("Blacksmith ~ Upgrade"), 34, true);
		this.blacksmithGUI = blacksmithGUI;
	}

	@Override
	public void insertItems(GamePlayer player, Inventory inventory) {

	}

	@Override
	public void onOpen(InventoryOpenEvent e, GamePlayer player) {

	}

	@Override
	public void onClick(InventoryClickEvent e, GamePlayer player) {
		e.setCancelled(true);
	}

	@Override
	public void onClose(InventoryCloseEvent e, GamePlayer player) {

	}

	@Override
	public GUI getPreviousGUI() {
		return blacksmithGUI;
	}
}
