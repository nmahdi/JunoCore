package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.entity.traits.JunoTrait;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.item.ability.RightClickItemAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.effects.ParticleHelper;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.Random;

public class MagicWandAbility extends RightClickItemAbility implements ParticleHelper {

	private Random random;
	private GameEntityManager entityManager;
	private final double range = 15;

	public MagicWandAbility(JCore main, GameItem item) {
		super(main, item, 10, 5);
		this.random = main.getRandom();
		this.entityManager = main.getEntityManager();
	}

	@Override
	public void activateAbility(GamePlayer player, NBTGameItem gameItem) {
		Player p = player.getPlayerObject();
		RayTraceResult rayTrace = player.getWorld().rayTraceEntities(p.getEyeLocation(), p.getEyeLocation().getDirection(), range,
				entity -> entity.hasMetadata("NPC") && !entity.isDead());
		drawBeam(Particle.DRIP_LAVA, p.getEyeLocation(), p.getEyeLocation().getDirection(), range, 0.5);
		p.swingMainHand();
		if(rayTrace == null) return;
		if(rayTrace.getHitEntity() == null) return;
		NPC npc = entityManager.getRegistry().getByUniqueId(rayTrace.getHitEntity().getUniqueId());
		if(npc == null || !npc.hasTrait(JunoTrait.class)) return;
		npc.getOrAddTrait(JunoTrait.class).damage(p, player.getDamage(random));
	}

	@Override
	public Component addAbilityDescrption() {
		return Component.text("Right click to shoot a magic beam dealing damage.").color(TextColor.color(TextColors.GRAY));
	}
}
