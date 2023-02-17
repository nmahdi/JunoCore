package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.loot.items.LootTable;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@TraitName("stats")
public class StatsTrait extends GameTrait {

	private JCore main;
	private HologramManager hologramManager;
	private PlayerManager playerManager;

	private String displayName;
	private int level;

	private double maxHealth;
	private double currentHealth;

	private float speedModifier;

	private int combatXP;

	private LootTable lootTable;

	public StatsTrait() {
		super("stats_trait");
	}

	@Override
	public void init(GameEntity entity){
		this.main = JavaPlugin.getPlugin(JCore.class);
		this.hologramManager = main.getHologramsManager();
		this.playerManager = main.getPlayerManager();
		this.displayName = entity.getDisplayName();
		this.level = main.getRandom().nextInt(entity.getMinLevel(), entity.getMaxLevel()+1);
		this.maxHealth = entity.getBaseHealth() + (level - 1) * 20;
		this.currentHealth = this.maxHealth;
		this.speedModifier = entity.getSpeedModifier();
		this.combatXP = entity.getCombatXP() + (level - 1) * 2;
	}

	@Override
	public void onAttach() {
		updateName();
		getNPC().getNavigator().getLocalParameters().speedModifier(speedModifier);
	}

	public void damage(Player player, int damage){
		hologramManager.createDamageHologram(getLocation(), damage);
		currentHealth-=damage;
		updateName();
		if(currentHealth <= 0) {
			if (player != null) {
				playerManager.getPlayer(player).gainXP(Skill.Combat, combatXP);

				/*if(lootTable.getTable() instanceof ChanceLootTable) {
					ChanceLootTable table = (ChanceLootTable) lootTable.getTable();
					for (Map.Entry<GameItem, Integer> items : table.rollForItems(main.getRandom(), false).entrySet()){
						HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(ItemBuilder.buildGameItem(items.getKey(), items.getValue()));
						for(int i = 0; i < leftover.size(); i++){
							getEntity().getWorld().dropItemNaturally(getEntity().getLocation(), leftover.get(i));
						}
					}
				} */

			}

			kill();
		}
	}

	public void kill(){
		currentHealth = 0;
		((LivingEntity)getNPC().getEntity()).setHealth(0);
	}

	@EventHandler
	public void onFallDamage(NPCDamageEvent e){
		if(e.getNPC() != this.getNPC()) return;
		if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
			damage(null, (int)e.getDamage());
			e.setDamage(0);
		}
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getLevel() {
		return level;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public double getHealth() {
		return currentHealth;
	}

	public void setHealth(int health){
		currentHealth = health;
	}

	public float getSpeedModifier() {
		return speedModifier;
	}

	public int getCombatXP() {
		return combatXP;
	}

	private void updateName(){
		getNPC().setName(ChatColor.translateAlternateColorCodes('&',
				"&7[" + level + "] &c" + displayName + " &" +
						getHealthColor(currentHealth, maxHealth) + Math.max(currentHealth, 0) + "&7/&a" + maxHealth + "&c" + PlayerStat.Health.getSymbol()));
	}

	private char getHealthColor(double health, double maxHealth){
		double percentage = (health/maxHealth)*100;
		if(percentage >= 70) return 'a';
		if(percentage >= 20) return 'e';
		return 'c';
	}

}
