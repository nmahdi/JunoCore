package io.github.nmahdi.JunoCore.gui.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BankGUI extends NPCGUI {

    private PlayerManager playerManager;

    private ItemStack coins = new ItemStackBuilder(Material.GOLD_NUGGET).setName("Coins", NamedTextColor.GOLD, false).build();

    private ItemStack deposit = new ItemStackBuilder(Material.PAPER).setName("Deposit Coins", NamedTextColor.AQUA, false)
            .addLore("Click to deposit all your coins!", TextColors.GRAY, false).build();
    private ItemStack withdraw = new ItemStackBuilder(Material.MAP).setName("Withdraw Coins", NamedTextColor.AQUA, false)
            .addLore("Click to withdraw all your coins!", TextColors.GRAY, false).build();

    public BankGUI(JCore main, PlayerMenuGUI playerMenuGUI) {
        super(main, "&6Bank", 54, playerMenuGUI, "&7Banker", "Deposit your coins into the bank!");
        this.playerManager = main.getPlayerManager();
    }

    @Override
    public void onInvClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent e){
        if(!e.getNPC().getName().equals(getNPCName())) return;
        openInventory(e.getClicker());
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        GamePlayer gamePlayer = playerManager.getPlayer(player);

        inventory.setItem(20, deposit);
        inventory.setItem(22, new ItemStackBuilder(coins).addLore(String.valueOf(gamePlayer.getCoins()), TextColors.WHITE, false).build());
        inventory.setItem(24, withdraw);

        insertBack(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }
}
