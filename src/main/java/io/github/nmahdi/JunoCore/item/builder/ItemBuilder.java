package io.github.nmahdi.JunoCore.item.builder;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.player.display.ComponentBuilder;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder{

    public static ItemStack buildGameItem(GameItem item){
        return buildGameItem(item, 1);
    }

    public static ItemStack buildGameItem(GameItem item, int amount){
        ItemStack stack = new ItemStackBuilder(item.getMaterial())
                .setAmount(amount).build();

        NBTGameItem gameItem = new NBTGameItem(stack);

        gameItem.init(item.getId());

        ItemStack finalItem = gameItem.getItem().clone();
        finalItem.setItemMeta(updateMeta(item, gameItem));
        return finalItem;
    }


    public static ItemMeta updateMeta(GameItem item, NBTGameItem gameItem){

        if(item.hasUUID() && !gameItem.hasUUID()) gameItem.setUUID(UUID.randomUUID().toString());
        if(item.canApplyRunes() && !gameItem.hasRunes()) gameItem.createRunes();

        ItemMeta meta = gameItem.getItem().getItemMeta();

        if(item.hasCustomModelData()) meta.setCustomModelData(item.getCusomModelData());
        meta.displayName(new ComponentBuilder().append(item.getDisplayName(), item.getRarity().getColor()).build());

        ArrayList<Component> description = new ArrayList<>();

        if(item.isRune()){
            //DO RUNE THINGS
        }

        if(!item.getStats().isEmpty()) {

            HashMap<PlayerStat, String> stats = item.getStats();
            for (PlayerStat stat : PlayerStat.values()) {
                int value = 0;

                Rune rune = Rune.getRune(stat);
                if (rune != null) {
                    if (gameItem.hasRunes() && gameItem.getRunes().containsKey(rune)) {
                        value += rune.getAmount() * gameItem.getRunes().get(rune);
                    }
                }

                if (item.hasStat(stat)) {
                    value+= Integer.parseInt(stats.get(stat));
                }
                if(value != 0) {
                    ComponentBuilder builder = new ComponentBuilder();
                    builder.append("<", TextColors.GRAY);
                    builder.append(stat.getSymbol(), stat.getColor());
                    builder.append("> " + stat.getDisplayName() + ": ", TextColors.GRAY);
                    builder.append((value > 0 ? "+" : "") + value, value > 0 ? TextColors.POSITIVE : TextColors.NEGATIVE);

                    description.add(builder.build());
                }
            }

            description.add(Component.empty());
        }
        if(item.canApplyRunes()){

            int index = description.size();
            int amount = 0;

            if(gameItem.hasRunes()){
                amount = gameItem.getRunesUsed();

                for(Map.Entry<Rune, Integer> runes : gameItem.getRunes().entrySet()) {
                    String value = "";
                    if(runes.getValue() > 1) value = " x" + runes.getValue();
                    description.add(new ComponentBuilder().append("- " + runes.getKey().toString() + " Rune" + value, TextColors.WHITE).build());
                }

                description.add(Component.empty());
            }

            description.add(index, new ComponentBuilder().append("Runes: (" + amount + "/" + item.getRuneSlots() + ")", TextColors.LIGHT_GRAY).build());

        }
        if(item.hasDescription()){
            description.addAll(item.getDescription());
            description.add(Component.empty());
        }
        description.add(buildRarity(item));
        meta.lore(description);
        return meta;
    }

    private static Component buildRarity(GameItem item){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(item.getRarity().toString(), item.getRarity().getColor(), true);
        if(item.getItemType() != ItemType.Misc){
            builder.append(" " + item.getItemType().toString(), item.getRarity().getColor(), true);
        }
        return builder.build();
    }


}
