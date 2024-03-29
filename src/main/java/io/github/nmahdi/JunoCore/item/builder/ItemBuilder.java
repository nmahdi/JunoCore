package io.github.nmahdi.JunoCore.item.builder;

import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTShopItem;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.AttackItemAbility;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.BlockBreakAbility;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.RightClickAbility;
import io.github.nmahdi.JunoCore.item.modifiers.stats.RuneItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {

    public static ItemStack buildGameItem(GameItem item){
        return buildGameItem(item, 1);
    }

    public static ItemStack buildGameItem(GameItem item, int amount){
        ItemStack stack = new ItemStackBuilder(item.getMaterial()).setAmount(amount).build();

        NBTGameItem gameItem = new NBTGameItem(stack);

        gameItem.init(item.getId());

        ItemStack finalItem = gameItem.getItem().clone();
        finalItem.setItemMeta(updateMeta(null, item, gameItem));
        return finalItem;
    }

    public static ItemStack buildShopItem(GameItem item, int amount){
        ItemStack stack = new ItemStackBuilder(item.getMaterial()).setAmount(amount).build();

        NBTShopItem shopItem = new NBTShopItem(stack);

        shopItem.init(item.getId());
        shopItem.setInfo(amount);

        ItemStack finalItem = shopItem.getItem().clone();
        ItemMeta finalMeta = updateMeta(null, item, new NBTGameItem(finalItem));
        List<Component> lore = finalMeta.lore();

        DescriptionBuilder builder = new DescriptionBuilder(lore);
        builder.skipLine();
        builder.append("Purchase x"+amount, TextColors.GRAY_DESCRIPTION).endLine();
        builder.append("Price: ", TextColors.GRAY_DESCRIPTION).append(String.valueOf(item.getBuyPrice()*amount), NamedTextColor.GOLD).endLine();

        finalMeta.lore(builder.getList());
        finalItem.setItemMeta(finalMeta);
        return finalItem;
    }

    public static ItemMeta updateMeta(GamePlayer gamePlayer, GameItem item, NBTGameItem gameItem){

        boolean hasUUID = item.hasUUID();
        boolean isRunable = item instanceof Runeable;
        boolean isRune = item instanceof RuneItem;
        boolean hasStats = item instanceof StatItem;
        boolean rightClick = item instanceof RightClickAbility;
        boolean attack = item instanceof AttackItemAbility;
        boolean blockBreak = item instanceof BlockBreakAbility;

        if(hasUUID && !gameItem.hasUUID()) gameItem.setUUID(UUID.randomUUID().toString());
        if(isRunable && !gameItem.hasRunes()) gameItem.createRunes();

        ItemMeta meta = gameItem.getItem().getItemMeta();

        if(item.hasCustomModelData()) meta.setCustomModelData(item.getCustomModelData());
        meta.displayName(Component.text(item.getDisplayName()).color(item.getRarity().getColor()).decoration(TextDecoration.ITALIC, false));

        DescriptionBuilder builder = new DescriptionBuilder(false);

        if(isRune){
            //DO RUNE THINGS
        }

        if(hasStats) {

            StatItem statItem = (StatItem) item;
            HashMap<PlayerStat, Double> stats = statItem.getStats();
            for (PlayerStat stat : PlayerStat.values()) {
                double value = 0;

                Rune rune = Rune.getRune(stat);
                if (rune != null) {
                    if (gameItem.hasRunes() && gameItem.getRunes().containsKey(rune)) {
                        value += rune.getAmount() * gameItem.getRunes().get(rune);
                    }
                }

                if (statItem.hasStat(stat)) {
                    value += stats.get(stat);
                }

                if(gamePlayer != null){

                    /*
                    for(int i = 0; i < gamePlayer.getActiveAbilities().size(); i++){
                        EquipmentAbility ability = gamePlayer.getActiveAbilities().get(i);
                        if(ability instanceof AppliesWeaponBuff) {

                            if (((AppliesWeaponBuff) ability).getWeapon() == item) {

                                if (((AppliesWeaponBuff) ability).getBuff(item, gameItem).containsKey(stat)) {
                                    value += ((AppliesWeaponBuff) ability).getBuff(item, gameItem).get(stat);
                                }

                            }
                        }
                    }
                     */

                }

                if(value != 0) {
                    builder.append("<", TextColors.GRAY_DESCRIPTION)
                            .append(stat.getSymbol(), stat.getColor())
                            .append("> " + stat.getDisplayName() + ": ", TextColors.GRAY_DESCRIPTION)
                            .append((value > 0 ? "+" : "") + value, value > 0 ? TextColors.POSITIVE : TextColors.NEGATIVE).endLine();

                }
            }

            builder.skipLine();
        }
        if(isRunable){


            int index = builder.getList().size();
            int amount = 0;

            if(gameItem.hasRunes()){
                amount = gameItem.getRunesUsed();

                if(!gameItem.getRunes().isEmpty()) {
                    for (Map.Entry<Rune, Integer> runes : gameItem.getRunes().entrySet()) {
                        String value = "";
                        if (runes.getValue() > 1) value = " x" + runes.getValue();
                        builder.append("- " + runes.getKey().toString() + " Rune" + value, TextColors.WHITE).endLine();
                    }
                }else{
                    builder.append("Apply runes at the rune station!", NamedTextColor.DARK_GRAY).endLine();
                }

                builder.insert(index, "Runes (" + amount + "/" + ((Runeable) item).getMaxRunes() + ")", TextColors.RUNE_AMOUNT_DESCRIPTION);
                builder.skipLine();
            }

        }
        if(rightClick){
            RightClickAbility ability = (RightClickAbility) item;
            builder.append("Ability: " + ability.getRightClickAbilityName(), NamedTextColor.GOLD)
                    .append(" (Right Click)", NamedTextColor.YELLOW).endLine();
            builder.appendAll(ability.getRightClickDescription()).skipLine();
        }
        if(attack){
            AttackItemAbility ability = (AttackItemAbility) item;
            builder.append("Ability: " + ability.getAttackAbilityName(), NamedTextColor.GOLD).endLine();
            builder.appendAll(ability.getAttackDescription()).skipLine();
        }
        if(blockBreak){
            BlockBreakAbility ability = (BlockBreakAbility) item;
            builder.append("Ability: " + ability.getBlockBreakAbilityName(), NamedTextColor.GOLD).endLine();
            builder.appendAll(ability.getBlockBreakDescription()).skipLine();
        }
        if(item.getDescription() != null && !item.getDescription().isEmpty()){
            StringBuilder line = new StringBuilder();
            String[] words = item.getDescription().split(" ");
            int lineLength = 0;
            for(String word : words){
                if(lineLength + word.length() > 35){
                    builder.append(line.toString(), TextColors.GRAY_DESCRIPTION).endLine();
                    line = new StringBuilder();
                    lineLength = 0;
                }
                line.append(word).append(" ");
                lineLength += word.length() + 1;
            }
            if(line.length() > 0) builder.append(line.toString(), TextColors.GRAY_DESCRIPTION).endLine();
            builder.skipLine();
        }
        /*if(item.canApplyRunes() && gameItem.hasRunes() && gameItem.getRunes().isEmpty()){
            builder.append("Runes can be applied", NamedTextColor.DARK_GRAY).endLine();
        }*/
        builder.append(buildRarity(item)).endLine();
        meta.lore(builder.getList());
        return meta;
    }

    private static Component buildRarity(GameItem item){
        String string = item.getRarity().toString();
        if(item.getItemType().getCatagory() != ItemType.Catagory.Misc && item.getItemType().getCatagory() != ItemType.Catagory.Resource){
            string+= " " + item.getItemType().toString();
        }
        return Component.text(string).color(item.getRarity().getColor()).decorate(TextDecoration.BOLD);
    }


}
