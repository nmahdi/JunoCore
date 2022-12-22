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

public class MaterialsCraftingGUI extends CraftingGUI {

	public final ItemStack ICON = new ItemStackBuilder(Material.DIAMOND).setName("Materials", NamedTextColor.AQUA, true).build();


	public MaterialsCraftingGUI(JCore main, PlayerCraftingGUI craftingGUI) {
		super(main, "Crafting - Materials", 54, craftingGUI);
	}

	@Override
	public Recipe.Menu getMenuType() {
		return Recipe.Menu.Materials;
	}

	@Override
	public void onInvClose(Inventory inventory, Player player) {

	}
}
