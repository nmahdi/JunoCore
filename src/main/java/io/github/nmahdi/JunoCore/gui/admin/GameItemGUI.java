package io.github.nmahdi.JunoCore.gui.admin;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameItemGUI extends GUI {

	private ItemStack NEXT = new ItemStackBuilder(Material.ARROW).setName("Next", TextColors.WHITE, false).build();

	public GameItemGUI(JCore main) {
		super(main, "&bGame Items", 54, null);
	}

	@Override
	public void onInvClick(InventoryClickEvent e) {
		e.setCancelled(true);
		if (e.getCurrentItem().isSimilar(NEXT)) {
			open((Player) e.getWhoClicked(), Integer.parseInt(e.getView().getTitle().replace(getName() + " - ", ""))+1);
			return;
		}
		if (e.getCurrentItem().isSimilar(BACK)) {
			open((Player) e.getWhoClicked(), Integer.parseInt(e.getView().getTitle().replace(getName() + " - ", ""))-1);
			return;
		}
		if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
			if (!e.getCurrentItem().isSimilar(EMPTY)) e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
		}
	}

	private void open(Player player, int page){
		Inventory inventory = Bukkit.createInventory(null, getSize(), getName() + " - " + page);

		setContents(inventory, page);

		inventory.setItem(getSize()-1, NEXT);
		if(page > 0) insertBack(inventory);
		insertFiller(inventory);

		player.openInventory(inventory);
	}

	@EventHandler
	public void invClick(InventoryClickEvent e) {
		if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;
		if(!e.getView().getTitle().contains(getName())) return;
		onInvClick(e);
	}

	@Override
	public void openInventory(Player player) {
		open(player, 0);
	}

	/**
	 * @param inventory
	 * @param page Page of items to open. Stats at 0
	 */
	private void setContents(Inventory inventory, int page){
		for(int i = (getSize()-9)*page; i < (getSize()-9)*(page+1); i++){
			if(i < GameItem.values().length) inventory.setItem(i-((getSize()-9)*page), ItemBuilder.buildGameItem(GameItem.values()[i]));
		}
	}

	@Override
	public void onInvClose(Inventory inventory, Player player) {

	}
}
