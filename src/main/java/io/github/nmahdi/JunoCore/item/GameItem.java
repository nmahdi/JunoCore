package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class GameItem {

	private String id;
	private String displayName;
	private Material material;
	private Rarity rarity;
	private ItemType itemType;
	private String description;

	private boolean hasUUID = false;
	private int sellPrice = 0;
	private int buyPrice = 0;

	private int customModelData = 0;

	public GameItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, ItemType itemType){
		this.id = id;
		this.displayName = displayName;
		this.material = material;
		this.rarity = rarity;
		this.itemType = itemType;
		itemManager.addItem(this);
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Material getMaterial() {
		return material;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public String getDescription() {
		return description;
	}

	public boolean hasUUID() {
		return hasUUID;
	}

	public void setHasUUID(boolean hasUUID) {
		this.hasUUID = hasUUID;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	public boolean hasCustomModelData(){
		return customModelData != 0;
	}

	public void setCustomModelData(int customModelData){
		this.customModelData = customModelData;
	}

	public int getCustomModelData(){
		return customModelData;
	}

}
