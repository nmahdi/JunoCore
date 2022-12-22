package io.github.nmahdi.JunoCore.gui.player.crafting;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.CraftingGUI;
import io.github.nmahdi.JunoCore.gui.player.PlayerCraftingGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ToolsCraftingGUI extends CraftingGUI {

	public ItemStack ICON = new ItemStackBuilder(Material.NETHERITE_PICKAXE).setName("Tools", NamedTextColor.AQUA, false).build();

	public ToolsCraftingGUI(JCore main, PlayerCraftingGUI craftingGUI) {
		super(main, "Crafting - Tools", 54, craftingGUI);
	}

	@Override
	public Recipe.Menu getMenuType() {
		return Recipe.Menu.Tools;
	}

	@Override
	public void onInvClose(Inventory inventory, Player player) {

	}
}
