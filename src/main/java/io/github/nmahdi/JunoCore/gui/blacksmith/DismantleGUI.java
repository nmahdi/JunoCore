package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.loot.Loot;
import io.github.nmahdi.JunoCore.loot.LootContainer;
import io.github.nmahdi.JunoCore.loot.WeightedLootContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
    ItemStack accept = new ItemStackBuilder(Material.BLAZE_POWDER).setName("&cConfirm").build();

    public DismantleGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main,"&cDismantle", 54, blacksmithGUI, blacksmithGUI.getName(), blacksmithGUI.getSkullLore());
        this.random = main.getRandom();
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getView().getTitle().equals(getName())) {

            if(openPrevious(e.getWhoClicked(), e.getCurrentItem())){
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().isSimilar(accept)) {

                if (e.getInventory().getItem(inputSlot) != null && !e.getInventory().getItem(inputSlot).getType().isAir()) {
                    if(e.getInventory().getItem(outputSlot) != null && !e.getInventory().getItem(outputSlot).getType().isAir()){
                        e.setCancelled(true);
                        return;
                    }

                    NBTJItem item = new NBTJItem(e.getInventory().getItem(inputSlot));
                    if (item.canDismantle()) {

                        JItem jItem = JItem.getItemByID(item.getID());

                        if (jItem != null && jItem.getStatContainer().hasLootTable()) {

                            if (jItem.getStatContainer().getLootTable().isWeighted()) {

                                WeightedLootContainer container = (WeightedLootContainer) jItem.getStatContainer().getLootTable().getLootContainer();
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
                                LootContainer container = (LootContainer) jItem.getStatContainer().getLootTable().getLootContainer();

                                for(Map.Entry<Loot, Float> map : container.getLoot().entrySet()){
                                    float chance = random.nextFloat(100);
                                    if(chance <= map.getValue()){
                                        int amount = random.nextInt(map.getKey().getMinAmount(), map.getKey().getMaxAmount()+ 1);
                                        output(e.getInventory(), map.getKey().getItem(), amount);
                                    }
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
    }

    private void output(Inventory inventory, JItem item, int amount){
        inventory.setItem(outputSlot, ItemBuilder.buildItem(item, amount));
        if (inventory.getItem(inputSlot).getAmount() == 1) {
            inventory.setItem(inputSlot, null);
        } else {
            inventory.setItem(inputSlot, new ItemStackBuilder(inventory.getItem(inputSlot))
                    .setAmount(inventory.getItem(inputSlot).getAmount() - 1).build());
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        inventory.setItem(31, accept);

        addNpcSkull(inventory);
        insertBack(inventory);
        insertFiller(inventory, inputSlot, outputSlot);

        player.openInventory(inventory);
    }

}
