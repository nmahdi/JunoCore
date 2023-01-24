package io.github.nmahdi.JunoCore.item.builder;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.equipment.AppliesWeaponBuff;
import io.github.nmahdi.JunoCore.item.ability.equipment.EquipmentAbility;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        finalItem.setItemMeta(updateMeta(null, item, gameItem));
        return finalItem;
    }

    public static ItemMeta updateMeta(GamePlayer gamePlayer, GameItem item, NBTGameItem gameItem){

        if(item.hasUUID() && !gameItem.hasUUID()) gameItem.setUUID(UUID.randomUUID().toString());
        if(item.canApplyRunes() && !gameItem.hasRunes()) gameItem.createRunes();

        ItemMeta meta = gameItem.getItem().getItemMeta();

        if(item.hasCustomModelData()) meta.setCustomModelData(item.getCusomModelData());
        meta.displayName(Component.text(item.getDisplayName()).color(item.getRarity().getColor()).decoration(TextDecoration.ITALIC, false));

        DescriptionBuilder builder = new DescriptionBuilder(false);

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

                if(gamePlayer != null){

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
        if(item.canApplyRunes()){

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

                builder.insert(index, "Runes (" + amount + "/" + item.getRuneSlots() + ")", TextColors.RUNE_AMOUNT_DESCRIPTION);
                builder.skipLine();
            }

        }
        if(item.hasItemAbility()){
            builder.appendAll(item.getItemAbility().getAbilityDescription()).skipLine();
        }
        if(item.hasEquipmentAbility()){
            builder.appendAll(item.getEquipmentAbility().getAbilityDescription()).skipLine();
        }
        if(item.hasDescription()){
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
        if(item.getItemType().getCatagory() != ItemType.Catagory.MISC && item.getItemType().getCatagory() != ItemType.Catagory.RESOURCE){
            string+= " " + item.getItemType().toString();
        }
        return Component.text(string).color(item.getRarity().getColor()).decorate(TextDecoration.BOLD);
    }


}
