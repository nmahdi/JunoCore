package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.effects.ParticleHelper;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@TraitName("lost_wizard")
public class LostWizardTrait extends GameTrait{

	private StatsTrait stats;

	private int chargingTicks = 0;
	private int attackingTicks = 0;
	private boolean attacking = false;
	private boolean attacked = false;

	public LostWizardTrait() {
		super("lost_wizard");
	}

	@Override
	public void init(GameEntity entity) {

	}

	@Override
	public void onAttach() {
		stats = getNPC().getTraitNullable(StatsTrait.class);
	}

	@Override
	public void run() {
		if(!npc.isSpawned()) return;


		/*if(getEntityTarget() != null){
			if(!attacking) chargingTicks++;

			if(chargingTicks == 50){
				if(attacking) getEntity().setVelocity(new Vector(0, getEntity().getVelocity().getY(), 0));

				attacking = true;
				attackingTicks++;

				if(!attacked){
					Fireball fireball = (Fireball) getWorld().spawnEntity(getEyeLocation().add(getEyeLocation().getDirection()), EntityType.FIREBALL);
					fireball.setDirection(getLocation().getDirection());
					attacked = true;
				}

				if(attackingTicks > 10){
					//getEntity().launchProjectile(Fireball.class, getLocation().getDirection());

					attacking = false;
					attacked = false;
					//getEntity().removePotionEffect(PotionEffectType.SLOW);
					attackingTicks = 0;
					chargingTicks = 0;
				}

			}

		}*/
	}

}
