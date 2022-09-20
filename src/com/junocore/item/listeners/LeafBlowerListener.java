package com.junocore.item.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class LeafBlowerListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR && e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
            NBTItem i = new NBTItem(e.getPlayer().getInventory().getItemInMainHand());
            if (i.hasCustomNbtData() && i.hasKey("juno") && i.getCompound("juno").getString("id").equals("leaf_blower")) {
                if (e.getBlock().getType() == Material.OAK_LEAVES) {
                    int bx = e.getBlock().getLocation().getBlockX();
                    int by = e.getBlock().getLocation().getBlockY();
                    int bz = e.getBlock().getLocation().getBlockZ();
                    if (i.getCompound("juno").hasKey("radius")) {
                        if (StringUtils.isNumeric(i.getString("radius"))) {
                            int radius = Integer.parseInt(i.getCompound("juno").getString("radius"));
                            for (int x = bx - radius; x < bx + radius; x++) {
                                for (int z = bz - radius; z < bz + radius; z++) {
                                    for (int y = by - radius; y < by + radius; y++) {
                                        if (e.getBlock().getWorld().getBlockAt(x, y, z).getType() == Material.OAK_LEAVES) {
                                            e.getBlock().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
