package io.github.nmahdi.JunoCore.player.listeners.fishing;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.loot.fishing.FishingLoot;
import io.github.nmahdi.JunoCore.loot.fishing.FishingLootEntity;
import io.github.nmahdi.JunoCore.loot.fishing.FishingLootItem;
import io.github.nmahdi.JunoCore.loot.fishing.FishingLootTable;
import io.github.nmahdi.JunoCore.loot.LootTable;
import io.github.nmahdi.JunoCore.loot.WeightLootTable;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.world.entity.item.EntityItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class FishingListener implements Listener {

	private JCore main;
	private PlayerManager playerManager;
	private GameEntityManager entityManager;
	private Random random;

	private FishingLootTable normal = new FishingLootTable().addItem(GameItem.Cod, 1, 100)
			.addItem(GameItem.Pufferfish, 1, 50)
			.addItem(GameItem.Sponge, 1, 25)
			.addEntity(GameEntity.Drowned, 500);

	private FishingLootTable mining = new FishingLootTable().addItem(GameItem.Coal, 1, 2, 100)
			.addItem(GameItem.IronBar, 1, 2, 50)
			.addItem(GameItem.LowQualityGeode, 1, 20);

	public FishingListener(JCore main){
		this.main = main;
		this.playerManager = main.getPlayerManager();
		this.entityManager = main.getEntityManager();
		this.random = main.getRandom();
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onFish(PlayerFishEvent e){
		if(e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

		e.setCancelled(true);
		e.getHook().setMinWaitTime(20);
		e.getHook().setMaxWaitTime(50);


		GamePlayer player = playerManager.getPlayer(e.getPlayer());

		FishingLootTable table = new FishingLootTable();
		table.merge(normal);

		if(player.hasBoots() && player.getBoots() == GameItem.FishermanBoots){
			table.merge(mining);
		}

		int roll = random.nextInt(table.getTotalWeight())+1;

		for (int i = 0; i < table.getLoot().size(); i++) {

			if (i > 0) {
				if (roll < table.getLoot().get(i).getWeightIndex() && roll >= table.getLoot().get(i - 1).getWeightIndex()) {
					setHooked(e, table.getLoot().get(i));
				}
			} else {
				if (roll < table.getLoot().get(i).getWeightIndex()) {
					setHooked(e, table.getLoot().get(i));
				}
			}

		}

	}

	private void setHooked(PlayerFishEvent e, FishingLoot loot){
		if(loot instanceof FishingLootItem){
			Item item = (Item) e.getHook().getWorld().spawnEntity(e.getHook().getLocation(), EntityType.DROPPED_ITEM);
			item.setItemStack(ItemBuilder.buildGameItem(((FishingLootItem) loot).getGameItem(), ((FishingLootItem) loot).getMinAmount()));
			e.getHook().setHookedEntity(item);
		}

		if(loot instanceof FishingLootEntity){
			NPC npc = entityManager.spawnEntity(((FishingLootEntity) loot).getGameEntity(), e.getHook().getLocation());
			npc.getEntity().setVelocity(npc.getEntity().getVelocity().add(new Vector(0, 1, 0)));
			e.getHook().setHookedEntity(npc.getEntity());
		}

		e.getHook().pullHookedEntity();
		e.getHook().remove();

	}

}
