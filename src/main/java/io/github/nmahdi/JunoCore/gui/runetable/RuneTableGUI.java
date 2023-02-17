package io.github.nmahdi.JunoCore.gui.runetable;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.RuneItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RuneTableGUI extends GUI {

	private ItemManager itemManager;
	private PlayerManager playerManager;

	private final int runeSlot = 29;
	private final int inputSlot = 11;
	private final int outputSlot = 24;

	ItemStack accept = new ItemStackBuilder(Material.ENCHANTING_TABLE).setName("Confirm", TextColors.GRAY_DESCRIPTION, false).build();
	private ArrayList<String> outputs = new ArrayList<>();

	public RuneTableGUI(JCore main) {
		super(main, "Rune Table", 54, null);
		this.itemManager = main.getItemManager();
		this.playerManager = main.getPlayerManager();
	}

	@EventHandler
	public void onTableClick(PlayerInteractEvent e){
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(e.getClickedBlock().getType() != Material.ENCHANTING_TABLE) return;
		e.setCancelled(true);
		openInventory(e.getPlayer());
	}

	@Override
	public void onInvClick(InventoryClickEvent e) {
		if(e.getCurrentItem().isSimilar(accept)){
			e.setCancelled(true);

			if (InventoryHelper.isAirOrNull(e.getInventory().getItem(runeSlot)) || InventoryHelper.isAirOrNull(e.getInventory().getItem(inputSlot))) return;

			NBTGameItem inputItem = new NBTGameItem(e.getInventory().getItem(inputSlot));
			GameItem item = itemManager.getItem(inputItem.getID());
			GameItem rune = itemManager.getItem(new NBTGameItem(e.getInventory().getItem(runeSlot)).getID());

			if(item == null || rune == null) return;

			if (item instanceof Runeable && rune instanceof RuneItem) {
				Runeable runeableItem = (Runeable) item;
				RuneItem runeItem = (RuneItem) rune;

				if(inputItem.getRunesUsed()+runeItem.getCost() <= runeableItem.getMaxRunes()){

					if(!e.getInventory().getItem(outputSlot).isSimilar(OUTPUT)) return;

					if(!runeItem.canBeAppliedTo(item.getItemType().getCatagory())) return;

					outputs.add(e.getWhoClicked().getUniqueId().toString());
					inputItem.addRune(runeItem.getRuneType());

					inputItem.getItem().setItemMeta(ItemBuilder.updateMeta(playerManager.getPlayer((Player)e.getWhoClicked()), item, inputItem));

					e.getInventory().setItem(outputSlot, inputItem.getItem());
					e.getInventory().setItem(inputSlot, null);

					if(e.getInventory().getItem(runeSlot).getAmount() > 1){
						e.getInventory().setItem(runeSlot, e.getInventory().getItem(runeSlot).subtract(1));
					}else {
						e.getInventory().setItem(runeSlot, null);
					}
					e.getWhoClicked().getWorld().playSound(e.getWhoClicked(), Sound.BLOCK_ANVIL_USE, 1f, 1f);

				}
			}
		}

		if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
			if (e.getSlot() != runeSlot && e.getSlot() != inputSlot) {
				e.setCancelled(true);

				if (e.getSlot() == outputSlot) {
					if (!e.getCurrentItem().isSimilar(OUTPUT)) {
						if (outputs.contains(e.getWhoClicked().getUniqueId().toString())) {
							if (InventoryHelper.isAirOrNull(e.getCursor())) {
								e.getWhoClicked().setItemOnCursor(e.getCurrentItem());
								e.setCurrentItem(OUTPUT);
								outputs.remove(e.getWhoClicked().getUniqueId().toString());
								return;
							}
						}
					}
				}


			}
		}
	}

	@Override
	public void setItems(Inventory inventory, Player player) {
		inventory.setItem(22, accept);
		inventory.setItem(outputSlot, OUTPUT);

		insertFiller(inventory, runeSlot, inputSlot, outputSlot);
	}

	@Override
	public void onInvClose(Inventory inventory, Player player) {
		if(!InventoryHelper.isAirOrNull(inventory.getItem(outputSlot))){
			if(!inventory.getItem(outputSlot).isSimilar(OUTPUT)){
				player.getInventory().addItem(inventory.getItem(outputSlot));
			}
		}
		if(!InventoryHelper.isAirOrNull(inventory.getItem(inputSlot))){
			player.getInventory().addItem(inventory.getItem(inputSlot));
		}
		if(!InventoryHelper.isAirOrNull(inventory.getItem(runeSlot))){
			player.getInventory().addItem(inventory.getItem(runeSlot));
		}
	}

}
