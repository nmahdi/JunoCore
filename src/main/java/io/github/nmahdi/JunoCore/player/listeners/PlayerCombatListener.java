package io.github.nmahdi.JunoCore.player.listeners;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.JHologramsManager;
import io.github.nmahdi.JunoCore.entity.JEntityManager;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import io.github.nmahdi.JunoCore.player.NBTPlayer;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Random;

public class PlayerCombatListener implements Listener {

    private JCore main;
    private Random random;
    private JPlayerManager playerManager;
    private JEntityManager entityManager;
    private JHologramsManager hologramsManager;

    public PlayerCombatListener(JCore main){
        this.main = main;
        this.random = main.getRandom();
        this.playerManager = main.getPlayerManager();
        this.entityManager = main.getEntityManager();
        this.hologramsManager = main.getHologramsManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerDamage(NPCDamageEntityEvent e){
        if(e.getDamaged() instanceof Player){
            if(e.getDamaged().hasMetadata("NPC")) return;
            double damage = e.getDamage();
            e.setDamage(0);
            NBTPlayer player = new NBTPlayer((Player)e.getDamaged());
            player.minusHealth((int)damage);
            double health = ((double)player.getHealth()/(double)player.getMaxHealth())*10;
            ((Player) e.getDamaged()).setHealth(health*2);
        }
    }

    @EventHandler
    public void onPlayerHeal(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerRegen(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    /**
     * Event handling player deaths
     * Clears mobs drops
     */
    @EventHandler
    public void onPlayerDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Player){
            /**
             * INSERT PLAYER DEATH CODE
             */
            return;
        }
        e.getDrops().clear();
        e.setDroppedExp(0);
    }

}
