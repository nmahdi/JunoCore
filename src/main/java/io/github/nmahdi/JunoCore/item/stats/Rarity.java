package io.github.nmahdi.JunoCore.item.stats;

public enum Rarity {

    Junk("7"),
    Common("f"),
    Uncommon("a"),
    Rare("b"),
    Unique("e"),
    Epic("5"),
    Legendary("6"),
    Mythic("d");

    String color;

    Rarity(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
