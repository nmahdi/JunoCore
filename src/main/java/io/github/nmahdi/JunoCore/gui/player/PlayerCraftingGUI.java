package io.github.nmahdi.JunoCore.gui.player;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.gui.player.PlayerMenuGUI;
import io.github.nmahdi.JunoCore.gui.player.crafting.MaterialsCraftingGUI;
import io.github.nmahdi.JunoCore.gui.player.crafting.ToolsCraftingGUI;
import io.github.nmahdi.JunoCore.gui.player.crafting.WeaponsCraftingGUI;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.bukkit.inventory.*;

public class PlayerCraftingGUI extends GUI {


    private MaterialsCraftingGUI materialsCraftingGUI;
    private ToolsCraftingGUI toolsCraftingGUI;
    private WeaponsCraftingGUI weaponsCraftingGUI;

    public PlayerCraftingGUI(JCore main, PlayerMenuGUI playerMenuGUI) {
        super(main, "Crafting", 54, playerMenuGUI);
        this.materialsCraftingGUI = new MaterialsCraftingGUI(main, this);
        this.toolsCraftingGUI = new ToolsCraftingGUI(main, this);
        this.weaponsCraftingGUI = new WeaponsCraftingGUI(main, this);
    }

    @EventHandler
    public void onRecipeBookClick(PlayerRecipeBookClickEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onRecipeUnlock(PlayerRecipeDiscoverEvent e){
        e.setCancelled(true);
    }


    @Override
    public void onInvClick(InventoryClickEvent e) {
        if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem().isSimilar(materialsCraftingGUI.ICON)){
            materialsCraftingGUI.openInventory((Player)e.getWhoClicked());
        }
        if(e.getCurrentItem().isSimilar(toolsCraftingGUI.ICON)){
            toolsCraftingGUI.openInventory((Player)e.getWhoClicked());
        }
        if(e.getCurrentItem().isSimilar(weaponsCraftingGUI.ICON)){
            weaponsCraftingGUI.openInventory((Player)e.getWhoClicked());
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = createInventory();

        inventory.setItem(10, materialsCraftingGUI.ICON);
        inventory.setItem(11, toolsCraftingGUI.ICON);
        inventory.setItem(12, weaponsCraftingGUI.ICON);


        insertBack(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }


}
