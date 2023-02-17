package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Dismantlable;
import io.github.nmahdi.JunoCore.loot.items.ChanceItemLootTable;
import io.github.nmahdi.JunoCore.loot.items.ItemDrop;
import io.github.nmahdi.JunoCore.loot.items.WeightedItemLootTable;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class DismantleGUI extends NPCGUI {

    private ItemManager itemManager;
    private Random random;
    private final int inputSlot = 20;
    private final int outputSlot = 24;
    ItemStack accept = new ItemStackBuilder(Material.BLAZE_POWDER).setName("Confirm", NamedTextColor.RED, false).build();

    public DismantleGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main,"Dismantle", 54, blacksmithGUI, blacksmithGUI.getName(), blacksmithGUI.getSkullLore());
        this.itemManager = main.getItemManager();
        this.random = main.getRandom();
    }


    @Override
    public void onInvClick(InventoryClickEvent e) {
            if (e.getCurrentItem().isSimilar(accept)) {

                if (e.getInventory().getItem(inputSlot) != null && !e.getInventory().getItem(inputSlot).getType().isAir()) {
                    if(e.getInventory().getItem(outputSlot) != null && !e.getInventory().getItem(outputSlot).getType().isAir()){
                        e.setCancelled(true);
                        return;
                    }

                    GameItem item = itemManager.getItem(new NBTGameItem(e.getInventory().getItem(inputSlot)).getID());
                    if(item == null) return;
                    if (item instanceof Dismantlable) {

                        if (((Dismantlable) item).getLootTable().isWeighted()) {

                            WeightedItemLootTable lootTable = (WeightedItemLootTable) ((Dismantlable) item).getLootTable();
                            int c = random.nextInt(lootTable.getTotalWeight());

                            for (int i = 0; i < lootTable.getDrops().size(); i++) {

                                int amount = random.nextInt(lootTable.getDrops().get(i).getMinAmount(), lootTable.getDrops().get(i).getMaxAmount() + 1);

                                if (i > 0) {
                                    if (c < lootTable.getDrops().get(i).getWeightIndex() && c >= lootTable.getDrops().get(i - 1).getWeightIndex()) {
                                        output(e.getInventory(), lootTable.getDrops().get(i).getItem(), amount);
                                    }
                                } else {
                                    if (c < lootTable.getDrops().get(i).getWeightIndex()) {
                                        output(e.getInventory(), lootTable.getDrops().get(i).getItem(), amount);
                                    }
                                }

                            }

                        }else{
                            ChanceItemLootTable lootTable = (ChanceItemLootTable) ((Dismantlable) item).getLootTable();

                            for(Map.Entry<ItemDrop, Float> map : lootTable.getDrops().entrySet()){
                                float chance = random.nextFloat(100);
                                if(chance <= map.getValue()){
                                    int amount = random.nextInt(map.getKey().getMinAmount(), map.getKey().getMaxAmount()+ 1);
                                    output(e.getInventory(), map.getKey().getItem(), amount);
                                }
                            }
                        }
                        e.getWhoClicked().getWorld().playSound(e.getWhoClicked(), Sound.BLOCK_ANVIL_USE, 1f, 1f);
                    }
                }

            }

            if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
                if (e.getSlot() != inputSlot && e.getSlot() != outputSlot) {
                    e.setCancelled(true);
                    return;
                }
            }
    }

    private void output(Inventory inventory, GameItem item, int amount){
        inventory.setItem(outputSlot, ItemBuilder.buildGameItem(item, amount));
        if (inventory.getItem(inputSlot).getAmount() == 1) {
            inventory.setItem(inputSlot, null);
        } else {
            inventory.setItem(inputSlot, new ItemStackBuilder(inventory.getItem(inputSlot))
                    .setAmount(inventory.getItem(inputSlot).getAmount() - 1).build());
        }
    }

    @Override
    public void setItems(Inventory inventory, Player player) {
        inventory.setItem(31, accept);

        addNpcSkull(inventory);
        insertBack(inventory);
        insertFiller(inventory, inputSlot, outputSlot);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

}
