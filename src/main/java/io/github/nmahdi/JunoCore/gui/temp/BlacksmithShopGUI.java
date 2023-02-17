package io.github.nmahdi.JunoCore.gui.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class BlacksmithShopGUI extends ShopGUI implements LinkedGUI{

	private BlacksmithGUI blacksmithGUI;

	public BlacksmithShopGUI(JCore main, BlacksmithGUI blacksmithGUI) {
		super(main, Component.text("Blacksmith ~ Shop"), 54, true);
		this.blacksmithGUI = blacksmithGUI;
	}

	@Override
	public void onOpen(InventoryOpenEvent e, GamePlayer player) {

	}

	@Override
	public void onClose(InventoryCloseEvent e, GamePlayer player) {

	}

	@Override
	public GUI getPreviousGUI() {
		return blacksmithGUI;
	}
}
