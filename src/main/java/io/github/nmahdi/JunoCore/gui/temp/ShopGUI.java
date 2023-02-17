package io.github.nmahdi.JunoCore.gui.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTShopItem;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class ShopGUI extends GUI{

	protected HashMap<GameItem, Integer> items = new HashMap<>();

	public ShopGUI(JCore main, Component displayName, int sizeInRows, boolean filler) {
		super(main, displayName, sizeInRows, filler);
	}

	@Override
	public void insertItems(GamePlayer player, Inventory inventory) {
		for(Map.Entry<GameItem, Integer> map : items.entrySet()){
			inventory.addItem(ItemBuilder.buildShopItem(map.getKey(), map.getValue()));
		}
	}

	@Override
	public void onClick(InventoryClickEvent e, GamePlayer player) {
		e.setCancelled(true);
		NBTShopItem item = new NBTShopItem(e.getCurrentItem());
		if(!item.isShopItem()) return;

		GameItem gameItem = itemManager.getItem(item.getItemID());
		if(gameItem == null) return;
		int amount = item.getAmount();

		int price = gameItem.getBuyPrice() *amount;

		if(player.getCoins() >= price){

			if(!InventoryHelper.isFull(player.getInventory())) {
				player.minusCoins(price);
				player.getInventory().addItem(ItemBuilder.buildGameItem(gameItem, amount));
			}else{
				player.sendMessage(ChatColor.RED + "Your inventory is full!");
				//TODO: Add sound when audio manager is created.
			}

		}

	}

	@Override
	public void insertFiller(Inventory inventory) {
		for(int i = 0; i < 9; i++){
			inventory.setItem(i, EMPTY);
		}

		for(int i = 0; i < (getSizeInRows()-2)*2; i++){
			inventory.setItem(9*(i+1), EMPTY);
			inventory.setItem(17*(i+1), EMPTY);
		}

		for(int i = size-9; i < size; i++){
			inventory.setItem(i, EMPTY);
		}
	}

	public HashMap<GameItem, Integer> getItems() {
		return items;
	}
}
