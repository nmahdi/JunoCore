package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.trait.SlimeSize;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

@TraitName("slime_split")
public class SlimeSplitTrait extends Trait {

    private SlimeSize slimeSize = new SlimeSize();
    private JCore main;
    private GameEntity entity;

    public SlimeSplitTrait(GameEntity entity, int size) {
        super("slime_split");
        this.main = JavaPlugin.getPlugin(JCore.class);
        this.entity = entity;
        slimeSize.setSize(size);
    }

    @Override
    public void onAttach() {
        getNPC().addTrait(slimeSize);
    }

    @EventHandler
    public void onDeath(NPCDeathEvent e){
        if(e.getNPC() != this.getNPC()) return;
        if(slimeSize.getSize() > 1) {
            int amount = slimeSize.getSize() == 2 ? 4 : 2;
            for(int i = 0; i < amount; i++) {
                NPC npc = getNPC().getOwningRegistry().createNPC(entity.getEntityType(), entity.getDisplayName());
                npc.addTrait(new JunoTrait(entity));
                npc.addTrait(new SlimeSplitTrait(entity, slimeSize.getSize()/2));
                npc.setProtected(false);
                npc.spawn(getNPC().getStoredLocation());
            }
        }
    }


}
