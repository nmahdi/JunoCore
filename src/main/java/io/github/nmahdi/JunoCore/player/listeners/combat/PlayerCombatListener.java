package io.github.nmahdi.JunoCore.player.listeners.combat;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.AttackItemAbility;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.RightClickAbility;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

    @EventHandler
    public void armorStand(PlayerArmorStandManipulateEvent e){
        e.setCancelled(true);
    }

    /**
     * Ran every time a player takes any type of damage.
     * Vanilla damage is set to 0
     */
    @EventHandler
    public void onVanillaDamage(EntityDamageEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        if(!(e.getEntity() instanceof Player)) return;
        e.setDamage(0);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(!e.getAction().isRightClick()) return;

        GamePlayer player = playerManager.getPlayer(e.getPlayer());

        if(!player.hasHeldItem()) return;
        if(player.getHeldItem() instanceof RightClickAbility){
            ((RightClickAbility) player.getHeldItem()).onRightClick(main, player, player.getNBTHeldItem());
        }
    }

    @EventHandler
    public void onPlayerDamaged(NPCDamageEntityEvent e){
        if(!e.getNPC().hasTrait(StatsTrait.class)) return;
        if(!(e.getDamaged() instanceof Player)) return;
        if(e.getDamaged().hasMetadata("NPC")) return;

        GamePlayer player = playerManager.getPlayer((Player)e.getDamaged());

        //player.getStats().minusHealth((int)e.getNPC().getTraitNullable(StatsTrait.class).getDamage());
    }

    /**
     *  Checks if the player has been hit by a projectile that was shot by an NPC that has the Stats Trait
     */
    @EventHandler
    public void onProjectileHitPlayer(EntityDamageByEntityEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        if(!(e.getEntity() instanceof Player)) return;

        if(e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) return;
        if(!(((Projectile)e.getDamager()).getShooter() instanceof LivingEntity)) return;

        LivingEntity entity = (LivingEntity)((Projectile) e.getDamager()).getShooter();

        if(!entity.hasMetadata("NPC")) return;

        NPC npc = entityManager.getMainRegistry().getByUniqueId(entity.getUniqueId());

        if(npc != null && npc.hasTrait(StatsTrait.class)){
            //playerManager.getPlayer((Player)e.getEntity()).damage((int) npc.getTraitNullable(StatsTrait.class).getDamage(), DamageType.Projectile, null);
        }
    }

    /**
     * Checks if a player launched a projectile and sets the damage to the entity meta data
     */
    @EventHandler
    public void onProjectileShoot(PlayerLaunchProjectileEvent e){
        if(e.getPlayer().hasMetadata("NPC")) return;
        setProjectileMeta(playerManager.getPlayer(e.getPlayer()), e.getProjectile());
    }

    /**
     * Checks if a player shot a bow and sets the damage to the entity meta data
     */
    @EventHandler
    public void onArrowShoot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(e.getEntity().hasMetadata("NPC")) return;
        setProjectileMeta(playerManager.getPlayer((Player)e.getEntity()), e.getProjectile());
    }

    /**
     * Disables natural health regeneration
     */
    @EventHandler
    public void onPlayerHeal(EntityRegainHealthEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        if(e.getEntity() instanceof Player){
            if(e.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM) e.setCancelled(true);
        }
    }

    /**
     * Disables natural hunger regeneration
     */
    @EventHandler
    public void onHungerRegen(FoodLevelChangeEvent e){
        if(e.getEntity().hasMetadata("NPC")) return;
        e.setCancelled(true);
    }

    /**
     *  Checks if the npc damaged has the Stats trait, if it does it damages it
     */
    @EventHandler
    public void onGameEntityDamage(NPCDamageByEntityEvent e){
        if(!e.getNPC().hasTrait(StatsTrait.class)) return;
        if(!(e.getDamager() instanceof Player)) return;

        GamePlayer player = playerManager.getPlayer((Player)e.getDamager());
        /*
        boolean weaponDamage = true;

        if(player.getHeldItem() != null && player.getHeldItem().isWeapon()){
            if(player.getHeldItem().getItemType() != ItemType.Sword) weaponDamage = false;
        }
        */
        if(player.getHeldItem() != null && player.getHeldItem() instanceof AttackItemAbility){
            ((AttackItemAbility) player.getHeldItem()).onAttack(main, e.getNPC().getTraitNullable(StatsTrait.class), player, player.getNBTHeldItem());
        }
        e.getNPC().getTraitNullable(StatsTrait.class).damage((Player)e.getDamager(), player.getDamage(random));
    }

    /**
     * Checks if an entity with the Stats Trait was hit by a projectile
     */
    @EventHandler
    public void onHurt(NPCDamageByEntityEvent e){
        if(!e.getNPC().hasTrait(StatsTrait.class)) return;

        e.setDamage(0);

        if(!(e.getDamager() instanceof Projectile)) return;

        if(!(((Projectile)e.getDamager()).getShooter() instanceof Player)) return;

        if(!e.getDamager().hasMetadata(GamePlayer.PROJECTILE_META)) return;

        Player p = (Player)((Projectile)e.getDamager()).getShooter();
        if(p.hasMetadata("NPC")) return;

        e.getNPC().getTraitNullable(StatsTrait.class).damage(p, e.getDamager().getMetadata(GamePlayer.PROJECTILE_META).get(0).asInt());
    }

    private void setProjectileMeta(GamePlayer player, Entity projectile){
        projectile.setMetadata(GamePlayer.PROJECTILE_META, new FixedMetadataValue(main, player.getDamage(random)));
    }

}
