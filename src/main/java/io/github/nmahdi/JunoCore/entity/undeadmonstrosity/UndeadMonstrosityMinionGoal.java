package io.github.nmahdi.JunoCore.entity.undeadmonstrosity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.entity.GameGoal;
import io.github.nmahdi.JunoCore.entity.traits.AttackTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UndeadMonstrosityMinionGoal extends GameGoal {

	private Random random = ThreadLocalRandom.current();
	private GameEntityManager entityManager;

	private NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

	private int spawnTicks = 0;

	public UndeadMonstrosityMinionGoal(JCore main, NPC npc) {
		super(main, npc);
		this.entityManager = main.getEntityManager();
		AttackTrait trait = new AttackTrait();
		trait.init(GameEntity.UndeadMonstrosity);
		this.npc.addTrait(trait);
	}

	@Override
	public void reset() {
		registry.despawnNPCs(DespawnReason.DEATH);
	}

	@Override
	public BehaviorStatus run() {
		spawnTicks++;

		if(spawnTicks > 15) {
			int x = random.nextInt(-4, 4);
			int z = random.nextInt(-4, 4);

			NPC minion = entityManager.spawnEntityToRegistry(registry, GameEntity.Zombie, npc.getStoredLocation().add(x, 0, z));
		}

		if((stats.getHealth()/stats.getMaxHealth())*100 <= 25) return BehaviorStatus.RESET_AND_REMOVE;


		return BehaviorStatus.RUNNING;
	}

	@Override
	public boolean shouldExecute() {
		double healthPercentage = (stats.getHealth()/stats.getMaxHealth())*100;
		if(healthPercentage <= 25) return false;
		return healthPercentage <= 75;
	}

}
