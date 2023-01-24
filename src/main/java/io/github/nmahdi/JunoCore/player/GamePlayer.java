package io.github.nmahdi.JunoCore.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ability.equipment.AppliesWeaponBuff;
import io.github.nmahdi.JunoCore.item.ability.equipment.EquipmentAbility;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.listeners.PlayerInventoryListener;
import io.github.nmahdi.JunoCore.player.display.ActionBar;
import io.github.nmahdi.JunoCore.player.display.ScoreboardManager;
import io.github.nmahdi.JunoCore.player.stats.DamageType;
import io.github.nmahdi.JunoCore.player.stats.Element;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.papermc.paper.math.Rotations;
import it.unimi.dsi.fastutil.Hash;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.EulerAngle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GamePlayer{

	public static final String PROJECTILE_META = "PROJECTILE_DAMAGE";

	private HologramManager hologramManager;

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

	private ArrayList<GameItem> abilityEquipment = new ArrayList<>();

	public HashMap<Integer, ItemStack> equipment = new HashMap<>();

	//Abilities
	private final ArrayList<EquipmentAbility> activeAbilities = new ArrayList<>();

	//private VirtualInventory virtualInventory;
	private Inventory storage;

	//Display & Tick
	private ActionBar actionBar;
	private int taskID;

	public GamePlayer(JCore main, Player player){
		this.hologramManager = main.getHologramsManager();
		this.player = player;
		this.stats = new PlayerStats(this);
		this.actionBar = new ActionBar(main, this);
		this.storage = Bukkit.createInventory(null, 54, getName() + "'s Storage");
	}

	public void loadData(ResultSet data) throws SQLException {
		this.coins = data.getLong("Coins");
		for(Skill skill : Skill.values()){
			long xp = data.getLong(skill.getSQLName());
			this.skills.put(skill, new PlayerSkill(skill, Skill.getCurrentLevel(xp), xp));
		}
	}

	public void newPlayer(){
		this.coins = 0;
		for(Skill skill : Skill.values()){
			this.skills.put(skill, new PlayerSkill(skill, 0, 0));
		}
	}

	public void login(JCore main, ScoreboardManager scoreboardManager){
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false, false));
		scoreboardManager.set(this);

		if(player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING){
			CraftingInventory inventory = (CraftingInventory) player.getOpenInventory().getTopInventory();
			for(Map.Entry<Integer, ItemStack> items : equipment.entrySet()){
				inventory.setItem(items.getKey(), items.getValue());
			}
			inventory.setItem(PlayerInventoryListener.CAPE_SLOT, equipment.get(PlayerInventoryListener.CAPE_SLOT));
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

		tick(main, scoreboardManager);
	}

	public void logout(JCore main){
		main.getServer().getScheduler().cancelTask(taskID);
	}


	private int updateTicks = 0;

	private void tick(JCore main, ScoreboardManager scoreboardManager){
		this.taskID = main.getServer().getScheduler().scheduleAsyncRepeatingTask(main, () -> {
			updateTicks++;

			//Tick amount might change
			if(updateTicks % 5 == 0) {
				//Display
				actionBar.send();
				scoreboardManager.update(this);
			}

			if(updateTicks % 5 == 0) {
				//Regen
				if (stats.getHealth() < stats.getMaxHealth()) {
					stats.plusHealth(stats.getHealthRegen());
				}
				if (stats.getMana() < stats.getMaxMana()) {
					stats.plusMana(stats.getManaRegen());
				}

				//Abilities
				for(int i = 0; i < activeAbilities.size(); i++){
					activeAbilities.get(i).run(this);
				}
			}

			if(updateTicks == 20) updateTicks = 0;


		}, 0, 1);
	}

	public int getDamage(Random random){
		double damage = stats.getDamage();
		double strength = stats.getStrength();

		if(hasHeldItem()){
			//Runes
			if(getHeldItem().canApplyRunes()) {
				if (getNBTHeldItem().getRunes().containsKey(Rune.Strength))
					strength += Rune.Strength.getAmount() * getNBTHeldItem().getRunes().get(Rune.Strength);
			}
		}

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
	 * Damage's the player and applies knockback.
	 */
	public void damage(NPC source, double knockbackStrength, int damage, DamageType damageType, Element element){
		this.damage(damage, damageType, element);
		if(source != null){
			player.setVelocity(source.getStoredLocation().getDirection().multiply(knockbackStrength).setY(knockbackStrength/5));
		}
	}

	/**
	 * Equips an item and updates the stats.
	 */
	public void equip(GameItem item, NBTGameItem gameItem){
		if(item == null || gameItem == null) return;

		updateEquipment(item, item.getItemType());
		updateActiveAbilities(item, false);

		for(PlayerStat stat : PlayerStat.values()){
			int value = 0;

			if(item.hasStat(stat)){
				value+=Integer.parseInt(item.getStat(stat));
			}

			//Runes
			if(item.canApplyRunes()) {
				Rune rune = Rune.getRune(stat);
				if (rune != null && gameItem.getRunes().containsKey(rune))
					value += rune.getAmount() * gameItem.getRunes().get(rune);
			}

			//Abilities & Buffs
			for(EquipmentAbility ability : activeAbilities){
				if(ability instanceof AppliesWeaponBuff abilityBuff){

					if(item == abilityBuff.getWeapon()) {
						HashMap<PlayerStat, Integer> buffs = abilityBuff.getBuff(item, gameItem);
						if(buffs.containsKey(stat))
							value += buffs.get(stat);
					}
				}
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
		updateActiveAbilities(item, true);

		for(PlayerStat stat : PlayerStat.values()){

			int value = 0;
			if(item.hasStat(stat)){
				value+=Integer.parseInt(item.getStat(stat));
			}

			//Runes
			if(item.canApplyRunes()) {
				Rune rune = Rune.getRune(stat);
				if (rune != null && gameItem.getRunes().containsKey(rune))
					value += rune.getAmount() * gameItem.getRunes().get(rune);
			}

			//Abilities & Buffs
			for(EquipmentAbility ability : activeAbilities){
				if(ability instanceof AppliesWeaponBuff abilityBuff){

					if(item == abilityBuff.getWeapon()) {
						HashMap<PlayerStat, Integer> buffs = abilityBuff.getBuff(item, gameItem);
						if(buffs.containsKey(stat))
							value += buffs.get(stat);
					}
				}
			}

			if(value != 0) {
				stats.put(stat, stats.get(stat)-value);
			}

		}

		stats.updateStats();
	}

	private void updateEquipment(GameItem item, ItemType itemType){
		switch (itemType) {
			case Helmet -> helmet = item;
			case Chestplate -> chestplate = item;
			case Leggings -> leggings = item;
			case Boots -> boots = item;
			case Bracelet -> bracelet = item;
			case Ring -> ring = item;
			case Necklace -> necklace = item;
			case Headband -> headband = item;
			case Cape -> cape = item;
			case Sword, Bow, Wand, Pickaxe, Axe, Shovel, Hoe, Shears -> heldItem = item;
		}
	}

	//TODO: Fix bug where held item stats stay when a set effect is removed.
	private void updateActiveAbilities(GameItem item, boolean removing){
		if(removing){

			if(item.hasEquipmentAbility()){
				if(activeAbilities.contains(item.getEquipmentAbility())){

					activeAbilities.remove(item.getEquipmentAbility().onUnEquip(this));
					updateInventory();
				}
			}

			abilityEquipment.remove(item);
		}else{
			abilityEquipment.add(item);
			if(item.hasEquipmentAbility()) {

				if (abilityEquipment.containsAll(item.getEquipmentAbility().getSet())) {

					activeAbilities.add(item.getEquipmentAbility().onEquip(this));
					updateInventory();

				}

			}
		}

	}

	public void updateInventory(){
		for(ItemStack stack : player.getInventory()){

			if(InventoryHelper.isAirOrNull(stack)) continue;

			NBTGameItem gameItem = new NBTGameItem(stack);
			if(!gameItem.hasID()) continue;

			GameItem item = GameItem.getItem(gameItem.getID());
			if(item == null) continue;

			stack.setItemMeta(ItemBuilder.updateMeta(this, item, gameItem));

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

	public void addItem(GameItem item, int amount){

		HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(ItemBuilder.buildGameItem(item, amount));

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
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.CAPE_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.CAPE_SLOT)) : null;
	}

	public boolean hasBracelet(){
		return bracelet != null;
	}

	public GameItem getBracelet() {
		return bracelet;
	}

	public NBTGameItem getNBTBracelet(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.BRACELET_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.BRACELET_SLOT)) : null;
	}

	public boolean hasRing(){
		return ring != null;
	}

	public GameItem getRing(){
		return ring;
	}

	public NBTGameItem getNBTRing(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.RING_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.RING_SLOT)) : null;
	}

	public boolean hasHeadband(){
		return headband != null;
	}

	public GameItem getHeadband(){
		return headband;
	}

	public NBTGameItem getNBTHeadband(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.HEADBAND_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.HEADBAND_SLOT)) : null;
	}

	public boolean hasNecklace(){
		return necklace != null;
	}

	public GameItem getNecklace() {
		return necklace;
	}

	public NBTGameItem getNBTNecklace(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.NECKLACE_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.NECKLACE_SLOT)) : null;
	}

	public ArrayList<EquipmentAbility> getActiveAbilities() {
		return activeAbilities;
	}

	public void gainXP(Skill skill, int amount){
		getSkill(skill).addXP(amount);
		player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
		actionBar.gainXP(skill, amount);
	}

	public ActionBar getActionBar() {
		return actionBar;
	}

	public class PlayerSkill {

		private Skill skill;
		private int level;
		private long xp;

		public PlayerSkill(Skill skill, int level, long xp){
			this.skill = skill;
			this.level = level;
			this.xp = xp;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public void levelUp(){
			player.sendMessage(Component.text("----------------------------------").color(NamedTextColor.YELLOW));
			player.sendMessage(Component.text("Skill Level Up!").color(NamedTextColor.AQUA));
			player.sendMessage(Component.text(skill.getDisplayName() + " " + level + " -----> " + (level+1)).color(NamedTextColor.GOLD));
			player.sendMessage(Component.text("----------------------------------").color(NamedTextColor.YELLOW));
			level++;
		}

		public long getXP() {
			return xp;
		}

		public void setXP(long xp) {
			this.xp = xp;
		}

		public void addXP(long amount){
			if(this.xp+amount >= Skill.getXPRequirement(level)){
				levelUp();
			}
			this.xp+=amount;
		}

	}

	public Inventory getStorage() {
		return storage;
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
