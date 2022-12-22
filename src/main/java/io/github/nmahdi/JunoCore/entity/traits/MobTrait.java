package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.player.stats.DamageType;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.LocationHelper;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;


@TraitName("mob")
public class MobTrait extends GameTrait {

	private JCore main;
	private StatsTrait stats;

	private double damage;
	private double attackRange;
	private int attackDelay;
	private double distanceMargin;

	private Player target;

	private int attackTicks = 0;

	private int wanderingTicks = 0;
	private boolean wandering = true;

	public MobTrait() {
		super("mob");
		main = JavaPlugin.getPlugin(JCore.class);
	}

	@Override
	public void init(GameEntity entity){
		this.damage = entity.getDamagePerHit();
		this.attackDelay = entity.getAttackDelay();
		this.attackRange = entity.getAttackRange();
		this.distanceMargin = entity.getDistanceMargin();
	}

	@Override
	public void onAttach() {
		stats = getNPC().getTraitNullable(StatsTrait.class);
		this.damage = this.damage + (stats.getLevel() - 1) * 2;
		getNPC().getNavigator().getLocalParameters().distanceMargin(distanceMargin);
	}

	@Override
	public void run() {
		if(!npc.isSpawned()) return;

		if(target == null){
			HashMap<Player, Double> players = new HashMap<>();
			Player temp = null;

			for(Player player : getWorld().getNearbyPlayers(getLocation(), 4)){
				if(!player.hasMetadata("NPC")) {
					if(temp == null) temp = player;
					players.put(player, player.getLocation().distance(getLocation()));
				}
			}

			if(!players.isEmpty()) {
				for (Map.Entry<Player, Double> entries : players.entrySet()) {
					if (entries.getValue() < players.get(temp)) temp = entries.getKey();
				}
			}

			if(temp != null) {
				this.target = temp;
				this.wandering = false;
				getNPC().getNavigator().setTarget(target, false);
			}

		}else {
			attackTicks++;
			double distanceFromTarget = getLocation().distance(target.getLocation());

			getEntity().lookAt(target);


			if(distanceFromTarget <= attackRange) {
				if (attackTicks >= attackDelay) {
					attackTicks = 0;
					main.getPlayerManager().getPlayer(target).damage(getNPC(), 1.5d, (int) damage, DamageType.Physical, null);
				}
			}


			if (distanceFromTarget > distanceMargin+5) {
				target = null;
				wandering = true;
			}
		}

	}

	public double getDamage() {
		return damage;
	}

	public double getAttackRange() {
		return attackRange;
	}

	public int getAttackDelay() {
		return attackDelay;
	}

	public double getDistanceMargin() {
		return distanceMargin;
	}

	public Player getTarget() {
		return target;
	}
}
