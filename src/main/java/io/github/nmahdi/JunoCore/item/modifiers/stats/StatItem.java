package io.github.nmahdi.JunoCore.item.modifiers.stats;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import org.bukkit.Material;

import java.util.HashMap;

public class StatItem extends GameItem {

	private HashMap<PlayerStat, Double> stats = new HashMap<>();

	public StatItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, ItemType itemType) {
		super(itemManager, id, displayName, material, rarity, itemType);
		setHasUUID(true);
	}

	public HashMap<PlayerStat, Double> getStats() {
		return stats;
	}

	public boolean hasStat(PlayerStat stat){
		return stats.containsKey(stat);
	}

	public StatItem setHealth(int value){
		return setStat(PlayerStat.MaxHealth, value);
	}

	public StatItem setMana(double value){
		return setStat(PlayerStat.MaxMana, value);
	}

	public StatItem setDefense(double value){
		return setStat(PlayerStat.Defense, value);
	}

	public StatItem setFireElement(double value){
		return setStat(PlayerStat.FireElement, value);
	}

	public StatItem setWaterElement(double value){
		return setStat(PlayerStat.WaterElement, value);
	}

	public StatItem setAirElement(double value){
		return setStat(PlayerStat.AirElement, value);
	}

	public StatItem setEarthElement(double value){
		return setStat(PlayerStat.EarthElement, value);
	}

	public StatItem setLightningElement(double value){
		return setStat(PlayerStat.LightningElement, value);
	}

	public StatItem setIceElement(double value){
		return setStat(PlayerStat.IceElement, value);
	}

	public StatItem setSpeed(double value){
		return setStat(PlayerStat.Speed, value);
	}

	public StatItem setDamage(double value){
		return setStat(PlayerStat.Damage, value);
	}

	public StatItem setStrength(double value){
		return setStat(PlayerStat.Strength, value);
	}

	public StatItem setCritChance(double value){
		return setStat(PlayerStat.CritChance, value);
	}

	public StatItem setCritDamage(double value){
		return setStat(PlayerStat.CritDamage, value);
	}

	public StatItem setFortune(double value){
		return setStat(PlayerStat.Fortune, value);
	}

	public StatItem setHarvestingSpeed(double value){
		return setStat(PlayerStat.HarvestingSpeed, value);
	}

	public StatItem setFishingSpeed(double value) {
		return setStat(PlayerStat.FishingSpeed, value);
	}

	private StatItem setStat(PlayerStat id, double value){
		stats.put(id, value);
		return this;
	}

	public double getHealth() {
		return getStat(PlayerStat.MaxHealth);
	}

	public double getMana() {
		return getStat(PlayerStat.MaxMana);
	}

	public double getDefense() {
		return getStat(PlayerStat.Defense);
	}

	public double getFireElement() {
		return getStat(PlayerStat.FireElement);
	}

	public double getWaterElement() {
		return getStat(PlayerStat.WaterElement);
	}

	public double getAirElement() {
		return getStat(PlayerStat.AirElement);
	}

	public double getEarthElement() {
		return getStat(PlayerStat.EarthElement);
	}

	public double getLightningElement() {
		return getStat(PlayerStat.LightningElement);
	}

	public double getIceElement() {
		return getStat(PlayerStat.IceElement);
	}

	public double getSpeed() {
		return getStat(PlayerStat.Speed);
	}

	public double getDamage() {
		return getStat(PlayerStat.Damage);
	}

	public double getStrength() {
		return getStat(PlayerStat.Strength);
	}

	public double getCritChance() {
		return getStat(PlayerStat.CritChance);
	}

	public double getCritDamage() {
		return getStat(PlayerStat.CritDamage);
	}

	public double getFortune() {
		return getStat(PlayerStat.Fortune);
	}

	public double getHarvestingSpeed() {
		return getStat(PlayerStat.HarvestingSpeed);
	}

	public double getFishingSpeed() {
		return getStat(PlayerStat.FishingSpeed);
	}

	public double getStat(PlayerStat stat){
		return stats.get(stat);
	}

}
