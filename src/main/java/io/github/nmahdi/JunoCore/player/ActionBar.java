package io.github.nmahdi.JunoCore.player;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBar {

    private NBTPlayer player;
    private String defaultMessage;
    private String currentMessage;

    public ActionBar(Player player){
        this.player = new NBTPlayer(player);
        this.defaultMessage = "&c&l%health_icon%%health%/%max_health%          &b&l%mana_icon%%mana%/%max_mana%";
    }

    public void send(){
        String temp = defaultMessage;
        for(PlayerStatID id : PlayerStatID.values()){
            temp = temp.replaceAll(id.getPlaceHolder(), String.valueOf(player.getStat(id)));
        }
        currentMessage = temp.replaceAll("%health_icon%", PlayerStatID.Health.getSymbol()).replaceAll("%mana_icon%", PlayerStatID.Mana.getSymbol());
        player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', currentMessage)));
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
