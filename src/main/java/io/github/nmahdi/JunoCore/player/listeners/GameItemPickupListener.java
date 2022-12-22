package io.github.nmahdi.JunoCore.player.listeners;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class GameItemPickupListener implements Listener {

    private PlayerManager playerManager;

    public GameItemPickupListener(JCore main){
        main.getServer().getPluginManager().registerEvents(this, main);
        this.playerManager = main.getPlayerManager();
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        NBTGameItem item = new NBTGameItem(e.getItem().getItemStack());
        GameItem gItem = GameItem.getItem(item.getID());
        if(item.hasJuno() && item.hasID() &&  gItem != null){
            e.getItem().getItemStack().setItemMeta(ItemBuilder.updateMeta(playerManager.getPlayer((Player)e.getEntity()), gItem, item));
        }

    }

}