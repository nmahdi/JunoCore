package io.github.nmahdi.JunoCore.gui.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.builder.DescriptionBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.text.DecimalFormat;

public class SkillsGUI extends GUI {

    private PlayerManager playerManager;
    private DecimalFormat format = new DecimalFormat("0.00");

    public SkillsGUI(JCore main, PlayerMenuGUI playerMenuGUI) {
        super(main, "Skills", 54, playerMenuGUI);
        this.playerManager = main.getPlayerManager();
    }

    @Override
    public void onInvClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void setItems(Inventory inventory, Player player) {
        GamePlayer gamePlayer = playerManager.getPlayer(player);

        int index = 20;
        for(Skill skill : Skill.values()){
            GamePlayer.PlayerSkill current = gamePlayer.getSkill(skill);
            double percentage = ((double)current.getXP()/Skill.getXPRequirement(current.getLevel()))*100;
            DescriptionBuilder builder = new DescriptionBuilder("Progress to Level " + (current.getLevel()+1) + ":", TextColors.GRAY_DESCRIPTION)
                    .append(" (" + format.format(percentage) + "%)", TextColors.YELLOW).endLine()
                    .append(getSkillProgress(current.getXP(), Skill.getXPRequirement(current.getLevel()), percentage)).endLine()
                    .skipLine()
                    .append("Level " + (current.getLevel()+1) + " Rewards:", TextColors.GRAY_DESCRIPTION).endLine();

            inventory.setItem(index, new ItemStackBuilder(skill.getMenuItem())
                    .setName(skill.getDisplayName() + " " + current.getLevel(), NamedTextColor.AQUA, true)
                    .addLore(builder.getList()).build());
            if(index == 24){
                index=31;
            }else{
                index++;
            }
        }

        insertBack(inventory);
        insertFiller(inventory);
    }

    public Component getSkillProgress(long currentXP, long needed, double percentage){
        TextComponent.Builder component = Component.text();


        String DASH = "-";
        component.append(Component.text(DASH.repeat((int)percentage/10)).color(TextColors.POSITIVE).decorate(TextDecoration.STRIKETHROUGH));
        component.append(Component.text(DASH.repeat(10-((int)percentage/10))).color(TextColors.GRAY_DESCRIPTION).decorate(TextDecoration.STRIKETHROUGH));
        component.append(Component.text(" " + currentXP + "/" + needed).color(TextColors.YELLOW));
        return component.build();
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

}
