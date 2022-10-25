package io.github.nmahdi.JunoCore.player.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.JHologramsManager;
import io.github.nmahdi.JunoCore.entity.EntityStatID;
import io.github.nmahdi.JunoCore.entity.JEntityManager;
import io.github.nmahdi.JunoCore.entity.SpawnZone;
import io.github.nmahdi.JunoCore.events.JEntityDeathEvent;
import io.github.nmahdi.JunoCore.item.JItemManager;
import io.github.nmahdi.JunoCore.item.builder.NBTItemStackBuilder;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.item.stats.WeaponType;
import io.github.nmahdi.JunoCore.loot.Loot;
import io.github.nmahdi.JunoCore.loot.LootTable;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import io.github.nmahdi.JunoCore.player.skills.SkillID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class JEntityListener implements Listener {

    private Random random;
    private JPlayerManager playerManager;
    private JEntityManager entityManager;
    private JHologramsManager hologramsManager;
    private JItemManager itemManager;

    public JEntityListener(Random random, JPlayerManager playerManager, JEntityManager entityManager, JHologramsManager hologramsManager, JItemManager itemManager){
        this.random = random;
        this.playerManager = playerManager;
        this.entityManager = entityManager;
        this.hologramsManager = hologramsManager;
        this.itemManager = itemManager;
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

    /**
     * Event handling custom Combat
     */

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player) return;
        /**
         * Checks if the entity is from Juno Core. If not, cancels event.
         */

        NBTEntity entity = new NBTEntity(e.getEntity());
        if(!entity.getPersistentDataContainer().hasKey("juno")){
            e.setCancelled(true);
            return;
        }
        e.setDamage(0);
        if(!(e.getDamager() instanceof Player)) return;
        NBTEntity player = new NBTEntity(e.getDamager());

        NBTCompound eJuno = entity.getPersistentDataContainer().getCompound("juno");
        int oldHealth = eJuno.getInteger(EntityStatID.Health.getId());

        NBTCompound pJuno = player.getPersistentDataContainer().getCompound("juno");
        NBTCompound pSkills = pJuno.getCompound("skills");
        NBTCompound pStats = pJuno.getCompound("stats");

        int weaponDamage = 0;

        /**
         * Held item checks.
         */
        if(((Player) e.getDamager()).getInventory().getItemInMainHand().getType() != Material.AIR) {
            if (((Player) e.getDamager()).getInventory().getItemInMainHand().hasItemMeta()) {
                NBTItem item = new NBTItem(((Player) e.getDamager()).getInventory().getItemInMainHand());
                if (item.hasKey("juno") && item.getCompound("juno").hasKey("stats")) {
                    NBTCompound stats = item.getCompound("juno").getCompound("stats");
                    NBTCompound hStats = item.getCompound("juno").getCompound("hidden-stats");
                    if (hStats.hasKey(ItemStatID.WeaponType.getID()) && hStats.getString(ItemStatID.WeaponType.getID()).equals(WeaponType.Sword.getId()))
                        weaponDamage = Integer.parseInt(stats.getString(ItemStatID.Damage.getID()));
                }
            }
        }

        /**
         * Damage calculation
         */
        double totalDamage = (pStats.getDouble(PlayerStatID.Damage.getId())+weaponDamage)+(pStats.getInteger(PlayerStatID.Strength.getId())*0.25);
        if(random.nextInt(100)+1 <= pStats.getInteger(PlayerStatID.CritChance.getId())) totalDamage*=1+((double)pStats.getInteger(PlayerStatID.CritDamage.getId())/100);
        /**
         * Applying damage
         */
        int finalHealth = oldHealth-(int)totalDamage;
        boolean dying = finalHealth <= 0;
        if(dying) finalHealth = 0;
        eJuno.setInteger(EntityStatID.Health.getId(), finalHealth);
        entityManager.setName(entity);
        hologramsManager.createDamageHologram(e.getEntity().getLocation(), (int)totalDamage);
        if(dying){
            hologramsManager.createXPHologram(e.getEntity().getLocation(), entity.getPersistentDataContainer().getCompound("juno").getInteger(EntityStatID.XP.getId()), SkillID.Combat, (Player)e.getDamager());
            Bukkit.getServer().getPluginManager().callEvent(new JEntityDeathEvent(e.getEntity().getWorld(), e.getEntity().getLocation(), (Player)e.getDamager(), entityManager.getEntityByID(eJuno.getString(EntityStatID.ID.getId()))));
            for(SpawnZone zone : entityManager.getSpawnZones()){
                zone.despawn(entityManager, e.getEntity().getUniqueId().toString());
            }
            if(e.getEntity() instanceof Damageable) ((Damageable)e.getEntity()).damage(((Damageable) e.getEntity()).getHealth());
        }
    }

    /**
     * Event handling Juno mobs dying
     */

    @EventHandler
    public void onJEntityDeath(JEntityDeathEvent e){
        LootTable lootTable = e.getJEntity().getLootTable();
        for(Loot loot : lootTable.getJLoot()){
            int chance = random.nextInt(100);
            if(chance <= loot.getChance()){
                int amount = random.nextInt(loot.getMaxAmount()-loot.getMinAmount()+1)+loot.getMinAmount();
                if(loot.getMinAmount() == loot.getMaxAmount()) amount = loot.getMinAmount();
                e.getWorld().dropItemNaturally(e.getDeathLocation(),
                        new NBTItemStackBuilder(loot.getItem(), amount).getStack());
            }
        }
    }

}
