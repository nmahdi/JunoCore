package io.github.nmahdi.JunoCore.player.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import io.github.nmahdi.JunoCore.gui.JGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import io.github.nmahdi.JunoCore.player.skills.SkillID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerMenuListener implements Listener {

    private JPlayerManager playerManager;

    private ItemStack menuItem = new ItemStackBuilder(Material.NETHER_STAR).setName("&e&lPlayer Menu").addLore("", "&7Click to open your player menu!").build();
    private JGUI mainMenu = new JGUI("&fPlayer Menu", 6, true)
            .addItem("stats", 14)
            .addItem("skills", 21)
            .addItem("bank", 23);


    public PlayerMenuListener(JPlayerManager playerManager) {
        this.playerManager = playerManager;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().getInventory().setItem(8, menuItem);
    }

    @EventHandler
    public void onMenuOpen(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) return;
        if(e.getPlayer().getInventory().getItemInMainHand().isSimilar(menuItem)) buildAndOpenPlayerMenu(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().isSimilar(menuItem)) e.setCancelled(true);
        if(e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', mainMenu.getName()))){
            e.setCancelled(true);
        }
    }

    private void buildAndOpenPlayerMenu(Player player){
        NBTEntity nPlayer = new NBTEntity(player);
        NBTCompound juno = nPlayer.getPersistentDataContainer().getCompound("juno");
        NBTCompound skills = juno.getCompound("skills");
        NBTCompound stats = juno.getCompound("stats");
        Inventory inv = Bukkit.createInventory(null, mainMenu.getSize(), ChatColor.translateAlternateColorCodes('&', mainMenu.getName()));

        if (mainMenu.hasFiller()) {
            for (int i = 0; i < mainMenu.getSize(); i++) {
                inv.setItem(i, JGUI.EMPTY);
            }
        }

        for (Map.Entry<String, Integer> set : mainMenu.getItems().entrySet()) {
            if(set.getKey().equals("stats")) {
                ItemStackBuilder builder = new ItemStackBuilder(Material.PLAYER_HEAD);
                for(PlayerStatID stat : PlayerStatID.values()){
                    if(stat.isOnMenu()) builder.addLore(stat.getColor() + stat.getSymbol() + " " + stat.getDisplayName() + "&f: " + stats.getInteger(stat.getId()));
                }
                    inv.setItem(set.getValue() - 1, builder.buildSkull("&b&lStats", player.getUniqueId()));
            }
            if(set.getKey().equals("skills")){
                ItemStackBuilder builder = new ItemStackBuilder(Material.IRON_AXE).setName("&b&lSkills");
                for(SkillID skill : SkillID.values()){
                    builder.addLore("&7" + skill.getDisplayName() + "&f: " + skills.getCompound(skill.getId()).getInteger("level") + "(" + skills.getCompound(skill.getId()).getLong("xp") + ")");
                }
                inv.setItem(set.getValue() - 1, builder.build());
            }
            if(set.getKey().equals("bank")){
                inv.setItem(set.getValue() - 1, new ItemStackBuilder(Material.GOLD_NUGGET).setName("&6&lBank").addLore(
                        "&7Click here to access the bank menu!"
                ).build());
            }
        }

        player.openInventory(inv);
    }

}
