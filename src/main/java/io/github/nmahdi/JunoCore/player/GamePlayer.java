package io.github.nmahdi.JunoCore.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.ItemBuffAbility;
import io.github.nmahdi.JunoCore.item.items.set.SetEffect;
import io.github.nmahdi.JunoCore.item.modifiers.abilities.SetAbility;
import io.github.nmahdi.JunoCore.item.modifiers.stats.CollectionItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.collection.Collection;
import io.github.nmahdi.JunoCore.player.listeners.PlayerInventoryListener;
import io.github.nmahdi.JunoCore.player.display.ActionBar;
import io.github.nmahdi.JunoCore.player.display.ScoreboardManager;
import io.github.nmahdi.JunoCore.player.stats.DamageType;
import io.github.nmahdi.JunoCore.player.stats.Element;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import it.unimi.dsi.fastutil.Hash;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GamePlayer{

	public static final String PROJECTILE_META = "PROJECTILE_DAMAGE";

	private HologramManager hologramManager;
	private ItemManager itemManager;

	private Player player;

	//Stats
	private long coins;
	private PlayerStats stats;
	private HashMap<Skill, PlayerSkill> skills = new HashMap<>();

	//Collections
	private HashMap<Collection, Long> collections = new HashMap<>();

	//Equipment
	private StatItem heldItem;

	private StatItem helmet;
	private StatItem chestplate;
	private StatItem leggings;
	private StatItem boots;

	private StatItem bracelet;
	private StatItem ring;
	private StatItem headband;
	private StatItem necklace;
	private StatItem cape;

	//TEMPORARY
	public ArrayList<GameItem> equiped = new ArrayList<>();

	public HashMap<Integer, ItemStack> equipment = new HashMap<>();

	//Abilities
	private final ArrayList<SetEffect> activeSetEffects = new ArrayList<>();

	//private VirtualInventory virtualInventory;
	private Inventory storage;

	//Display & Tick
	private ActionBar actionBar;
	private int taskID;

	public GamePlayer(JCore main, Player player){
		this.hologramManager = main.getHologramsManager();
		this.itemManager = main.getItemManager();
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
			StatItem temp = (StatItem) itemManager.getItem(getNBTHeldItem().getID());
			if(temp != null && ItemType.isHandEquipable(temp)) {
				heldItem = temp;
			}
		}

		if(getNBTHelmet() != null && getNBTHelmet().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTHelmet().getID());
			if(temp != null && temp.getItemType() == ItemType.Helmet) {
				helmet = temp;
			}
		}

		if(getNBTChestplate() != null && getNBTChestplate().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTChestplate().getID());
			if(temp != null && temp.getItemType() == ItemType.Chestplate) {
				chestplate = temp;
			}
		}

		if(getNBTLeggings() != null && getNBTLeggings().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTLeggings().getID());
			if(temp != null && temp.getItemType() == ItemType.Leggings) {
				leggings = temp;
			}
		}

		if(getNBTBoots() != null && getNBTBoots().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTBoots().getID());
			if(temp != null && temp.getItemType() == ItemType.Boots) {
				boots = temp;
			}
		}

		if(getNBTCape() != null && getNBTCape().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTCape().getID());
			if(temp != null && temp.getItemType() == ItemType.Cape) {
				cape = temp;
			}
		}

		if(getNBTBracelet() != null && getNBTBracelet().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTBracelet().getID());
			if(temp != null && temp.getItemType() == ItemType.Bracelet) {
				bracelet = temp;
			}
		}

		if(getNBTRing() != null && getNBTRing().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTRing().getID());
			if(temp != null && temp.getItemType() == ItemType.Ring) {
				ring = temp;
			}
		}

		if(getNBTHeadband() != null && getNBTHeadband().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTHeadband().getID());
			if(temp != null && temp.getItemType() == ItemType.Headband) {
				headband = temp;
			}
		}

		if(getNBTNecklace() != null && getNBTNecklace().hasID()){
			StatItem temp = (StatItem) itemManager.getItem(getNBTNecklace().getID());
			if(temp != null && temp.getItemType() == ItemType.Necklace) {
				necklace = temp;
			}
		}

		stats.login();

		for(Collection collection : Collection.values()){
			collections.put(collection, 0L);
		}

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
				for(int i = 0; i < activeSetEffects.size(); i++){
					activeSetEffects.get(i).tick(this);
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
			if(getHeldItem() instanceof Runeable) {
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
	public void equip(StatItem item, NBTGameItem gameItem){
		if(item == null || gameItem == null) return;

		updateEquipment(item, item.getItemType());
		//updateActiveAbilities(item, false);

		for(PlayerStat stat : PlayerStat.values()){
			double value = 0;

			if(item.hasStat(stat)){
				value+=item.getStat(stat);
			}

			//Runes
			if(item instanceof Runeable) {
				Rune rune = Rune.getRune(stat);
				if (rune != null && gameItem.getRunes().containsKey(rune))
					value += rune.getAmount() * gameItem.getRunes().get(rune);
			}

			//Abilities & Buffs
			for(SetEffect ability : activeSetEffects){
				if(ability instanceof ItemBuffAbility abilityBuff){

					/*if(item == abilityBuff.getWeapon()) {
						HashMap<PlayerStat, Integer> buffs = abilityBuff.getBuff(item, gameItem);
						if(buffs.containsKey(stat))
							value += buffs.get(stat);
					} */
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
	public void unequip(StatItem item, NBTGameItem gameItem){
		if(item == null || gameItem == null) return;

		updateEquipment(null, item.getItemType());
		updateSetEffects(item, true);

		for(PlayerStat stat : PlayerStat.values()){

			double value = 0;
			if(item.hasStat(stat)){
				value+=item.getStat(stat);
			}

			//Runes
			if(item instanceof Runeable) {
				Rune rune = Rune.getRune(stat);
				if (rune != null && gameItem.getRunes().containsKey(rune))
					value += rune.getAmount() * gameItem.getRunes().get(rune);
			}

			//Abilities & Buffs
			for(SetEffect ability : activeSetEffects){
				if(ability instanceof ItemBuffAbility abilityBuff){

					/*if(item == abilityBuff.getWeapon()) {
						HashMap<PlayerStat, Integer> buffs = abilityBuff.getBuff(item, gameItem);
						if(buffs.containsKey(stat))
							value += buffs.get(stat);
					} */
				}
			}

			if(value != 0) {
				stats.put(stat, stats.get(stat)-value);
			}

		}

		stats.updateStats();
	}

	private void updateEquipment(StatItem item, ItemType itemType){
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

	private void updateSetEffects(GameItem item, boolean removing){
		if(removing){

			equiped.remove(item);

			if(item instanceof SetAbility ability){
				if(activeSetEffects.contains(ability.getSet())){

					activeSetEffects.remove(ability.getSet().onUnequip(this));
					updateInventory();
				}
			}

		}else{
			if(item instanceof SetAbility ability) {

				equiped.add(item);

				if (equiped.containsAll(ability.getSet().getItems())) {

					activeSetEffects.add(ability.getSet().onEquip(this));
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

			GameItem item = itemManager.getItem(gameItem.getID());
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

	public HashMap<Integer, ItemStack> addItem(ItemStack itemStack){
		HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(itemStack);

		//TODO: Add leftovers to virtual inventory;

		return leftOver;
	}

	public void addItem(GameItem item, int amount){
		HashMap<Integer, ItemStack> leftOver = addItem(ItemBuilder.buildGameItem(item, amount));

		if(item instanceof CollectionItem collectionItem) {
			int collectionAmount = amount - leftOver.get(0).getAmount();
			collections.put(collectionItem.getCollection(), collections.get(collectionItem.getCollection())+(collectionAmount+collectionItem.getCollectionAmount()));
		}

	}

	public boolean hasHeldItem(){
		return heldItem != null;
	}

	public StatItem getHeldItem(){
		return heldItem;
	}

	public NBTGameItem getNBTHeldItem(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getItemInMainHand()) ? new NBTGameItem(player.getInventory().getItemInMainHand()) : null;
	}

	public boolean hasHelmet(){
		return helmet != null;
	}

	public StatItem getHelmet(){
		return helmet;
	}

	public NBTGameItem getNBTHelmet(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getHelmet()) ? new NBTGameItem(player.getInventory().getHelmet()) : null;
	}

	public boolean hasChestplate(){
		return chestplate != null;
	}

	public StatItem getChestplate(){
		return chestplate;
	}

	public NBTGameItem getNBTChestplate(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getChestplate()) ? new NBTGameItem(player.getInventory().getChestplate()) : null;
	}

	public boolean hasLeggings(){
		return leggings != null;
	}

	public StatItem getLeggings() {
		return leggings;
	}

	public NBTGameItem getNBTLeggings(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getLeggings()) ? new NBTGameItem(player.getInventory().getLeggings()) : null;
	}

	public boolean hasBoots(){
		return boots != null;
	}

	public StatItem getBoots() {
		return boots;
	}

	public NBTGameItem getNBTBoots(){
		return !InventoryHelper.isAirOrNull(player.getInventory().getBoots()) ? new NBTGameItem(player.getInventory().getBoots()) : null;
	}

	public boolean hasCape(){
		return cape != null;
	}

	public StatItem getCape() {
		return cape;
	}

	public NBTGameItem getNBTCape(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.CAPE_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.CAPE_SLOT)) : null;
	}

	public boolean hasBracelet(){
		return bracelet != null;
	}

	public StatItem getBracelet() {
		return bracelet;
	}

	public NBTGameItem getNBTBracelet(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.BRACELET_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.BRACELET_SLOT)) : null;
	}

	public boolean hasRing(){
		return ring != null;
	}

	public StatItem getRing(){
		return ring;
	}

	public NBTGameItem getNBTRing(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.RING_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.RING_SLOT)) : null;
	}

	public boolean hasHeadband(){
		return headband != null;
	}

	public StatItem getHeadband(){
		return headband;
	}

	public NBTGameItem getNBTHeadband(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.HEADBAND_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.HEADBAND_SLOT)) : null;
	}

	public boolean hasNecklace(){
		return necklace != null;
	}

	public StatItem getNecklace() {
		return necklace;
	}

	public NBTGameItem getNBTNecklace(){
		return !InventoryHelper.isAirOrNull(equipment.get(PlayerInventoryListener.NECKLACE_SLOT)) ? new NBTGameItem(equipment.get(PlayerInventoryListener.NECKLACE_SLOT)) : null;
	}

	/*public ArrayList<EquipmentAbility> getActiveAbilities() {
		return activeAbilities;
	}*/

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

	public void sendMessage(String message){
		player.sendMessage(message);
	}

	public Player getPlayerObject(){
		return player;
	}

}
