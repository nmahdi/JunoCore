package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.utils.JLogger;
import net.citizensnpcs.api.event.NPCCombustByBlockEvent;
import net.citizensnpcs.api.event.NPCCombustByEntityEvent;
import net.citizensnpcs.api.event.NPCCombustEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;

@TraitName("sunburn")
public class SunburnTrait extends GameTrait{

	public SunburnTrait() {
		super("sunburn");
	}

	@Override
	public void init(GameEntity entity) {

	}

	@EventHandler
	public void onBurn(NPCCombustEvent e) {
		if(this.getNPC() != e.getNPC()) return;

		if (e instanceof NPCCombustByBlockEvent) return;
		if (e instanceof NPCCombustByEntityEvent) return;
		e.setDuration(0);
		e.setCancelled(true);
	}

}
