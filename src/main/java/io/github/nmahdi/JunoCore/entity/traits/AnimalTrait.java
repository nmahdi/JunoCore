package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import net.citizensnpcs.api.trait.TraitName;

@TraitName("animal")
public class AnimalTrait extends GameTrait{

	public AnimalTrait() {
		super("animal");
	}

	@Override
	public void init(GameEntity entity) {
	}

	@Override
	public void onSpawn() {

	}

	@Override
	public void onAttach() {

	}

}
