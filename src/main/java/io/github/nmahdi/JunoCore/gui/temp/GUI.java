package io.github.nmahdi.JunoCore.gui.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class GUI implements Listener {

	public final ItemStack EMPTY = new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).blankName().build();
	public final ItemStack BACK = new ItemStackBuilder(Material.ARROW).setName("Back", TextColors.WHITE, false).build();

	protected JCore main;
	protected PlayerManager playerManager;
	protected ItemManager itemManager;
	protected Random random = ThreadLocalRandom.current();
	protected Component displayName;
	protected int size;
	protected boolean filler;

	public GUI(JCore main, Component displayName, int sizeInRows, boolean filler){
		this.main = main;
		this.playerManager = main.getPlayerManager();
		this.itemManager = main.getItemManager();
		this.displayName = displayName;
		this.size = sizeInRows*9;
		this.filler = filler;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	public void openInventory(GamePlayer player){
		Inventory inventory = Bukkit.createInventory(null, size, displayName);

		if(filler){
			insertFiller(inventory);
		}

		insertItems(player, inventory);

		if(this instanceof LinkedGUI){
			inventory.setItem(size-9, BACK);
		}

		player.getPlayerObject().openInventory(inventory);
	}

	public abstract void insertItems(GamePlayer player, Inventory inventory);

	@EventHandler
	private void openEvent(InventoryOpenEvent e){
		if(!e.getView().title().equals(displayName)) return;
		onOpen(e, playerManager.getPlayer((Player) e.getPlayer()));
	}

	public abstract void onOpen(InventoryOpenEvent e, GamePlayer player);

	@EventHandler
	private void clickEvent(InventoryClickEvent e){
		if(!e.getView().title().equals(displayName)) return;
		if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;

		GamePlayer player = playerManager.getPlayer((Player)e.getWhoClicked());

		if(this instanceof LinkedGUI){
			if(e.getCurrentItem().isSimilar(BACK)) {
				((LinkedGUI) this).getPreviousGUI().openInventory(player);
				e.setCancelled(true);
			}
		}

		onClick(e, player);
	}

	public abstract void onClick(InventoryClickEvent e, GamePlayer player);

	@EventHandler
	private void closeEvent(InventoryCloseEvent e){
		if(!e.getView().title().equals(displayName)) return;
		onClose(e, playerManager.getPlayer((Player) e.getPlayer()));
	}

	public abstract void onClose(InventoryCloseEvent e, GamePlayer player);

	public void insertFiller(Inventory inventory){
		for (int i = 0; i < getSize(); i++) {
			if (inventory.getItem(i) == null) inventory.setItem(i, EMPTY);
		}
	}

	public JCore getMain() {
		return main;
	}

	public Component getDisplayName() {
		return displayName;
	}

	public int getSizeInRows(){
		return size*9;
	}

	public int getSize() {
		return size;
	}

	public boolean hasFiller() {
		return filler;
	}
}
