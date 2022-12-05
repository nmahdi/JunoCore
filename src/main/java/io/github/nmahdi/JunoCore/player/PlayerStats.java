package io.github.nmahdi.JunoCore.player;

import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import org.bukkit.Sound;

import java.util.HashMap;

public class PlayerStats extends HashMap<PlayerStat, Integer> {

	private GamePlayer player;

	public PlayerStats(GamePlayer player){
		this.player = player;
		for(PlayerStat stat : PlayerStat.values()){
			put(stat, stat.getBaseValue());
		}
	}

	public void login(){
		if(player.hasHeldItem()){
			if(player.getHeldItem().isHandEquipable()){
				player.equip(player.getHeldItem(), player.getNBTHeldItem());
			}
		}
		if(player.hasHelmet()){
			if(player.getHelmet().isArmor()){
				player.equip(player.getHelmet(), player.getNBTHelmet());
			}
		}
		if(player.hasChestplate()){
			if(player.getChestplate().isArmor()){
				player.equip(player.getChestplate(), player.getNBTChestplate());
			}
		}
		if(player.hasLeggings()){
			if(player.getLeggings().isArmor()){
				player.equip(player.getLeggings(), player.getNBTLeggings());
			}
		}
		if(player.hasBoots()){
			if(player.getBoots().isArmor()){
				player.equip(player.getBoots(), player.getNBTBoots());
			}
		}
		if(player.hasCape()){
			if(player.getCape().isEquipment()){
				player.equip(player.getCape(), player.getNBTCape());
			}
		}
		if(player.hasBracelet()){
			if(player.getBracelet().isEquipment()){
				player.equip(player.getBracelet(), player.getNBTBracelet());
			}
		}
		if(player.hasRing()){
			if(player.getRing().isEquipment()){
				player.equip(player.getRing(), player.getNBTRing());
			}
		}
		if(player.hasHeadband()){
			if(player.getHeadband().isEquipment()){
				player.equip(player.getHeadband(), player.getNBTHeadband());
			}
		}
		if(player.hasNecklace()){
			if(player.getNecklace().isEquipment()){
				player.equip(player.getNecklace(), player.getNBTNecklace());
			}
		}
		setHealth(getMaxHealth());
		setMana(getMaxMana());
	}

	public void updateStats(){
		if(getHealth() > getMaxHealth()) setHealth(getMaxHealth());
		if(getMana() > getMaxMana()) setMana(getMaxMana());

		setHealthRegen((int)((float)getMaxHealth()/100f));
		setManaRegen((int)((float)getMaxMana()/100f));

		float walkSpeed = ((float)getSpeed()/100)*0.2f;
		if(player.getPlayerObject().getWalkSpeed() != walkSpeed) player.getPlayerObject().setWalkSpeed(walkSpeed);
	}

	public int getHealth(){
		return get(PlayerStat.Health);
	}

	/**
	 * Sets the player's health & updates the Player health visually.
	 * If the amount entered is higher than the player's max health
	 * then the player's health is set to it's max health
	 */
	public void setHealth(int health){
		if(health < 0){
			player.kill();
			return;
		}
		put(PlayerStat.Health, Math.min(health, getMaxHealth()));
		player.getPlayerObject().setHealth((((double)getHealth()/(double)getMaxHealth())*10)*2);
	}

	public void plusHealth(int amount){
		setHealth(getHealth()+amount);
	}

	public void minusHealth(int amount){
		player.getPlayerObject().playSound(player.getPlayerObject(), Sound.ENTITY_PLAYER_HURT, 5f, 1f);
		setHealth(getHealth()-amount);
	}

	public int getMaxHealth(){
		return get(PlayerStat.MaxHealth);
	}

	/**
	 * @return The amount of health a player should regen every 20 ticks(1 second)
	 */
	public int getHealthRegen(){
		return get(PlayerStat.HealthRegen);
	}

	public void setHealthRegen(int regen){
		put(PlayerStat.HealthRegen, regen);
	}

	public int getMana(){
		return get(PlayerStat.Mana);
	}

	/**
	 * Sets the player's mana & updates the Player hunger(mana) visually.
	 * If the amount entered is higher than the player's max mana
	 * then the player's mana is set to it's max mana
	 */
	public void setMana(int mana){
		if(mana > getMaxMana()){
			put(PlayerStat.Mana, getMaxMana());
		}else put(PlayerStat.Mana, Math.max(mana, 0));
		player.getPlayerObject().setFoodLevel((int)(((double)getMana()/(double)getMaxMana())*10)*2);
	}

	public void plusMana(int amount){
		setMana(getMana()+amount);
	}

	public void minusMana(int amount){
		setMana(getMana()-amount);
	}

	public int getMaxMana(){
		return get(PlayerStat.MaxMana);
	}

	/**
	 * @return The amount of mana a player should regen every 20 ticks(1 second)
	 */
	public int getManaRegen(){
		return get(PlayerStat.ManaRegen);
	}

	public void setManaRegen(int regen){
		put(PlayerStat.ManaRegen, regen);
	}

	public int getDefense(){
		return get(PlayerStat.Defense);
	}

	public int getFireElement(){
		return get(PlayerStat.FireElement);
	}

	public int getWaterElement(){
		return get(PlayerStat.WaterElement);
	}

	public int getAirElement(){
		return get(PlayerStat.AirElement);
	}

	public int getEarthElement(){
		return get(PlayerStat.EarthElement);
	}

	public int getLightningElement(){
		return get(PlayerStat.LightningElement);
	}

	public int getIceElement(){
		return get(PlayerStat.IceElement);
	}

	public int getSpeed(){
		return get(PlayerStat.Speed);
	}

	public int getDamage(){
		return get(PlayerStat.Damage);
	}

	public int getStrength(){
		return get(PlayerStat.Strength);
	}

	public int getCritDamage(){
		return get(PlayerStat.CritDamage);
	}

	public int getCritChance(){
		return get(PlayerStat.CritChance);
	}

	public int getAttackSpeed(){
		return get(PlayerStat.AttackSpeed);
	}

	public int getLuck(){
		return get(PlayerStat.Luck);
	}

	public int getFortune(){
		return get(PlayerStat.Fortune);
	}

	public int getHarvestingSpeed(){
		return get(PlayerStat.HarvestingSpeed);
	}

	public int getFishingSpeed(){
		return get(PlayerStat.FishingSpeed);
	}

}
