package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.JHologramsManager;
import io.github.nmahdi.JunoCore.entity.JEntity;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.loot.Loot;
import io.github.nmahdi.JunoCore.loot.LootContainer;
import io.github.nmahdi.JunoCore.loot.WeightedLootContainer;
import io.github.nmahdi.JunoCore.loot.LootTable;
import io.github.nmahdi.JunoCore.player.NBTPlayer;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import io.github.nmahdi.JunoCore.player.skills.SkillID;
import net.citizensnpcs.api.event.NPCCombustEvent;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Random;

@TraitName("juno")
public class JunoTrait extends Trait {

    private JCore main;
    private Random random;
    private JHologramsManager hologramsManager;

    String id;
    String name;
    int level;
    int maxHealth;
    int health;
    int xp;
    LootTable lootTable;

    private boolean agro = false;
    private Entity target;

    public JunoTrait(JEntity entity) {
        super("juno");
        main = JavaPlugin.getPlugin(JCore.class);
        random = main.getRandom();
        hologramsManager = main.getHologramsManager();
        this.id = entity.getId();
        this.name = entity.getDisplayName();
        this.level = random.nextInt(entity.getMinLevel(), entity.getMaxLevel()+1);
        this.maxHealth = entity.getBaseHealth();
        this.health = maxHealth;
        this.xp = entity.getXP();
        this.lootTable = entity.getLootTable();
    }

    @EventHandler
    public void onHit(NPCDamageByEntityEvent e){
        if(e.getNPC() != this.getNPC()) return;

        e.setDamage(0);
        if(!(e.getDamager() instanceof Player)) return;
        target = e.getDamager();
        NBTPlayer player = new NBTPlayer((Player)e.getDamager());

        int oldHealth = health;
        int weaponDamage = 0;

        /**
         * Held item checks.
         */
        if(((Player) e.getDamager()).getInventory().getItemInMainHand().getType() != Material.AIR) {
            if (((Player) e.getDamager()).getInventory().getItemInMainHand().hasItemMeta()) {
                NBTJItem item = new NBTJItem(((Player) e.getDamager()).getInventory().getItemInMainHand());
                if (item.hasJuno() && item.hasStats()) {
                    if (item.isWeapon() && item.getWeaponType() != null)
                        weaponDamage = item.getDamage();
                }
            }
        }

        /**
         * Damage calculation
         */
        double totalDamage = (player.getDamage()+weaponDamage)+(player.getStrength()*0.25);
        if(random.nextInt(100)+1 <= player.getCritChance()) totalDamage*=1+((double)player.getCritDamage()/100);
        /**
         * Applying damage
         */
        int finalHealth = oldHealth-(int)totalDamage;
        boolean dying = finalHealth <= 0;
        if(dying) {
            finalHealth = 0;
            if(getNPC().getEntity() instanceof LivingEntity) ((LivingEntity)getNPC().getEntity()).setHealth(0);
        }
        health = finalHealth;
        updateName();
        hologramsManager.createDamageHologram(e.getNPC().getStoredLocation(), (int)totalDamage);
        if(dying){
            hologramsManager.createXPHologram(e.getNPC().getStoredLocation(), xp, SkillID.Combat, (Player)e.getDamager());
            /*for(SpawnZone zone : entityManager.getSpawnZones()){
                zone.despawn(entityManager, e.getEntity().getUniqueId().toString());
            } */
        }
    }

    @EventHandler
    public void onDeath(NPCDeathEvent e){
        if(e.getNPC() != this.getNPC())return;
        if(!lootTable.isWeighted()){
            LootContainer container = (LootContainer) lootTable.getLootContainer();
            for(Map.Entry<Loot, Float> map : container.getLoot().entrySet()){
                float chance = random.nextFloat(100);
                if(chance <= map.getValue()){
                    int amount = random.nextInt(map.getKey().getMinAmount(), map.getKey().getMaxAmount()+1);
                    if(map.getKey().getMinAmount() == map.getKey().getMaxAmount()) amount = map.getKey().getMaxAmount();
                    getNPC().getEntity().getWorld().dropItemNaturally(getNPC().getStoredLocation(), ItemBuilder.buildItem(map.getKey().getItem(), amount));
                }
            }
        }
    }

    @EventHandler
    public void onFire(NPCCombustEvent e){
        if(e.getNPC() != this.getNPC()) return;
        e.setCancelled(true);
    }

    @Override
    public void run() {
        if(!npc.isSpawned()) return;
        if(!agro) {
            for (Entity entity : getNPC().getEntity().getWorld().getNearbyPlayers(getNPC().getStoredLocation(), 35)) {
                if(!entity.hasMetadata("NPC")) {
                    target = entity;
                    break;
                }
            }
        }
        if(target != null){
            getNPC().getNavigator().setTarget(target, true);
            agro = true;
        }
    }

    @Override
    public void onSpawn() {
        updateName();
    }


    private void updateName(){
        getNPC().setName(ChatColor.translateAlternateColorCodes('&',
                "&7[" + level + "] &c" + name + " &" +
                        getHealthColor(health, maxHealth) + health + "&7/&a" + maxHealth + "&c" + PlayerStatID.Health.getSymbol()));
    }

    private char getHealthColor(double health, double maxHealth){
        double percentage = (health/maxHealth)*100;
        if(percentage >= 70) return 'a';
        if(percentage >= 20) return 'e';
        return '4';
    }

}
