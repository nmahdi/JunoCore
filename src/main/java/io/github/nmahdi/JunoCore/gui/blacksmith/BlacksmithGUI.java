package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlacksmithGUI extends NPCGUI {

    private ItemStack dismantle = new ItemStackBuilder(Material.BLAZE_POWDER).setName("Dismantle", NamedTextColor.AQUA, false).skipLore().addLore("Dismantle old equipment", TextColors.GRAY_DESCRIPTION, false)
            .addLore("break open geodes.", TextColors.GRAY_DESCRIPTION, false).build();
    private ItemStack upgrade = new ItemStackBuilder(Material.ANVIL).setName("Upgrade", NamedTextColor.AQUA, false).skipLore().addLore("Upgrade equipment and weapons!", TextColors.GRAY_DESCRIPTION, false).build();
    private ItemStack shop = new ItemStackBuilder(Material.EMERALD).setName("Shop", NamedTextColor.AQUA, false).skipLore().addLore("Click here to open the blacksmith shop!", TextColors.GRAY_DESCRIPTION, false).build();

    private DismantleGUI dismantleGUI;
    private UpgradeGUI upgradeGUI;
    private BlacksmithShopGUI shopGUI;

    public BlacksmithGUI(JCore main) {
        super(main, "Blacksmith", 54, null, "&7Blacksmith", "Dismantle and upgrade items at the blacksmith!");
        dismantleGUI = new DismantleGUI(main, this);
        upgradeGUI = new UpgradeGUI(main, this);
        shopGUI = new BlacksmithShopGUI(main, this);
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent e){
        if(e.getNPC().getName().equalsIgnoreCase(npcName)) openInventory(e.getClicker());
    }

    @Override
    public void onInvClick(InventoryClickEvent e){
        e.setCancelled(true);
        if(e.getCurrentItem().isSimilar(dismantle)) dismantleGUI.openInventory((Player)e.getWhoClicked());
        if(e.getCurrentItem().isSimilar(upgrade)) upgradeGUI.openInventory((Player)e.getWhoClicked());
        if(e.getCurrentItem().isSimilar(shop)) shopGUI.openInventory((Player)e.getWhoClicked());
    }

    @Override
    public void setItems(Inventory inventory, Player player) {
        inventory.setItem(20, dismantle);
        inventory.setItem(21, upgrade);
        inventory.setItem(22, shop);

        addNpcSkull(inventory);
        insertFiller(inventory);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

}
