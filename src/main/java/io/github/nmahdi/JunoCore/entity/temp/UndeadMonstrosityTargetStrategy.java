package io.github.nmahdi.JunoCore.entity.temp;

import net.citizensnpcs.api.ai.*;
import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.BoundingBox;
import net.citizensnpcs.npc.ai.MCTargetStrategy;
import net.citizensnpcs.util.NMS;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class UndeadMonstrosityTargetStrategy implements PathStrategy, EntityTarget {

	private int attackTicks;
	private CancelReason cancelReason;
	private final Entity handle;
	private final NPC npc;
	private final NavigatorParameters parameters;
	private final Entity target;
	private MCTargetStrategy.TargetNavigator targetNavigator;
	private int updateCounter = -1;

	static final AttackStrategy DEFAULT_ATTACK_STRATEGY = new AttackStrategy() {
		public boolean handle(LivingEntity attacker, LivingEntity bukkitTarget) {
			NMS.attack(attacker, bukkitTarget);
			return false;
		}
	};
	private static final Location HANDLE_LOCATION = new Location((World)null, 0.0, 0.0, 0.0);
	private static final Location TARGET_LOCATION = new Location((World)null, 0.0, 0.0, 0.0);

	public UndeadMonstrosityTargetStrategy(NPC npc, Entity target, NavigatorParameters parameters){
		this.npc = npc;
		this.parameters = parameters;
		this.handle = npc.getEntity();
		this.target = target;
		MCTargetStrategy.TargetNavigator nms = NMS.getTargetNavigator(npc.getEntity(), target, parameters);
		this.targetNavigator = (MCTargetStrategy.TargetNavigator) nms;
	}

	private boolean canAttack(){
		BoundingBox handleBB = NMS.getBoundingBox(this.handle);
		BoundingBox targetBB = NMS.getBoundingBox(this.target);
		return this.attackTicks <= 0 && handleBB.maxY > targetBB.minY && handleBB.minY < targetBB.maxY && closeEnough(distance()) && hasLineOfSight();
	}

	private boolean closeEnough(double distance) {
		return distance <= this.parameters.attackRange();
	}

	private double distance() {
		return this.handle.getLocation(HANDLE_LOCATION).distance(this.target.getLocation(TARGET_LOCATION));
	}

	private boolean hasLineOfSight() {
		return ((LivingEntity)this.handle).hasLineOfSight(this.target);
	}

	@Override
	public void clearCancelReason() {
		this.cancelReason = null;
	}

	@Override
	public CancelReason getCancelReason() {
		return cancelReason;
	}

	@Override
	public Location getCurrentDestination() {
		return targetNavigator.getCurrentDestination();
	}

	@Override
	public Iterable<Vector> getPath() {
		return targetNavigator.getPath();
	}

	@Override
	public Entity getTarget() {
		return target;
	}

	@Override
	public Location getTargetAsLocation() {
		return target.getLocation();
	}

	@Override
	public boolean isAggressive() {
		return true;
	}

	@Override
	public TargetType getTargetType() {
		return TargetType.ENTITY;
	}

	@Override
	public void stop() {
		targetNavigator.stop();
	}

	@Override
	public boolean update() {
		if(target != null && target.isValid()){
			if(target.getWorld() != handle.getWorld()){
				cancelReason = CancelReason.TARGET_MOVED_WORLD;
				return true;
			}else if(cancelReason == null){
				return true;
			}else{

				/*if (this.parameters.straightLineTargetingDistance() > 0.0F && !(this.targetNavigator instanceof MCTargetStrategy.StraightLineTargeter)) {
					this.targetNavigator = new MCTargetStrategy.StraightLineTargeter(this.targetNavigator);
				} */

				if(distance() <= parameters.distanceMargin()){
					stop();
					return false;
				}else{

					if(updateCounter == -1 || updateCounter++ > parameters.updatePathRate()){
						targetNavigator.setPath();;
						updateCounter = 0;
					}

					targetNavigator.update();
					NMS.look(handle, target);
					if(canAttack()){
						AttackStrategy strategy = parameters.attackStrategy();

						if((strategy == null || !strategy.handle((LivingEntity) handle, (LivingEntity) getTarget())) && strategy != parameters.defaultAttackStrategy()){
							parameters.defaultAttackStrategy().handle((LivingEntity) handle, (LivingEntity) getTarget());
						}

						attackTicks = parameters.attackDelayTicks();

					}

					if(attackTicks > 0){
						attackTicks--;
					}
					return false;
				}

			}
		}else{
			cancelReason = CancelReason.TARGET_DIED;
			return true;
		}
	}
}
