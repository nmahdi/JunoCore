package io.github.nmahdi.JunoCore.item.stats;

public class Stat {

    private final ItemStatID id;
    private final String value;

    public Stat(ItemStatID id, String value){
        this.id = id;
        this.value = value;
    }

    public ItemStatID getID() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
