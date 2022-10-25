package io.github.nmahdi.JunoCore.events;


import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.stats.EquipmentSlotID;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class JEquipmentEquipEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    private final EquipMethod equipMethod;
    private EquipmentSlotID slot;
    private NBTItem oldItem, newItem;

    /**
     *
     * @param player The player who equipped/unequipped the armor
     * @param equipMethod The method a player used to equip the armor
     * @param slot The EquipmentSlotID the armor goes in
     * @param oldItem NBTItem of the armor unequipped
     * @param newItem NBTItem of the armor equipped
     */
    public JEquipmentEquipEvent(Player player, EquipMethod equipMethod, EquipmentSlotID slot, NBTItem oldItem, NBTItem newItem){
        super(player);
        this.equipMethod = equipMethod;
        this.slot = slot;
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    public JEquipmentEquipEvent(Player player, EquipMethod equipMethod, EquipmentSlotID slot, ItemStack oldItem, ItemStack newItem){
        super(player);
        this.equipMethod = equipMethod;
        this.slot = slot;
        if(oldItem != null && !oldItem.getType().isAir()) this.oldItem = new NBTItem(oldItem);
        if(newItem != null && !newItem.getType().isAir()) this.newItem = new NBTItem(newItem);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = true;
    }

    /**
     *
     * @return The equip method
     */
    public EquipMethod getEquipMethod() {
        return equipMethod;
    }

    /**
     *
     * @return The Equipment Slot ID
     */
    public EquipmentSlotID getSlot() {
        return slot;
    }

    /**
     *
     * @return NBTItem for the unequipped armor piece. Could be an armor piece, or null
     */
    public NBTItem getOldItem() {
        return oldItem;
    }

    public void setOldItem(NBTItem oldItem) {
        this.oldItem = oldItem;
    }

    /**
     *
     * @return NBTItem for the Equipped armor piece. Could be an armor piece, or null
     */
    public NBTItem getNewItem() {
        return newItem;
    }

    public void setNewItem(NBTItem newItem) {
        this.newItem = newItem;
    }

    public enum EquipMethod{// These have got to be the worst documentations ever.
        /**
         * When you shift click an armor piece to equip or unequip
         */
        SHIFT_CLICK,
        /**
         * When you drag and drop the item to equip or unequip
         */
        DRAG,
        /**
         * When you manually equip or unequip the item. Use to be DRAG
         */
        PICK_DROP,
        /**
         * When you right click an armor piece in the hotbar without the inventory open to equip.
         */
        HOTBAR,
        /**
         * When you press the hotbar slot number while hovering over the armor slot to equip or unequip
         */
        HOTBAR_SWAP
        ;
    }

}
