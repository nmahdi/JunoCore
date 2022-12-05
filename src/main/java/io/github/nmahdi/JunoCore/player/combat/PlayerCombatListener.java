package io.github.nmahdi.JunoCore.player.combat;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.entity.traits.JunoTrait;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.stats.DamageType;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class PlayerCombatListener implements Listener {

    private JCore main;
    private Random random;
    private PlayerManager playerManager;
    private GameEntityManager entityManager;
    private HologramManager hologramsManager;

    public PlayerCombatListener(JCore main){
        this.main = main;
        this.random = main.getRandom();
        this.playerManager = main.getPlayerManager();
        this.entityManager = main.getEntityManager();
        this.hologramsManager = main.getHologramsManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    /**
     * Ran every time a player takes any type of damage.
     * Vanilla damage is set to 0
     */
    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        if(!(e.getEntity() instanceof Player)) return;
        e.setDamage(0);
    }

    /**
     *  Checks if the player has been hit by a projectile that was shot by an NPC that has the Juno Trait
     */
    @EventHandler
    public void onProjectileHitPlayer(EntityDamageByEntityEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        if(!(e.getEntity() instanceof Player)) return;

        if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            if(((Projectile)e.getDamager()).getShooter() instanceof LivingEntity){

                LivingEntity entity = (LivingEntity)((Projectile) e.getDamager()).getShooter();
                if(entity.hasMetadata("NPC")){
                    NPC npc = entityManager.getRegistry().getByUniqueId(entity.getUniqueId());
                    if(npc != null && npc.hasTrait(JunoTrait.class)){

                        playerManager.getPlayer((Player)e.getEntity()).damage((int) npc.getOrAddTrait(JunoTrait.class).getDamagePerHit(), DamageType.Projectile, null);
                    }
                }
            }
        }
    }

    /**
     *  Checks if the npc damaged has the Juno trait, if it does it damages it
     */
    @EventHandler
    public void onGameEntityDamage(NPCDamageByEntityEvent e){
        if(!e.getNPC().hasTrait(JunoTrait.class)) return;
        if(!(e.getDamager() instanceof Player)) return;
        GamePlayer player = playerManager.getPlayer((Player)e.getDamager());
        boolean weaponDamage = true;
        if(player.getHeldItem() != null && player.getHeldItem().isWeapon()){
            if(player.getHeldItem().getItemType() != ItemType.Sword) weaponDamage = false;
        }
        e.getNPC().getOrAddTrait(JunoTrait.class).damage((Player)e.getDamager(), player.getDamage(random));
    }

    @EventHandler
    public void onProjectileShoot(PlayerLaunchProjectileEvent e){
        if(e.getPlayer().hasMetadata("NPC")) return;
        setProjectileMeta(playerManager.getPlayer(e.getPlayer()), e.getProjectile());
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(e.getEntity().hasMetadata("NPC")) return;
        setProjectileMeta(playerManager.getPlayer((Player)e.getEntity()), e.getProjectile());
    }

    @EventHandler
    public void onPlayerHeal(EntityRegainHealthEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        if(e.getEntity() instanceof Player){
            if(e.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerRegen(FoodLevelChangeEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        e.setCancelled(true);
    }

    private void setProjectileMeta(GamePlayer player, Entity projectile){
        projectile.setMetadata(GamePlayer.PROJECTILE_META, new FixedMetadataValue(main, player.getDamage(random)));
    }

}
