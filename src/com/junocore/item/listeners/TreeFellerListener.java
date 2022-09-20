package com.junocore.item.listeners;

import com.junocore.JCore;
import com.junocore.events.SkillXPGainEvent;
import com.junocore.item.JItem;
import com.junocore.item.JItemManager;
import com.junocore.item.stats.ItemStatID;
import com.junocore.player.skills.SkillID;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TreeFellerListener implements Listener {

    private final JCore main;
    private final JItemManager itemManager;

    public TreeFellerListener(JCore main, JItemManager itemManager){
        this.main = main;
        this.itemManager = itemManager;
    }

    /**
     * Used to check if the next block is the same type as the old block
     *
     * @param original - Original block
     * @param toBreak - Next block to break
     * @return
     */
    public boolean isSameBlock(Block original, Block toBreak){
        return original.getType().equals(toBreak.getType());
    }

    public boolean isLog(Block block){ return block.getType().toString().contains("LOG"); }

    private HashMap<Block, Double> calcBlocks(World world, Block centerBlock){
        HashMap<Block, Double> blocks = new HashMap<>();
        Block current = centerBlock;
            for (int x = centerBlock.getX() - 10; x < centerBlock.getX() + 10; x++) {
                for (int y = centerBlock.getY() - 10; y < centerBlock.getY() + 10; y++) {
                    for (int z = centerBlock.getZ() - 10; z < centerBlock.getZ() + 10; z++) {
                        current = world.getBlockAt(x, y, z);
                        if(isLog(current)) {
                            if (isSameBlock(centerBlock, current)) {
                                blocks.put(current, calcDistance(centerBlock, current));
                            }
                        }
                    }
                }
            }
        return blocks;
    }

    private double calcDistance(Block centerBlock, Block newBlock){
        return centerBlock.getLocation().distance(newBlock.getLocation());
    }
    public List<Block> sortMap(HashMap<Block, Double> map){
        List<Map.Entry<Block, Double>> temp = new LinkedList<Map.Entry<Block, Double>>(map.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<Block, Double> >() {
            public int compare(Map.Entry<Block, Double> o1,
                               Map.Entry<Block, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        List<Block> total = new ArrayList<>();
        for(Map.Entry<Block, Double> m : temp){
            total.add(m.getKey());
        }
        return total;
    }


    /**
     * Event handler
     *
     * @param e - Auto-supplied when event is called.
     */
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.isCancelled()) return;

        Player player = e.getPlayer();

        ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
        if(itemStack.getType() == Material.AIR) return;
        NBTItem item = new NBTItem(e.getPlayer().getInventory().getItemInMainHand());
        if(!itemManager.isJunoItem(e.getPlayer().getInventory().getItemInMainHand())) return;
        if(!itemManager.getItemID(e.getPlayer().getInventory().getItemInMainHand()).equalsIgnoreCase(JItem.TreeFeller.getId())) return;

        List<Block> total = sortMap(calcBlocks(e.getPlayer().getWorld(), e.getBlock()));
        int bc = Integer.parseInt(item.getCompound("juno").getCompound("stats").getString(ItemStatID.Power.getID()));
        Bukkit.getServer().getPluginManager().callEvent(new SkillXPGainEvent(e.getPlayer(), SkillID.Foraging, 5));
        if(total.size() < bc) bc = total.size();
        for(int i = 0; i < bc; i++){
            if(i-1 >= 0){
                total.get(i).breakNaturally();
                Bukkit.getServer().getPluginManager().callEvent(new SkillXPGainEvent(e.getPlayer(), SkillID.Foraging, 5));
            }
        }
    }

}