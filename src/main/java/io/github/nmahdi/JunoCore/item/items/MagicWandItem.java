package io.github.nmahdi.JunoCore.item.items;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.effects.ParticleHelper;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.RightClickAbility;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public class MagicWandItem extends StatItem implements RightClickAbility, Runeable, ParticleHelper {

	private final double range = 15;

	public MagicWandItem(ItemManager itemManager) {
		super(itemManager, "magic_wand", "Magic Wand", Material.STICK, Rarity.Epic, ItemType.Wand);

		setDamage(100);
		setMana(100);

		addRightClickAbilityDescription(Component.text("Right click to shoot a flame beam").color(TextColors.GRAY_DESCRIPTION));
		addRightClickAbilityDescription(Component.text("dealing ").color(TextColors.GRAY_DESCRIPTION)
				.append(Component.text(getDamage()).color(TextColors.DAMAGE))
				.append(Component.text(" magical damage.").color(TextColors.GRAY_DESCRIPTION)));
	}

	@Override
	public void onRightClick(JCore main, GamePlayer player, NBTGameItem item) {
		Player p = player.getPlayerObject();
		RayTraceResult rayTrace = player.getWorld().rayTraceEntities(p.getEyeLocation(), p.getEyeLocation().getDirection(), range,
				entity -> entity.hasMetadata("NPC") && !entity.isDead());
		drawBeam(Particle.DRIP_LAVA, p.getEyeLocation(), p.getEyeLocation().getDirection(), range, 0.5);
		p.swingMainHand();
		if(rayTrace == null) return;
		if(rayTrace.getHitEntity() == null) return;
		NPC npc = main.getEntityManager().getMainRegistry().getByUniqueId(rayTrace.getHitEntity().getUniqueId());
		if(npc == null || !npc.hasTrait(StatsTrait.class)) return;
		npc.getOrAddTrait(StatsTrait.class).damage(p, player.getDamage(main.getRandom()));
	}

	@Override
	public String getRightClickAbilityName() {
		return "Magic Beam";
	}

	@Override
	public int getRightClickManaCost() {
		return 10;
	}

	@Override
	public int getMaxRunes() {
		return 5;
	}

}
