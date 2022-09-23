package io.github.nmahdi.JunoCore.entity.npc;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.JItemManager;
import io.github.nmahdi.JunoCore.item.builder.NBTItemStackBuilder;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BlacksmithNPC implements Listener {

    private final String NAME = "&7&lBlacksmith";

    private JItemManager itemManager;

    public BlacksmithNPC(JCore main, JItemManager itemManager){
        main.getServer().getPluginManager().registerEvents(this, main);
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent e){
            if(e.getClicker().getInventory().getItemInMainHand().getType().isAir()) return;
            NBTItem item = new NBTItem(e.getClicker().getInventory().getItemInMainHand());
            if(!item.hasKey("juno")) return;
            if(!item.getCompound("juno").getString("id").equals(JItem.Geode.getId())) return;
            e.getClicker().getInventory().setItemInMainHand(new NBTItemStackBuilder(JItem.CompactedDiamond).getStack());
    }

}
