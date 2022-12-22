package io.github.nmahdi.JunoCore.item.ability.abilities;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.item.RightClickItemAbility;
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

import java.util.ArrayList;
import java.util.Random;

public class MagicWand extends RightClickItemAbility implements ParticleHelper {

	private Random random;
	private GameEntityManager entityManager;
	private final double range = 15;

	public MagicWand(JCore main) {
		super(main, "Flame Beam", GameItem.MagicWand, 10, 5);
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
		if(npc == null || !npc.hasTrait(StatsTrait.class)) return;
		npc.getOrAddTrait(StatsTrait.class).damage(p, player.getDamage(random));
	}

	@Override
	public ArrayList<Component> addAbilityDescrption() {
		ArrayList<Component> description = new ArrayList<>();
		description.add(Component.text("Right click to shoot a flame beam").color(TextColor.color(TextColors.GRAY_DESCRIPTION)));
		description.add(Component.text("dealing " + item.getDamage() + " magical damage.").color(TextColors.GRAY_DESCRIPTION));
		return description;
	}
}
