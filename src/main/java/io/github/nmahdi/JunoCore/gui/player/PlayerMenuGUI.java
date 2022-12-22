package io.github.nmahdi.JunoCore.gui.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.builder.SkullItemBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerMenuGUI extends GUI {

    private final ItemStack SKILLS_MENU = new ItemStackBuilder(Material.IRON_AXE).setName("Skills", NamedTextColor.AQUA, true)
            .addLore(CLICK_TO_VIEW).build();

    private final ItemStack CRAFTING_MENU = new ItemStackBuilder(Material.CRAFTING_TABLE).setName("Crafting Menu", NamedTextColor.AQUA, true)
            .addLore(CLICK_TO_VIEW).build();

    private final ItemStack BANK_MENU = new ItemStackBuilder(Material.GOLD_NUGGET).setName("Bank", NamedTextColor.AQUA, true)
            .addLore(CLICK_TO_VIEW).build();

    private final ItemStack BESTIARY = new ItemStackBuilder(Material.WRITTEN_BOOK).setName("Bestiary", NamedTextColor.AQUA, true)
            .addLore(CLICK_TO_VIEW).build();

    private final ItemStack STORAGE = new ItemStackBuilder(Material.ENDER_CHEST).setName("Storage", NamedTextColor.AQUA, true)
            .addLore(CLICK_TO_VIEW).build();


    public final ItemStack menuItem = new ItemStackBuilder(Material.NETHER_STAR).setName("Player Menu", NamedTextColor.GOLD, true)
            .addLore(Component.text("Access your skills, coins, storage & much more!").color(TextColors.GRAY_DESCRIPTION))
            .skipLore().addLore(CLICK_TO_VIEW).build();

    private PlayerManager playerManager;

    private SkillsGUI skillsGUI;
    private PlayerCraftingGUI craftingGUI;
    private BankGUI bankGUI;

    public PlayerMenuGUI(JCore main) {
        super(main,"Player Menu", 54, null);
        this.playerManager = main.getPlayerManager();
        this.skillsGUI = new SkillsGUI(main, this);
        this.craftingGUI = new PlayerCraftingGUI(main, this);
        this.bankGUI = new BankGUI(main, this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().getInventory().setItem(8, menuItem);
    }

    //TODO: FIX bug where cape isnt updating after opening player MENU
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuClick(InventoryClickEvent e){
        if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem().isSimilar(menuItem)){
            if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
                e.setCancelled(true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> openInventory((Player) e.getWhoClicked()), 1);
            }
        }
    }

    @EventHandler
    public void onMenuDrop(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().isSimilar(menuItem)) e.setCancelled(true);
    }


    @EventHandler
    public void onMenuOpen(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(InventoryHelper.isAirOrNull(e.getPlayer().getInventory().getItemInMainHand())) return;
        if(e.getPlayer().getInventory().getItemInMainHand().isSimilar(menuItem)) openInventory(e.getPlayer());
    }

    @Override
    public void onInvClick(InventoryClickEvent e){
        e.setCancelled(true);
        if(e.getCurrentItem().isSimilar(SKILLS_MENU)) skillsGUI.openInventory((Player)e.getWhoClicked());
        if(e.getCurrentItem().isSimilar(CRAFTING_MENU)) craftingGUI.openInventory((Player)e.getWhoClicked());
        if(e.getCurrentItem().isSimilar(BANK_MENU)) bankGUI.openInventory((Player)e.getWhoClicked());
        if(e.getCurrentItem().isSimilar(STORAGE)) e.getWhoClicked().openInventory(playerManager.getPlayer((Player)e.getWhoClicked()).getStorage());
    }


    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());
        GamePlayer gamePlayer = playerManager.getPlayer(player);

        SkullItemBuilder bStats = (SkullItemBuilder) new SkullItemBuilder(player).setName("Stats", NamedTextColor.AQUA, true);
        bStats.addLore(Component.text("View your stats!").color(TextColors.GRAY_DESCRIPTION));
        bStats.addLore(Component.empty());
        for(PlayerStat stat : PlayerStat.values()){
            //stat.getSymbol() + " " + stat.getDisplayName() + ": " + nPlayer.getStat(stat)
            if(stat.isOnMenu()) bStats.addLore(Component.text(stat.getSymbol() + " " + stat.getDisplayName()).color(TextColor.color(stat.getColor()))
                    .append(Component.text(": " + gamePlayer.getStats().get(stat).intValue()).color(TextColor.color(TextColors.WHITE))).decoration(TextDecoration.ITALIC, false));
        }
        bStats.addLore(Component.empty());
        bStats.addLore(CLICK_TO_VIEW);

        inventory.setItem(13, bStats.build());


        inventory.setItem(20, SKILLS_MENU);

        inventory.setItem(21, CRAFTING_MENU);

        inventory.setItem(22, BANK_MENU);

        inventory.setItem(23, BESTIARY);

        inventory.setItem(24, STORAGE);

        insertFiller(inventory);

        player.openInventory(inventory);
    }

    @Override
    public void onInvClose(Inventory inventory, Player player) {

    }

}
