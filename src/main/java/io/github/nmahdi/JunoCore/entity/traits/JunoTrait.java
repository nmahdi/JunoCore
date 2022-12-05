package io.github.nmahdi.JunoCore.entity.traits;

import com.destroystokyo.paper.entity.RangedEntity;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.loot.*;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import net.citizensnpcs.api.ai.EntityTarget;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Random;

@TraitName("juno")
public class JunoTrait extends Trait {

    private final JCore main;
    private final Random random;
    private final HologramManager hologramsManager;

    private final String name;
    private final int level;
    private final int maxHealth;
    private int health;
    private final int xp;
    private final LootTable lootTable;

    private final double damagePerHit;
    private final double attackRange;
    private final double distanceMargin;
    private final int attackDelay;
    private final float speedModifier;
    private final boolean ranged;

    private int attackTicks = 0;

    private Material heldItem;
    private Material helmet, chestplate, leggings, boots;

    private final Equipment equipmentTrait  = new Equipment();

    public JunoTrait(GameEntity entity) {
        super("juno");
        this.main = JavaPlugin.getPlugin(JCore.class);
        this.random = main.getRandom();
        this.hologramsManager = main.getHologramsManager();
        this.name = entity.getDisplayName();
        this.level = random.nextInt(entity.getMinLevel(), entity.getMaxLevel()+1);
        this.maxHealth = entity.getBaseHealth();
        this.health = maxHealth;
        this.xp = entity.getCombatXP();
        this.lootTable = entity.getLootTable();
        this.damagePerHit = entity.getDamagePerHit();
        this.attackRange = entity.getAttackRange();
        this.distanceMargin = entity.getDistanceMargin();
        this.attackDelay = entity.getAttackDelay();
        this.speedModifier = entity.getSpeedModifier();
        this.ranged = entity.isRanged();
        if(entity.hasHeldItem()) heldItem = entity.heldItem();
        if(entity.hasHelmet()) helmet = entity.getHelmet();
        if(entity.hasChestplate()) chestplate = entity.getChestplate();
        if(entity.hasLeggings()) leggings = entity.getLeggings();
        if(entity.hasBoots()) boots = entity.getBoots();
    }

    @Override
    public void run() {
        if(!npc.isSpawned()) return;
        attackTicks++;
        if(getEntityTarget() == null) {
            for (Entity entity : getNPC().getEntity().getWorld().getNearbyPlayers(getNPC().getStoredLocation(), 35)) {
                if(!entity.hasMetadata("NPC")) {
                    getNPC().getNavigator().setTarget(entity, true);
                    break;
                }
            }
        }else{
            if(attackTicks >= attackDelay) {
                if (getNPC().getEntity() instanceof RangedEntity && ranged) {
                    ((RangedEntity) getNPC().getEntity()).setChargingAttack(true);
                    if (((RangedEntity) getNPC().getEntity()).hasLineOfSight(getEntityTarget().getTarget())) {
                        ((RangedEntity) getNPC().getEntity()).rangedAttack((LivingEntity) getEntityTarget().getTarget(), 1);
                        attackTicks = 0;
                    }
                }
            }
            if(getEntityTarget().getTarget().getLocation().distance(getNPC().getStoredLocation()) > 35){
                getNPC().getNavigator().setTarget(null, false);
            }
        }
        /*if(getNPC().getNavigator().getEntityTarget() != null){
            getNPC().faceLocation(getNPC().getNavigator().getEntityTarget().getTarget().getLocation());
        } */
    }

    @Override
    public void onAttach() {
        updateName();

        getNPC().getNavigator().getLocalParameters().attackRange(attackRange).distanceMargin(distanceMargin).attackDelayTicks(attackDelay).speedModifier(speedModifier);

        getNPC().addTrait(equipmentTrait);

        if(heldItem != null) equipmentTrait.set(Equipment.EquipmentSlot.HAND, new ItemStack(heldItem));
        if(helmet != null) equipmentTrait.set(Equipment.EquipmentSlot.HELMET, new ItemStack(helmet));
        if(chestplate != null) equipmentTrait.set(Equipment.EquipmentSlot.CHESTPLATE, new ItemStack(chestplate));
        if(leggings != null) equipmentTrait.set(Equipment.EquipmentSlot.LEGGINGS, new ItemStack(leggings));
        if(boots != null) equipmentTrait.set(Equipment.EquipmentSlot.BOOTS, new ItemStack(boots));
    }

