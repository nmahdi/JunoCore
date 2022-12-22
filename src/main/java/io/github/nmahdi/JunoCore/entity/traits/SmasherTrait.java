package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.effects.ParticleHelper;
import io.github.nmahdi.JunoCore.entity.interfaces.DoesKnockBack;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

@TraitName("smasher")
public class SmasherTrait extends Trait implements DoesKnockBack, ParticleHelper {

	private Entity target;

	private double attackRange;
	private final int radius = 5;
	private final int smashDamage = 10;

	private boolean leaping = false;
	private int leapCooldown = 0;
	private double yLevel;

	public SmasherTrait() {
		super("smasher");
	}

	@Override
	public void run() {
		if(!getNPC().isSpawned()) return;
		target = getNPC().getNavigator().getEntityTarget().getTarget();

		if(leaping){
			if(getNPC().getEntity().getVelocity().getY() < 0){
				getNPC().getEntity().setVelocity(getNPC().getEntity().getVelocity().setX(0).setZ(0).add(new Vector(0, -0.5, 0)));
				drawCircle(radius, getLocation(), yLevel);
			}
			if(getNPC().getEntity().isOnGround()) {
				leaping = false;
				for (Player player : getLocation().getWorld().getNearbyPlayers(getLocation(), radius)) {
					if (!player.hasMetadata("NPC")) {
						if(player.getLocation().getY() <= getLocation().getY()) {
							knockBack(player, getLocation(), 2.5);
							//
							//nPlayer.damage(smashDamage, DamageType.Physical, null);
						}
					}
				}
				getNPC().getNavigator().getLocalParameters().attackRange(attackRange);
				getLocation().getWorld().spawnParticle(Particle.DRIP_LAVA, getLocation(), 5);
				getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
			}
		}else{
			leapCooldown++;
		}

		if(leapCooldown >= 50) {
			if(getNPC().getNavigator().getEntityTarget() != null) {
				if (getNPC().getNavigator().getEntityTarget().getTarget().getLocation().distance(getLocation()) > 5) {
					getNPC().getNavigator().getLocalParameters().attackRange(0);
					yLevel = getLocation().getY();
					//TODO: Fix bug where it smahser stands still when a player isnt looking at him
					getNPC().getEntity().setVelocity(target.getLocation().getDirection().multiply(-(getLocation().distance(target.getLocation()) / 3.6)).setY(1));
					leaping = true;
					leapCooldown = 0;
				}
			}
		}
	}

	@Override
	public void onAttach() {
		this.attackRange = getNPC().getNavigator().getLocalParameters().attackRange();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFallDamage(NPCDamageEvent e){
		if(e.getNPC() != this.getNPC()) return;
		if(e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);
	}

	@EventHandler
	public void onHit(NPCDamageEntityEvent e){
		if(e.getNPC() != this.getNPC()) return;
		if(e.getDamaged() instanceof Player){
			knockBack(e.getDamaged(), getLocation(), 2.5);
			getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
		}
	}

	private Location getLocation(){
		return getNPC().getEntity().getLocation();
	}

}
