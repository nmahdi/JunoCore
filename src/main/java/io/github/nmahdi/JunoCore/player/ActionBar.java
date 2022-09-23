package io.github.nmahdi.JunoCore.player;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBar {


    private Player player;
    private NBTCompound stats;
    private String defaultMessage;
    private String currentMessage;

    public ActionBar(Player player){
        this.player = player;
        this.stats = new NBTEntity(player).getPersistentDataContainer().getCompound("juno").getCompound("stats");
        this.defaultMessage = "&c%health%/%max_health%         &b%mana%/%max_mana%";
    }

    public void send(){
        String temp = defaultMessage;
        for(PlayerStatID id : PlayerStatID.values()){
            temp = temp.replaceAll(id.getActionBarID(), String.valueOf(stats.getInteger(id.getId())));
        }
        currentMessage = temp;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', currentMessage)));
    }

    public Player getPlayer() {
        return player;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }
}
