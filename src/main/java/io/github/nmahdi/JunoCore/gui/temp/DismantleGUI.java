package io.github.nmahdi.JunoCore.gui.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Dismantlable;
import io.github.nmahdi.JunoCore.loot.items.ItemDrop;
import io.github.nmahdi.JunoCore.loot.items.WeightedItemLootTable;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DismantleGUI extends GUI implements LinkedGUI{

	private BlacksmithGUI blacksmithGUI;

	private final int inputSlot = 13;
	private ItemStack DISMANTLE = new ItemStackBuilder(Material.BLAZE_POWDER).setName("Dismantle", NamedTextColor.RED, false).build();

	public DismantleGUI(JCore main, BlacksmithGUI blacksmithGUI) {
		super(main, Component.text("Blacksmith ~ Dismantle"), 34, true);
		this.blacksmithGUI = blacksmithGUI;
	}

	@Override
	public void insertItems(GamePlayer player, Inventory inventory) {
		inventory.setItem(22, DISMANTLE);
	}

	@Override
	public void onClick(InventoryClickEvent e, GamePlayer player) {
		if(e.getClickedInventory().getType() != InventoryType.PLAYER){
			if(e.getSlot() != inputSlot) e.setCancelled(true);
		}

		if(!e.getCurrentItem().isSimilar(DISMANTLE)) return;

		if(InventoryHelper.isAirOrNull(e.getInventory().getItem(inputSlot))) return;

		GameItem item = itemManager.getItem(e.getInventory().getItem(inputSlot));
		if(item == null) return;

		if(item instanceof Dismantlable){
			Dismantlable dismantlable = (Dismantlable)item;

			if(dismantlable.getLootTable().isWeighted()) {
				WeightedItemLootTable lootTable = (WeightedItemLootTable) dismantlable.getLootTable();
				ItemDrop drop = lootTable.roll(random);

				if(e.getCurrentItem().getAmount() > 1){
					e.getCurrentItem().setAmount(e.getCurrentItem().getAmount()-1);
				}else{
					e.setCurrentItem(null);
				}

				player.addItem(drop.getItem(), drop.rollAmount(random));
				//TODO: Add success audio
				return;
			}
		}
		//Fail audio here

	}


	@Override
	public void onClose(InventoryCloseEvent e, GamePlayer player) {
		if(!InventoryHelper.isAirOrNull(e.getInventory().getItem(inputSlot))){
			player.getInventory().addItem(e.getInventory().getItem(inputSlot));
		}
	}

	@Override
	public void onOpen(InventoryOpenEvent e, GamePlayer player) {

	}

	@Override
	public GUI getPreviousGUI() {
		return blacksmithGUI;
	}
}
