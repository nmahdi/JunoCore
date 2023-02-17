package io.github.nmahdi.JunoCore.item.items.set;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class SetEffect {

	private ItemManager itemManager;
	private String name;
	private ArrayList<GameItem> items = new ArrayList<>();

	public SetEffect(ItemManager itemManager, String name, GameItem... setItems){
		this.itemManager = itemManager;
		this.name = name;
		this.items.addAll(List.of(setItems));
		itemManager.getSetEffects().add(this);
	}

	public abstract SetEffect onEquip(GamePlayer player);

	public abstract SetEffect tick(GamePlayer player);

	public abstract SetEffect onUnequip(GamePlayer player);


	public String getName() {
		return name;
	}

	public ArrayList<GameItem> getItems() {
		return items;
	}
}
