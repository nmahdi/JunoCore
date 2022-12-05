package io.github.nmahdi.JunoCore.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.combat.EquipmentListener;
import io.github.nmahdi.JunoCore.player.display.ActionBar;
import io.github.nmahdi.JunoCore.player.display.ScoreboardManager;
import io.github.nmahdi.JunoCore.player.stats.DamageType;
import io.github.nmahdi.JunoCore.player.stats.Element;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GamePlayer{

	public static final String PROJECTILE_META = "PROJECTILE_DAMAGE";

	private Player player;

	//Stats
	private long coins;
	private PlayerStats stats;
	private HashMap<Skill, PlayerSkill> skills = new HashMap<>();

	//Equipment
	private GameItem heldItem;
	private GameItem helmet;
	private GameItem chestplate;
	private GameItem leggings;
	private GameItem boots;

	private GameItem bracelet;
	private GameItem ring;
	private GameItem headband;
	private GameItem necklace;
	private GameItem cape;

	public HashMap<Integer, ItemStack> equipment = new HashMap<>();


	//private VirtualInventory virtualInventory;
	//private Inventory storage;

	private ActionBar actionBar;
	private int taskID;

	public GamePlayer(Player player){
		this.player = player;
		this.stats = new PlayerStats(this);
		this.actionBar = new ActionBar(this);
	}

	public void loadData(ResultSet data) throws SQLException {
		this.coins = data.getLong("Coins");
		for(Skill skill : Skill.values()){
			long xp = data.getLong(skill.getSQLName());
			this.skills.put(skill, new PlayerSkill(Skill.getCurrentLevel(xp), xp));
		}
	}

	public void newPlayer(){
		this.coins = 0;
		for(Skill skill : Skill.values()){
			this.skills.put(skill, new PlayerSkill(0, 0));
		}
	}

	public void login(JCore main, ScoreboardManager scoreboardManager){
		scoreboardManager.set(this);

		if(player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING){
			CraftingInventory inventory = (CraftingInventory) player.getOpenInventory().getTopInventory();
			for(Map.Entry<Integer, ItemStack> items : equipment.entrySet()){
				inventory.setItem(items.getKey(), items.getValue());
			}
			inventory.setItem(EquipmentListener.CAPE_SLOT, equipment.get(EquipmentListener.CAPE_SLOT));
		}

		if(getNBTHeldItem() != null && getNBTHeldItem().hasID()){
			GameItem temp = GameItem.getItem(getNBTHeldItem().getID());
			if(temp != null && temp.isHandEquipable()) {
				heldItem = temp;
			}
		}

		if(getNBTHelmet() != null && getNBTHelmet().hasID()){
			GameItem temp = GameItem.getItem(getNBTHelmet().getID());
			if(temp != null && temp.getItemType() == ItemType.Helmet) {
				helmet = temp;
			}
		}

		if(getNBTChestplate() != null && getNBTChestplate().hasID()){
			GameItem temp = GameItem.getItem(getNBTChestplate().getID());
			if(temp != null && temp.getItemType() == ItemType.Chestplate) {
				chestplate = temp;
			}
		}

		if(getNBTLeggings() != null && getNBTLeggings().hasID()){
			GameItem temp = GameItem.getItem(getNBTLeggings().getID());
			if(temp != null && temp.getItemType() == ItemType.Leggings) {
				leggings = temp;
			}
		}

		if(getNBTBoots() != null && getNBTBoots().hasID()){
			GameItem temp = GameItem.getItem(getNBTBoots().getID());
			if(temp != null && temp.getItemType() == ItemType.Boots) {
				boots = temp;
			}
		}

		if(getNBTCape() != null && getNBTCape().hasID()){
			GameItem temp = GameItem.getItem(getNBTCape().getID());
			if(temp != null && temp.getItemType() == ItemType.Cape) {
				cape = temp;
			}
		}

		if(getNBTBracelet() != null && getNBTBracelet().hasID()){
			GameItem temp = GameItem.getItem(getNBTBracelet().getID());
			if(temp != null && temp.getItemType() == ItemType.Bracelet) {
				bracelet = temp;
			}
		}

		if(getNBTRing() != null && getNBTRing().hasID()){
			GameItem temp = GameItem.getItem(getNBTRing().getID());
			if(temp != null && temp.getItemType() == ItemType.Ring) {
				ring = temp;
			}
		}

		if(getNBTHeadband() != null && getNBTHeadband().hasID()){
			GameItem temp = GameItem.getItem(getNBTHeadband().getID());
			if(temp != null && temp.getItemType() == ItemType.Headband) {
				headband = temp;
			}
		}

		if(getNBTNecklace() != null && getNBTNecklace().hasID()){
			GameItem temp = GameItem.getItem(getNBTNecklace().getID());
			if(temp != null && temp.getItemType() == ItemType.Necklace) {
				necklace = temp;
			}
		}

		stats.login();

		this.taskID = main.getServer().getScheduler().scheduleAsyncRepeatingTask(main, () -> {
			//Display
			actionBar.send();
			scoreboardManager.update(this);

			//Regen
			if(stats.getHealth() < stats.getMaxHealth()){
				stats.plusHealth(stats.getHealthRegen());
			}
			if(stats.getMana() < stats.getMaxMana()){
				stats.plusMana(stats.getManaRegen());
			}
		}, 20, 20);
	}

	public void logout(JCore main){
		main.getServer().getScheduler().cancelTask(taskID);
	}



	public int getDamage(Random random){
		double damage = stats.getDamage();
		double strength = stats.getStrength();

		double total = damage+(strength/4);

		if(random.nextInt(100)+1 < stats.getCritChance()) total+=total*((double)stats.getCritDamage()/100);

		return (int)total;
	}

	/**
	 * Damages the player
	 */
	public void damage(int damage, DamageType damageType, Element element){
		if(element != null){
			damage-=stats.get(element.getPlayerElement());
		}else{
			damage-=stats.getDefense();
		}
		stats.minusHealth(Math.max(damage, 1));
	}

	/**
	 * Equips an item and updates the stats.
	 */
	public void equip(GameItem item, NBTGameItem gameItem){
		if(item == null || gameItem == null) return;
		updateEquipment(item, item.getItemType());
		for(PlayerStat stat : PlayerStat.values()){
			int value = 0;
			if(item.hasStat(stat)){
				value+=Integer.parseInt(item.getStat(stat));
			}
			if(item.canApplyRunes()) {
				Rune rune = Rune.getRune(stat);
				if (rune != null && gameItem.getRunes().containsKey(rune))
					value += rune.getAmount() * gameItem.getRunes().get(rune);
			}
			if(value != 0) {
				stats.put(stat, stats.get(stat)+value);
			}
		}
		stats.updateStats();
	}

	/**
	 * Unequips an item and updates the stats.
	 */
	public void unequip(GameItem item, NBTGameItem gameItem){
		if(item == null || gameItem == null) return;
		updateEquipment(null, item.getItemType());
		for(PlayerStat stat : PlayerStat.values()){
			int value = 0;
			if(item.hasStat(stat)){
				value+=Integer.parseInt(item.getStat(stat));
			}
			if(item.canApplyRunes()) {
				Rune rune = Rune.getRune(stat);
				if (rune != null && gameItem.getRunes().containsKey(rune))
					value += rune.getAmount() * gameItem.getRunes().get(rune);
			}
			if(value != 0) {
				stats.put(stat, stats.get(stat)-value);
			}
		}
		stats.updateStats();
	}

	private void updateEquipment(GameItem item, ItemType itemType){
		switch(itemType){
			case Helmet:
				helmet = item;
				break;
			case Chestplate:
				chestplate = item;
				break;
			case Leggings:
				leggings = item;
				break;
			case Boots:
				boots = item;
				break;
			case Bracelet:
				bracelet = item;
				break;
			case Ring:
				ring = item;
				break;
			case Necklace:
				necklace = item;
				break;
			case Headband:
				headband = item;
				break;
			case Cape:
				cape = item;
				break;
			case Sword:
			case Bow:
			case Wand:
			case Pickaxe:
			case Axe:
			case Shovel:
			case Hoe:
			case Shears:
				heldItem = item;
				break;
		}
	}

	public void kill(){
		player.damage(0);
		player.sendMessage(ChatColor.RED + "YOU DIED!");
		player.teleport(player.getWorld().getSpawnLocation());
		stats.setHealth(stats.getMaxHealth());
		stats.setMana(stats.getMaxMana());
	}

	public void heal(){
		stats.setHealth(stats.getMaxHealth());
		stats.setMana(stats.getMaxMana());
	}

	public long getCoins() {
		return coins;
	}

	public void plusCoins(long amount){
		player.sendMessage(ChatColor.GREEN + "+" + amount + " coins have been added to your account.");
		setCoins(getCoins()+amount);
	}

	public void minusCoins(long amount){
		player.sendMessage(ChatColor.RED + "-" + amount + " coins have been taken out of your account.");
		setCoins(getCoins()-amount);
	}

	public void setCoins(long amount){
		this.coins = amount;
	}

	public PlayerStats getStats() {
		return stats;
	}

	public PlayerSkill getCombat(){
		return skills.get(Skill.Combat);
	}

	public PlayerSkill getMining(){
		return skills.get(Skill.Mining);
	}

	public PlayerSkill getForaging(){
		return skills.get(Skill.Foraging);
	}

	public PlayerSkill getFishing(){
		return skills.get(Skill.Fishing);
	}

	public PlayerSkill getWoodcutting(){
		return skills.get(Skill.Woodcutting);
	}

	public PlayerSkill getFarming(){
		return skills.get(Skill.Farming);
	}

	public PlayerSkill getMetalDetecting(){
		return skills.get(Skill.MetalDetecting);
	}

	public PlayerSkill getSkill(Skill skill){
		return skills.get(skill);
	}

	public boolean hasHeldItem(){
		return heldItem != null;
	}

	public GameItem getHeldItem(){
		return heldItem;
	}

	public NBTGameItem getNBTHeldItem(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getItemInMainHand()) ? new NBTGameItem(player.getInventory().getItemInMainHand()) : null;
	}

	public boolean hasHelmet(){
		return helmet != null;
	}

	public GameItem getHelmet(){
		return helmet;
	}

	public NBTGameItem getNBTHelmet(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getHelmet()) ? new NBTGameItem(player.getInventory().getHelmet()) : null;
	}

	public boolean hasChestplate(){
		return chestplate != null;
	}

	public GameItem getChestplate(){
		return chestplate;
	}

	public NBTGameItem getNBTChestplate(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getChestplate()) ? new NBTGameItem(player.getInventory().getChestplate()) : null;
	}

	public boolean hasLeggings(){
		return leggings != null;
	}

	public GameItem getLeggings() {
		return leggings;
	}

	public NBTGameItem getNBTLeggings(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getLeggings()) ? new NBTGameItem(player.getInventory().getLeggings()) : null;
	}

	public boolean hasBoots(){
		return boots != null;
	}

	public GameItem getBoots() {
		return boots;
	}

	public NBTGameItem getNBTBoots(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getBoots()) ? new NBTGameItem(player.getInventory().getBoots()) : null;
	}

	public boolean hasCape(){
		return cape != null;
	}

	public GameItem getCape() {
		return cape;
	}

	public NBTGameItem getNBTCape(){
		return !InventoryHelper.isAirOrNull(equipment.get(EquipmentListener.CAPE_SLOT)) ? new NBTGameItem(equipment.get(EquipmentListener.CAPE_SLOT)) : null;
	}

	public boolean hasBracelet(){
		return bracelet != null;
	}

	public GameItem getBracelet() {
		return bracelet;
	}

	public NBTGameItem getNBTBracelet(){
		return !InventoryHelper.isAirOrNull(equipment.get(EquipmentListener.BRACELET_SLOT)) ? new NBTGameItem(equipment.get(EquipmentListener.BRACELET_SLOT)) : null;
	}

	public boolean hasRing(){
		return ring != null;
	}

	public GameItem getRing(){
		return ring;
	}

	public NBTGameItem getNBTRing(){
		return !InventoryHelper.isAirOrNull(equipment.get(EquipmentListener.RING_SLOT)) ? new NBTGameItem(equipment.get(EquipmentListener.RING_SLOT)) : null;
	}

	public boolean hasHeadband(){
		return headband != null;
	}

	public GameItem getHeadband(){
		return headband;
	}

	public NBTGameItem getNBTHeadband(){
		return !InventoryHelper.isAirOrNull(equipment.get(EquipmentListener.HEADBAND_SLOT)) ? new NBTGameItem(equipment.get(EquipmentListener.HEADBAND_SLOT)) : null;
	}

	public boolean hasNecklace(){
		return necklace != null;
	}

	public GameItem getNecklace() {
		return necklace;
	}

	public NBTGameItem getNBTNecklace(){
		return !InventoryHelper.isAirOrNull(equipment.get(EquipmentListener.NECKLACE_SLOT)) ? new NBTGameItem(equipment.get(EquipmentListener.NECKLACE_SLOT)) : null;
	}

	public static class PlayerSkill {

		private int level;
		private long xp;

		public PlayerSkill(int level, long xp){
			this.level = level;
			this.xp = xp;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public long getXP() {
			return xp;
		}

		public void setXP(long xp) {
			this.xp = xp;
		}

	}

	public Scoreboard getScoreboard(){
		return player.getScoreboard();
	}

	public void setScoreboard(Scoreboard scoreboard){
		player.setScoreboard(scoreboard);
	}

	public PlayerInventory getInventory(){
		return player.getInventory();
	}

	public Location getLocation(){
		return player.getLocation();
	}

	public World getWorld(){
		return player.getWorld();
	}

	public UUID getUniqueId(){
		return player.getUniqueId();
	}

	public String getName(){
		return player.getName();
	}

	public Player getPlayerObject(){
		return player;
	}

}
