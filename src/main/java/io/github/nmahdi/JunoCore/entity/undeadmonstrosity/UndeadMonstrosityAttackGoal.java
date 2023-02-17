package io.github.nmahdi.JunoCore.entity.undeadmonstrosity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameGoal;
import io.github.nmahdi.JunoCore.entity.traits.AttackTrait;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.LocationHelper;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UndeadMonstrosityAttackGoal extends GameGoal {

	private Random random = ThreadLocalRandom.current();

	private long lastCircle;
	private Entity target;
	private long timeStarted = 0;
	private int timeCircling = 0;
	private int ticks = 0;

	public UndeadMonstrosityAttackGoal(JCore main, NPC npc) {
		super(main, npc);
		this.lastCircle = System.currentTimeMillis();
	}

	@Override
	public void reset() {
		this.target = null;
		this.timeStarted = 0;
		this.timeCircling = 0;
		this.ticks = 0;
	}

	@Override
	public BehaviorStatus run() {
		if(target == null) return BehaviorStatus.FAILURE;

		if(timeStarted == 0) timeStarted = System.currentTimeMillis();
		if(timeCircling == 0) timeCircling = random.nextInt(2000, 10000);

		if(System.currentTimeMillis() > timeStarted+timeCircling){
			JLogger.log("Goal Finished");
			return BehaviorStatus.SUCCESS;
		}

		ticks++;
		Location location = LocationHelper.circleAround(target.getLocation(), 0.8f, ticks * (1.5/20));
		npc.setMoveDestination(location);


		return BehaviorStatus.RUNNING;
	}

	@Override
	public boolean shouldExecute() {
		if(target == null && npc.getNavigator().getEntityTarget() != null){
			target = npc.getNavigator().getEntityTarget().getTarget();
			JLogger.log("Target Stopped");
		}else{
			return false;
		}
		return System.currentTimeMillis() > lastCircle;
	}
}
