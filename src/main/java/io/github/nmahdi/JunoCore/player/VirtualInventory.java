package io.github.nmahdi.JunoCore.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class VirtualInventory {

	private Player player;
	private ArrayList<ItemStack> items = new ArrayList<>();

	public VirtualInventory(Player player){
		this.player = player;
	}

	public void storeItems(ItemStack... items){
		this.items.addAll(List.of(items));
	}

	public void collectItems(){
		for(int i = 0; i < items.size(); i++){
			HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(items.get(i));
			items.remove(i);
			if(!leftOver.isEmpty()) {
				items.addAll(leftOver.values());
				break;
			}
		}
	}

	public Player getPlayer() {
		return player;
	}
}
