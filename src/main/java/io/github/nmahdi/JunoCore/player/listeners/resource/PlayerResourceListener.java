package io.github.nmahdi.JunoCore.player.listeners.resource;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.effects.PacketManager;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.generation.BlockBreakHelper;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.utils.JLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class PlayerResourceListener implements Listener, BlockBreakHelper {

    private JCore main;
    private ResourceManager resourceManager;
    private PacketManager packetManager;
    private PlayerManager playerManager;
    private Random random;
    private ArrayList<BreakingBlock> breakingBlocks = new ArrayList<>();

    public PlayerResourceListener(JCore main){
        this.main = main;
        this.resourceManager = main.getResourceManager();
        this.packetManager = main.getPacketManager();
        this.playerManager = main.getPlayerManager();
        this.random = main.getRandom();
        main.getServer().getPluginManager().registerEvents(this, main);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                tick();
            }
        }, 1,1);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e){
        e.setCancelled(true);

        ResourceType resourceType;

        if(e.getBlock().hasMetadata("resource")) {
            resourceType = resourceManager.getResourceType(e.getBlock().getMetadata("resource").get(0).asString());
        }else{
            resourceType = resourceManager.getResourceType(e.getBlock());
        }

        if (resourceType == null) return;

        GamePlayer player = playerManager.getPlayer(e.getPlayer());
        if (player.getHeldItem() == null) return;
        if (player.getHeldItem().getItemType() != resourceType.getTool()) return;

        BreakingBlock breakingBlock = getBreakingBlock(e.getPlayer());
        if (breakingBlock == null) {
            breakingBlocks.add(new BreakingBlock(main, player, e.getBlock().getLocation(), resourceType).tick(packetManager, resourceManager));
        }
    }

    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent e){
        BreakingBlock breakingBlock = getBreakingBlock(e.getPlayer());
        if(breakingBlock == null) return;

        try {
            packetManager.hideBlockBreak(breakingBlock.getPlayer().getPlayerObject(), breakingBlock.getLocation().getBlock());
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }

        breakingBlocks.remove(breakingBlock);
    }

    //Ran every tick
    public void tick(){
        if(breakingBlocks.isEmpty()) return;
        for(int i = 0; i < breakingBlocks.size(); i++) {
            breakingBlocks.get(i).tick(packetManager, resourceManager);
        }
    }

    public BreakingBlock getBreakingBlock(Player player){
        for(BreakingBlock block : breakingBlocks){
            if(block.getPlayer().getUniqueId().equals(player.getUniqueId())) return block;
        }
        return null;
    }

}
