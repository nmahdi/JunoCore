package io.github.nmahdi.JunoCore.gui;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.builder.SkullItemBuilder;
import io.github.nmahdi.JunoCore.item.crafting.CraftingManager;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import io.github.nmahdi.JunoCore.player.NBTPlayer;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import io.github.nmahdi.JunoCore.player.skills.SkillID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerMenuGUI extends GUI{

    public final ItemStack CRAFTING_MENU = new ItemStackBuilder(Material.CRAFTING_TABLE).setName("&b&lCrafting Menu").addLore(
            "&7Click here to access the crafting menu!").build();
    public final ItemStack BANK_MENU = new ItemStackBuilder(Material.GOLD_NUGGET).setName("&6&lBank").addLore(
            "&7Click here to access the bank menu!").build();


    public final ItemStack menuItem = new ItemStackBuilder(Material.NETHER_STAR).setName("&e&lPlayer Menu").addLore("", "&7Click to open your player menu!").build();

    private JPlayerManager playerManager;
    private CraftingManager craftingManager;

    public PlayerMenuGUI(JCore main) {
        super(main,"&fPlayer Menu", 54, null);
        this.playerManager = main.getPlayerManager();
        this.craftingManager = new CraftingManager(main, this);
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().getInventory().setItem(8, menuItem);
    }

    @EventHandler
    public void onMenuOpen(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) return;
        if(e.getPlayer().getInventory().getItemInMainHand().isSimilar(menuItem)) openInventory(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().isSimilar(menuItem)) e.setCancelled(true);
        if(e.getView().getTitle().equals(getName())){
            e.setCancelled(true);
            if(e.getCurrentItem().isSimilar(CRAFTING_MENU)) craftingManager.openCraftingMenu((Player)e.getWhoClicked());
        }
    }


    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());
        NBTPlayer nPlayer = new NBTPlayer(player);

        SkullItemBuilder bStats = new SkullItemBuilder(player).setName("&b&lStats");
        for(PlayerStatID stat : PlayerStatID.values()){
            if(stat.isOnMenu()) bStats.addLore(stat.getColor() + stat.getSymbol() + " " + stat.getDisplayName() + "&f: " + nPlayer.getStat(stat));
        }
        inventory.setItem(13, bStats.build());

        ItemStackBuilder bSkills = new ItemStackBuilder(Material.IRON_AXE).setName("&b&lSkills");
        for(SkillID skill : SkillID.values()){
            bSkills.addLore("&7" + skill.getDisplayName() + "&f: " + nPlayer.getSkills().getCompound(skill.getId()).getInteger("level") + "(" + nPlayer.getSkills().getCompound(skill.getId()).getLong("xp") + ")");
        }
        inventory.setItem(20, bSkills.build());

        inventory.setItem(21, CRAFTING_MENU);

        inventory.setItem(22, BANK_MENU);

        for (int i = 0; i < getSize(); i++) {
            if(inventory.getItem(i) == null) inventory.setItem(i, EMPTY);
        }

        player.openInventory(inventory);
    }

}
