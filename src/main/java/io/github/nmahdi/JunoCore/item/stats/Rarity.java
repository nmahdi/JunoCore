package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.format.TextColor;

public enum Rarity {

    Junk(TextColors.JUNK),
    Common(TextColors.COMMON),
    Uncommon(TextColors.UNCOMMON),
    Rare(TextColors.RARE),
    Unique(TextColors.UNIQUE),
    Epic(TextColors.EPIC),
    Legendary(TextColors.LEGENDARY),
    Mythic(TextColors.MYTHIC);

    TextColor color;

    Rarity(TextColor color){
        this.color = color;
    }

    public TextColor getColor() {
        return color;
    }
}
