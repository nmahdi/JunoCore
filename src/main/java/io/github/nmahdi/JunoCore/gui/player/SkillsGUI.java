package io.github.nmahdi.JunoCore.gui.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.text.DecimalFormat;

public class SkillsGUI extends GUI {

    private PlayerManager playerManager;
    private DecimalFormat format = new DecimalFormat("0.00");

    public SkillsGUI(JCore main, PlayerMenuGUI playerMenuGUI) {
        super(main, "&bSkills", 54, playerMenuGUI);
        this.playerManager = main.getPlayerManager();
    }

    @Override
    public void onInvClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        GamePlayer gamePlayer = playerManager.getPlayer(player);

        int index = 20;
        for(Skill skill : Skill.values()){
            GamePlayer.PlayerSkill current = gamePlayer.getSkill(skill);
            inventory.setItem(index, new ItemStackBuilder(skill.getMenuItem())
                    .setName(skill.getDisplayName() + " " + current.getLevel(), NamedTextColor.AQUA, true)
                    .addLore(Component.text("Progress to level " + (current.getLevel()+1) + ":").color(TextColors.GRAY))
                    .addLore(Component.text("XP needed: " + Skill.getXPNeeded(gamePlayer, skill)).color(TextColors.GRAY))
                    .addLore(getSkillProgress(current.getLevel(), current.getXP())).build());
            if(index == 24){
                index=31;
            }else{
                index++;
            }
        }

        insertBack(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
    }

    public Component getSkillProgress(int level, long currentXP){
        TextComponent.Builder component = Component.text();
        long needed = Skill.getXPRequirement(level);
        double percentage = ((double)currentXP/needed)*100;

        String DASH = "-";
        component.append(Component.text(DASH.repeat((int)percentage/10)).color(TextColors.POSITIVE));
        component.append(Component.text(DASH.repeat(10-((int)percentage/10))).color(TextColors.GRAY));
        component.append(Component.text(" (" + format .format(percentage)+ "%)").color(TextColors.YELLOW));
        return component.build();
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

}
