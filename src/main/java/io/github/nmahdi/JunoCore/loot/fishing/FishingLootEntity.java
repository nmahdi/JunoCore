package io.github.nmahdi.JunoCore.loot.fishing;

import io.github.nmahdi.JunoCore.entity.GameEntity;

public class FishingLootEntity extends FishingLoot{

	private final GameEntity gameEntity;

	public FishingLootEntity(GameEntity gameEntity, int weight, int weightIndex) {
		super(weight, weightIndex);
		this.gameEntity = gameEntity;
	}

	public GameEntity getGameEntity() {
		return gameEntity;
	}

}
