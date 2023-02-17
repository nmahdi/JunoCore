package io.github.nmahdi.JunoCore.gui;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.text.TextColors;
import io.github.nmahdi.JunoCore.item.stats.SkullURL;
import io.github.nmahdi.JunoCore.item.builder.SkullItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class NPCGUI extends GUI{

    protected ItemStack npcSkull;
    protected String npcName;
    protected String skullLore;

    public NPCGUI(JCore main, String name, int size, GUI previousMenu, String npcName, String skullLore) {
        super(main, name, size, previousMenu);
        this.npcName = ChatColor.translateAlternateColorCodes('&', npcName);
        this.skullLore = skullLore;
        npcSkull = new SkullItemBuilder(SkullURL.GEODE).setName(getNPCName(), TextColors.GRAY_DESCRIPTION, false).addLore(getSkullLore(), TextColors.GRAY_DESCRIPTION, false).build();
    }

    protected void addNpcSkull(Inventory inventory){
        inventory.setItem(13, npcSkull);
    }

    public String getNPCName() {
        return npcName;
    }

    public String getSkullLore() {
        return skullLore;
    }
}
