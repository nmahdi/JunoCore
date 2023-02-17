package io.github.nmahdi.JunoCore.gui.temp;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlacksmithGUI extends GUI{

	private ItemStack DISMANTLE_ITEM = new ItemStackBuilder(Material.BLAZE_POWDER).setName("Dismantle", NamedTextColor.AQUA, false).skipLore().addLore("Dismantle old equipment", TextColors.GRAY_DESCRIPTION, false)
			.addLore("break open geodes.", TextColors.GRAY_DESCRIPTION, false).build();
	private ItemStack UPGRADE_ITEM = new ItemStackBuilder(Material.ANVIL).setName("Upgrade", NamedTextColor.AQUA, false).skipLore().addLore("Upgrade equipment and weapons!", TextColors.GRAY_DESCRIPTION, false).build();
	private ItemStack SHOP_ITEM = new ItemStackBuilder(Material.EMERALD).setName("Shop", NamedTextColor.AQUA, false).skipLore().addLore("Click here to open the blacksmith shop!", TextColors.GRAY_DESCRIPTION, false).build();


	private DismantleGUI dismantleGUI;
	private UpgradeGUI upgradeGUI;
	private BlacksmithShopGUI blacksmithShopGUI;

	public BlacksmithGUI(JCore main) {
		super(main, Component.text("Blacksmith"), 54, true);
		dismantleGUI = new DismantleGUI(main, this);
		upgradeGUI = new UpgradeGUI(main, this);
		blacksmithShopGUI = new BlacksmithShopGUI(main, this);
	}

	@Override
	public void insertItems(GamePlayer player, Inventory inventory) {
		inventory.setItem(20, DISMANTLE_ITEM);
		inventory.setItem(21, UPGRADE_ITEM);
		inventory.setItem(22, SHOP_ITEM);
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
}