    public void damage(Player player, int damage){
        if(!getEntityTarget().getTarget().getUniqueId().equals(player.getUniqueId())) {
            getNPC().getNavigator().setTarget(player, true);
        }
        boolean dying = damage(damage);
        if(dying){
            hologramsManager.createXPHologram(player.getLocation().add(0, 1.2, 0), xp, Skill.Combat, player);
            /*for(SpawnZone zone : entityManager.getSpawnZones()){
                zone.despawn(entityManager, e.getEntity().getUniqueId().toString());
            } */
        }
    }

    public boolean damage(int damage){
        health-=damage;
        updateName();
        hologramsManager.createDamageHologram(getNPC().getStoredLocation(), damage);
        if(health <= 0){
            kill();
            return true;
        }
        return false;
    }

    private void kill() {
        health = 0;
        ((LivingEntity) getNPC().getEntity()).setHealth(0);
    }

    @EventHandler
    public void onHurt(NPCDamageByEntityEvent e){
        if(e.getNPC() != this.getNPC()) return;
        e.setDamage(0);
        if(e.getDamager() instanceof Projectile){

            if(((Projectile)e.getDamager()).getShooter() instanceof Player){

                Player p = (Player)((Projectile)e.getDamager()).getShooter();

                if(p.hasMetadata("NPC")) return;

                if(!e.getDamager().hasMetadata(GamePlayer.PROJECTILE_META)) return;

                damage(p, e.getDamager().getMetadata(GamePlayer.PROJECTILE_META).get(0).asInt());
            }
        }
    }
    //Add this to the player's combat listener, and set the custom damage.
    /*@EventHandler
    public void onHit(NPCDamageEntityEvent e){
        if(e.getNPC() != this.getNPC()) return;
        if(e.getDamaged() instanceof Player){
            if(e.getDamaged().hasMetadata("NPC")) return;
            new NBTPlayer((Player)e.getDamaged()).damage((int)damagePerHit, DamageType.Physical, null);
        }
    } */

    @EventHandler
    public void onFallDamage(NPCDamageEvent e){
        if(e.getNPC() != this.getNPC()) return;
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            damage((int)e.getDamage());
        }
    }

    @EventHandler
    public void onDeath(NPCDeathEvent e){
        if(e.getNPC() != this.getNPC())return;
        if(!lootTable.isWeighted()){
            ChanceLootTable container = (ChanceLootTable) lootTable.getTable();
            for(Map.Entry<Loot, Float> map : container.getLoot().entrySet()){
                float chance = random.nextFloat(100);
                if(chance <= map.getValue()){
                    int amount = random.nextInt(map.getKey().getMinAmount(), map.getKey().getMaxAmount()+1);
                    if(map.getKey().getMinAmount() == map.getKey().getMaxAmount()) amount = map.getKey().getMaxAmount();
                    getNPC().getStoredLocation().getWorld().dropItemNaturally(getNPC().getStoredLocation(), ItemBuilder.buildGameItem(map.getKey().getItem(), amount));
                }
            }
        }
    }

    @EventHandler
    public void onCombustion(EntityCombustEvent e) {
        if (e instanceof EntityCombustByBlockEvent) return;
        if (e instanceof EntityCombustByEntityEvent) return;
        if (!e.getEntity().hasMetadata("NPC")) return;
        e.setCancelled(true);
    }

    private void updateName(){
        getNPC().setName(ChatColor.translateAlternateColorCodes('&',
                "&7[" + level + "] &c" + name + " &" +
                        getHealthColor(health, maxHealth) + Math.max(health, 0) + "&7/&a" + maxHealth + "&c" + PlayerStat.Health.getSymbol()));
    }

    private char getHealthColor(double health, double maxHealth){
        double percentage = (health/maxHealth)*100;
        if(percentage >= 70) return 'a';
        if(percentage >= 20) return 'e';
        return 'c';
    }

    private EntityTarget getEntityTarget(){
        return getNPC().getNavigator().getEntityTarget();
    }

    public double getDamagePerHit() {
        return damagePerHit;
    }

}
