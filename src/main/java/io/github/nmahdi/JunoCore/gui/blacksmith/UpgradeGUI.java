package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends NPCGUI {

    private int insertSlot = 22;
    ItemStack upgrade = new ItemStackBuilder(Material.ANVIL).setName("&bUpgrade", TextColors.GRAY_DESCRIPTION, false).build();

    public UpgradeGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main, "Upgrade", 54, blacksmithGUI, blacksmithGUI.getName(), blacksmithGUI.getSkullLore());
    }

    @Override
    public void onInvClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void setItems(Inventory inventory, Player player) {
        addNpcSkull(inventory);
        insertBack(inventory);
        insertFiller(inventory);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

}
