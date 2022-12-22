package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.gui.ShopGUI;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class BlacksmithShopGUI extends NPCGUI  implements ShopGUI {

    private PlayerManager playerManager;
    private ArrayList<GameItem> items = new ArrayList<>();
    private int index = 10;

    public BlacksmithShopGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main, "Blacksmith Shop", 54, blacksmithGUI, blacksmithGUI.getNPCName(), blacksmithGUI.getSkullLore());
        this.playerManager = main.getPlayerManager();
    }

    @Override
    public void onInvClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if(!e.getCurrentItem().hasItemMeta()) return;
        NBTGameItem nbtItem = new NBTGameItem(e.getCurrentItem());
        if(nbtItem.hasJuno() && nbtItem.getJuno().hasKey("price")){
            GameItem item = GameItem.getItem(nbtItem.getID());
            if (item == null) return;

            int price = nbtItem.getJuno().getInteger("price");
            GamePlayer player = playerManager.getPlayer((Player)e.getWhoClicked());
            if(player.getCoins() >= price) {
                player.minusCoins(price);
                player.getPlayerObject().getInventory().addItem(ItemBuilder.buildGameItem(item));
            }
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        for(int i = index; i < index+items.size(); i++){
            //inventory.setItem(i, ItemBuilder.buildShopItem(items.get(i)));
        }

        insertBack(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

    @Override
    public ArrayList<GameItem> getItems() {
        return items;
    }
}
