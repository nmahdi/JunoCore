package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.NPC;

public class UndeadMonstrosityGoal extends BehaviorGoalAdapter {

	private NPC npc;
	private StatsTrait stats;

	public UndeadMonstrosityGoal(NPC npc){
		this.npc = npc;
		this.stats = npc.getTraitNullable(StatsTrait.class);
	}

	@Override
	public void reset() {

	}

	@Override
	public BehaviorStatus run() {
		return null;
	}

	@Override
	public boolean shouldExecute() {
		return false;
	}
}
