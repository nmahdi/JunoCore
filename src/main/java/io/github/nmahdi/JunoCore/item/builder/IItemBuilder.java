package io.github.nmahdi.JunoCore.item.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public interface IItemBuilder {

    default IItemBuilder setAmount(int amount){
        getItemStack().setAmount(amount);
        return this;
    }
    default IItemBuilder setName(String name, TextColor color, boolean bold){
        getItemMeta().displayName(Component.text(name).color(color).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, bold));
        return this;
    }
    default IItemBuilder setName(String name, NamedTextColor color, boolean bold){
        getItemMeta().displayName(Component.text(name).color(color).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, bold));
        return this;
    }

    default IItemBuilder blankName(){
        getItemMeta().displayName(Component.empty());
        return this;
    }

    default IItemBuilder addLore(Component... components){
        for(Component component : components){
            getLore().add(component.decoration(TextDecoration.ITALIC, false));
        }
        return this;
    }

    default IItemBuilder addLore(ArrayList<Component> components){
        for(Component component : components){
            getLore().add(component.decoration(TextDecoration.ITALIC, false));
        }
        return this;
    }

    default IItemBuilder addLore(Component component){
        getLore().add(component.decoration(TextDecoration.ITALIC, false));
        return this;
    }

    default IItemBuilder addLore(String string, TextColor color, boolean bold){
        getLore().add(Component.text(string).color(color).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, bold));
        return this;
    }

    default IItemBuilder skipLore(){
        getLore().add(Component.empty());
        return this;
    }
    default IItemBuilder clearLore(){
        getLore().clear();
        return this;
    }
    default IItemBuilder addFlag(ItemFlag itemFlag){
        getItemMeta().addItemFlags(itemFlag);
        return this;
    }

    default IItemBuilder setCustomModelData(int data){
        getItemMeta().setCustomModelData(data);
        return this;
    }
    default ItemStack build(){
        for(ItemFlag itemFlag : ItemFlag.values()){
            getItemMeta().addItemFlags(itemFlag);
        }
        //getItemMeta().addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("attack_speed", 0, AttributeModifier.Operation.ADD_NUMBER));
        getItemMeta().lore(getLore());
        getItemMeta().setUnbreakable(true);
        getItemStack().setItemMeta(getItemMeta());
        return getItemStack();
    }

    ItemStack getItemStack();
    ItemMeta getItemMeta();
    ArrayList<Component> getLore();

}
