package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.player.PlayerStatID;

public enum ConsumableID {
    Health(PlayerStatID.Health),
    Mana(PlayerStatID.Mana),
    Defense(PlayerStatID.Defense),
    Strength(PlayerStatID.Strength)
    ;

    private PlayerStatID playerID;

    ConsumableID(PlayerStatID playerID){
        this.playerID = playerID;
    }

    public PlayerStatID getPlayerID() {
        return playerID;
    }

}
