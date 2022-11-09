package io.github.nmahdi.JunoCore.item.listeners;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.stats.ConsumableID;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.player.NBTPlayer;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ConsumableListener implements Listener {

    private JCore main;

    public ConsumableListener(JCore main){
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
        if(e.getItem() == null || e.getItem().getType().isAir()) return;
        if(!e.getItem().hasItemMeta()) return;
        NBTJItem item = new NBTJItem(e.getItem());
        NBTPlayer player = new NBTPlayer(e.getPlayer());
        if(!item.hasJuno()) return;
        if(!item.isConsumable()) return;
        for(ConsumableID id : ConsumableID.values()){
            if(item.getStats().hasKey(id.getPlayerID().getId())){
                switch(id){
                    case Mana:
                        if(player.getStat(id.getPlayerID())+Integer.parseInt(item.getStat(id.getPlayerID().getId())) > player.getStat(PlayerStatID.MaxMana)){
                            player.setMana(player.getMaxMana());
                        }else{
                            player.plusMana(Integer.parseInt(item.getStat(id.getPlayerID().getId())));
                        }
                        break;
                    case Health:
                        if(player.getStat(id.getPlayerID())+Integer.parseInt(item.getStat(id.getPlayerID().getId())) > player.getStat(PlayerStatID.MaxHealth)){
                            player.setHealth(player.getMaxHealth());
                        }else{
                            player.plusHealth(Integer.parseInt(item.getStat(id.getPlayerID().getId())));
                        }
                        break;
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                e.getPlayer().getInventory().setItemInMainHand(null);
            }
        },1);
    }

}
