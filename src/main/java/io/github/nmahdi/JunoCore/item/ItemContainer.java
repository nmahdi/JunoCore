package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.item.ability.equipment.EquipmentAbility;
import io.github.nmahdi.JunoCore.item.ability.item.ItemAbility;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.loot.LootTable;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemContainer {

    public String id;
    public String displayName;
    public Material material;

    public Rarity rarity;
    public String description;
    public boolean hasUUID = false;
    public ItemType itemType;
    public int npcSellPrice = 0;
    public int npcBuyPrice = 0;

    public boolean dismantlable = false;
    public LootTable lootTable;

    public int runeSlots = 0;
    public HashMap<PlayerStat, String> stats = new HashMap<>();

    public Rune rune;

    public int modelData = 999;

    public ItemAbility itemAbility;
    public EquipmentAbility equipmentAbility;

    public ItemContainer(String id, String displayName, Material material, Rarity rarity, ItemType itemType){
        this.id = id;
        this.displayName = displayName;
        this.material = material;
        this.rarity = rarity;
        this.itemType = itemType;
        if(itemType.getCatagory() == ItemType.Catagory.ARMOR || itemType.getCatagory() == ItemType.Catagory.EQUIPMENT || itemType.getCatagory() == ItemType.Catagory.TOOL
        || itemType.getCatagory() == ItemType.Catagory.WEAPON){
            hasUUID = true;
        }
    }

    public ItemContainer addDescription(String description){
        this.description = description;
        return this;
    }

    public ItemContainer setHasUUID(){
        hasUUID = true;
        return this;
    }

    public ItemContainer setNPCSellPrice(int npcSellPrice){
        this.npcSellPrice = npcSellPrice;
        return this;
    }

    public ItemContainer setNPCBuyPrice(int npcBuyPrice){
        this.npcBuyPrice = npcBuyPrice;
        return this;
    }

    public ItemContainer setDismantlable(LootTable lootTable){
        dismantlable = true;
        this.lootTable = lootTable;
        return this;
    }

    public ItemContainer setRuneSlots(int runeSlots){
        this.runeSlots = runeSlots;
        return this;
    }

    public ItemContainer setHealth(int value){
        return setInt(PlayerStat.MaxHealth, value);
    }

    public ItemContainer setMana(int value){
        return setInt(PlayerStat.MaxMana, value);
    }

    public ItemContainer setDefense(int value){
        return setInt(PlayerStat.Defense, value);
    }

    public ItemContainer setFireElement(int value){
        return setInt(PlayerStat.FireElement, value);
    }

    public ItemContainer setWaterElement(int value){
        return setInt(PlayerStat.WaterElement, value);
    }

    public ItemContainer setAirElement(int value){
        return setInt(PlayerStat.AirElement, value);
    }

    public ItemContainer setEarthElement(int value){
        return setInt(PlayerStat.EarthElement, value);
    }

    public ItemContainer setLightningElement(int value){
        return setInt(PlayerStat.LightningElement, value);
    }

    public ItemContainer setIceElement(int value){
        return setInt(PlayerStat.IceElement, value);
    }

    public ItemContainer setSpeed(int value){
        return setInt(PlayerStat.Speed, value);
    }

    public ItemContainer setDamage(int value){
        return setInt(PlayerStat.Damage, value);
    }

    public ItemContainer setStrength(int value){
        return setInt(PlayerStat.Strength, value);
    }

    public ItemContainer setCritChance(int value){
        return setInt(PlayerStat.CritChance, value);
    }

    public ItemContainer setCritDamage(int value){
        return setInt(PlayerStat.CritDamage, value);
    }

    public ItemContainer setFortune(int value){
        return setInt(PlayerStat.Fortune, value);
    }

    public ItemContainer setHarvestingSpeed(int value){
        return setInt(PlayerStat.HarvestingSpeed, value);
    }

    public ItemContainer setFishingSpeed(int value) {
        return setInt(PlayerStat.FishingSpeed, value);
    }

    private ItemContainer setInt(PlayerStat id, int value){
        stats.put(id, String.valueOf(value));
        return this;
    }
    public ItemContainer setRune(Rune rune){
        this.rune = rune;
        return this;
    }

    public ItemContainer setCustomModelData(int modelData) {
        this.modelData = modelData;
        return this;
    }

    public ItemContainer setItemAbility(ItemAbility itemAbility){
        this.itemAbility = itemAbility;
        return this;
    }

    public ItemContainer setEquipmentAbility(EquipmentAbility equipmentAbility){
        this.equipmentAbility = equipmentAbility;
        return this;
    }


}
