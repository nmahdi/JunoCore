package io.github.nmahdi.JunoCore.player.listeners.fishing;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.loot.fishing.FishingDrop;
import io.github.nmahdi.JunoCore.loot.fishing.FishingEntityDrop;
import io.github.nmahdi.JunoCore.loot.fishing.FishingItemDrop;
import io.github.nmahdi.JunoCore.loot.fishing.FishingLootTable;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class FishingListener implements Listener {

	private JCore main;
	private ItemManager itemManager;
	private PlayerManager playerManager;
	private GameEntityManager entityManager;
	private Random random;

	private FishingLootTable normal;

	private FishingLootTable mining;

	public FishingListener(JCore main){
		this.main = main;
		this.itemManager = main.getItemManager();
		this.playerManager = main.getPlayerManager();
		this.entityManager = main.getEntityManager();
		this.random = main.getRandom();
		main.getServer().getPluginManager().registerEvents(this, main);

		normal = new FishingLootTable().addItem(itemManager.COD, 1, 100)
				.addEntity(GameEntity.Drowned, 500);

		mining = new FishingLootTable().addItem(itemManager.COAL, 1, 2, 100)
				.addItem(itemManager.IRON_BAR, 1, 2, 50);
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

		/*if(player.hasBoots() && player.getBoots() == itemManager.FishermanBoots){
			table.merge(mining);
		} */

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

	private void setHooked(PlayerFishEvent e, FishingDrop loot){
		if(loot instanceof FishingItemDrop){
			Item item = (Item) e.getHook().getWorld().spawnEntity(e.getHook().getLocation(), EntityType.DROPPED_ITEM);
			item.setItemStack(ItemBuilder.buildGameItem(((FishingItemDrop) loot).getGameItem(), ((FishingItemDrop) loot).getMinAmount()));
			e.getHook().setHookedEntity(item);
		}

		if(loot instanceof FishingEntityDrop){
			NPC npc = entityManager.spawnEntity(((FishingEntityDrop) loot).getGameEntity(), e.getHook().getLocation());
			npc.getEntity().setVelocity(npc.getEntity().getVelocity().add(new Vector(0, 1, 0)));
			e.getHook().setHookedEntity(npc.getEntity());
		}

		e.getHook().pullHookedEntity();
		e.getHook().remove();

	}

}
