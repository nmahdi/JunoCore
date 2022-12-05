package io.github.nmahdi.JunoCore.item.ability;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemContainer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public abstract class ItemAbility implements Listener {

	protected JCore main;
	protected PlayerManager playerManager;
	protected GameItem item;
	protected int manaCost;
	protected int coolDownTicks;
	private ArrayList<Component> description = new ArrayList<>();
	private ArrayList<Player> cooldowns = new ArrayList<>();

	public ItemAbility(JCore main, GameItem item, int manaCost, int coolDownTicks){
		this.item = item;
		this.manaCost = manaCost;
		this.coolDownTicks = coolDownTicks;
		this.main = main;
		this.playerManager = main.getPlayerManager();
		main.getServer().getPluginManager().registerEvents(this, main);
		description.add(addAbilityDescrption());
		if(manaCost > 0) description.add(Component.text("Mana Cost: " ).color(TextColor.color(TextColors.GRAY)).append(Component.text(manaCost).color(TextColors.NEGATIVE)));
		if(coolDownTicks > 0) description.add(Component.text("Cooldown Time: " + (double)coolDownTicks/20 + "s").color(TextColor.color(TextColors.GRAY)));
	}

	public abstract Component addAbilityDescrption();

	public int getManaCost() {
		return manaCost;
	}

	public int getCoolDownTicks() {
		return coolDownTicks;
	}

	public boolean isOnCooldown(Player player){
		return cooldowns.contains(player);
	}

	/**
	 * Adds the player to a list and schedules a synced delayed task to remove the player after {@link #getCoolDownTicks()} amount of ticks.
	 */
	public void setOnCooldown(Player player){
		if(coolDownTicks <= 0) return;
		cooldowns.add(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				cooldowns.remove(player);
			}
		}, coolDownTicks);
	}

	public ArrayList<Component> getAbilityDescription(){
		return description;
	}
}
