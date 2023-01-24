package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.loot.Loot;
import io.github.nmahdi.JunoCore.loot.ChanceLootTable;
import io.github.nmahdi.JunoCore.loot.WeightLootTable;
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

    private Random random;
    private final int inputSlot = 20;
    private final int outputSlot = 24;
    ItemStack accept = new ItemStackBuilder(Material.BLAZE_POWDER).setName("Confirm", NamedTextColor.RED, false).build();

    public DismantleGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main,"Dismantle", 54, blacksmithGUI, blacksmithGUI.getName(), blacksmithGUI.getSkullLore());
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

                    GameItem item = GameItem.getItem(new NBTGameItem(e.getInventory().getItem(inputSlot)).getID());
                    if(item == null) return;
                    if (item.isDismantlable()) {

                        if (item.getLootTable().isWeighted()) {

                            WeightLootTable container = (WeightLootTable) item.getLootTable().getTable();
                            int c = random.nextInt(container.getTotalWeight());

                            for (int i = 0; i < container.getLoot().size(); i++) {

                                int amount = random.nextInt(container.getLoot().get(i).getLoot().getMinAmount(), container.getLoot().get(i).getLoot().getMaxAmount() + 1);

                                if (i > 0) {
                                    if (c < container.getLoot().get(i).getWeightIndex() && c >= container.getLoot().get(i - 1).getWeightIndex()) {
                                        output(e.getInventory(), container.getLoot().get(i).getLoot().getItem(), amount);
                                    }
                                } else {
                                    if (c < container.getLoot().get(i).getWeightIndex()) {
                                        output(e.getInventory(), container.getLoot().get(i).getLoot().getItem(), amount);
                                    }
                                }

                            }

                        }else{
                            ChanceLootTable container = (ChanceLootTable) item.getLootTable().getTable();

                            for(Map.Entry<Loot, Float> map : container.getLoot().entrySet()){
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
