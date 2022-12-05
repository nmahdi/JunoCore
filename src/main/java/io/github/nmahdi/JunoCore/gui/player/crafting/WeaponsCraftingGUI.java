package io.github.nmahdi.JunoCore.gui.player.crafting;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.CraftingGUI;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WeaponsCraftingGUI extends CraftingGUI {

	public ItemStack ICON = new ItemStackBuilder(Material.NETHERITE_SWORD).setName("Weapons", NamedTextColor.AQUA, false).build();

	public WeaponsCraftingGUI(JCore main, PlayerCraftingGUI craftingGUI) {
		super(main, "&7Crafting - Weapons", 54, craftingGUI);
	}

	@Override
	public Recipe.Menu getMenuType() {
		return Recipe.Menu.Weapons;
	}

	@Override
	public void onInvClose(Inventory inventory, Player player) {

	}
}
