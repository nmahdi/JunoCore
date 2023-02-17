package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.NPC;

public abstract class GameGoal extends BehaviorGoalAdapter {

	protected JCore main;
	protected NPC npc;
	protected StatsTrait stats;

	public GameGoal(JCore main, NPC npc){
		this.main = main;
		this.npc = npc;
		this.stats = npc.getOrAddTrait(StatsTrait.class);
	}

}
