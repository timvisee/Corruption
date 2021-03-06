package com.mcdr.corruption.entity.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

import com.mcdr.corruption.ability.Ability;
import com.mcdr.corruption.config.GlobalConfig.BossParam;
import com.mcdr.corruption.drop.Roll;
import com.mcdr.corruption.entity.EquipmentSet;


public class BossData {
	private List<Ability> abilities = new ArrayList<Ability>();
	private List<Roll> rolls = new ArrayList<Roll>();
	private String name;
	private EntityType entityType;
	private EquipmentSet bossEquipment = null;
	private List<BossImmunity> immunities;
	private List<Biome> biomes;
	private double chance;
	private double chanceFromSpawner;
	private double healthCoef;
	private double damageCoef;
	private double expCoef;
	private double maxSpawnLevel;
	private double heroesXPBonus;
	private int mcMMOXPBonus = 0;	
	private boolean useHealthAsMultiplier;
	private boolean useDamageAsMultiplier;
	private boolean useExperienceAsMultiplier;
	
	public BossData(String name, EntityType entityType) {
		this.name = name;
		this.entityType = entityType;
		setStatsMultipliers(BossParam.USE_HEALTH_AS_MULTIPLIER.getValue(), BossParam.USE_DAMAGE_AS_MULTIPLIER.getValue(), BossParam.USE_EXPERIENCE_AS_MULTIPLIER.getValue());
		immunities = new ArrayList<BossImmunity>();
		biomes = new ArrayList<Biome>();
	}
	
	public void AddAbility(Ability ability) {
		abilities.add(ability);
	}
	
	public void AddRoll(Roll roll) {
		rolls.add(roll);
	}
	
	public List<Ability> getAbilities() {
		return abilities;
	}
	
	public List<Roll> getRolls() {
		return rolls;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public double getChance() {
		return chance;
	}
	
	public double getChanceFromSpawner() {
		return chanceFromSpawner;
	}
	
	public double getHealthCoef() {
		return healthCoef;
	}
	
	public double getDamageCoef() {
		return damageCoef;
	}	
	
	public double getExpCoef() {
		return expCoef;
	}
	
	public double getMaxSpawnLevel(){
		return maxSpawnLevel;
	}
	
	public int getMCMMOXPBonus(){
		return mcMMOXPBonus;
	}
	
	public double getHeroesXPBonus(){
		return heroesXPBonus;
	}
	
	public List<Biome> getBiomes(){
		return biomes;
	}
	
	public boolean useHealthMultiplier(){
		return useHealthAsMultiplier;
	}
	
	public boolean useDamageMultiplier(){
		return useDamageAsMultiplier;
	}
	
	public boolean useExperienceMultiplier(){
		return useExperienceAsMultiplier;
	}
	
	public void setStatsMultipliers(boolean health, boolean damage, boolean experience){
		useHealthAsMultiplier = health;
		useDamageAsMultiplier = damage;
		useExperienceAsMultiplier = experience;
	}
	
	public void setSpawnData(double chance, double chanceFromSpawner, double maxSpawnLevel) {
		this.chance = chance;
		this.chanceFromSpawner = chanceFromSpawner;
		this.maxSpawnLevel = maxSpawnLevel;
	}
	
	public void setStatData(double healthCoef, double damageCoef, double expCoef) {
		this.healthCoef = healthCoef;
		this.damageCoef = damageCoef;
		this.expCoef = expCoef;
	}
	
	public void setMCMMOXPBonus(int bonus){
		mcMMOXPBonus = bonus;
	}
	
	public void setHeroesXPBonus(double bonus){
		heroesXPBonus = bonus;
	}
	
	public void setBiomes(List<Biome> biomes){
		this.biomes = biomes;
	}
	
	public void setEquipment(EquipmentSet eqS){
		bossEquipment = eqS;
	}
	
	public boolean hasEquipment(){
		return !bossEquipment.isEmpty();
	}
	
	public EntityEquipment setRandomEquipment(LivingEntity e){
		return bossEquipment.setRandomEquipment(e);
	}
	
	public void setImmunity(String immunityName, boolean isEnabled){
		for(BossImmunity immunity : BossImmunity.values()){
			if(isEnabled && immunityName.equals(immunity.getNode())){
				immunities.add(immunity);				
			}
		}
	}
	
	public List<BossImmunity> getImmunities(){
		return immunities;
	}

	public enum BossImmunity {
		ATTACK_IMMUNE {@Override public String getNode() {return "Attack";}},
		PROJECTILE_IMMUNE {@Override public String getNode() {return "Projectile";}},
		BLOCK_EXPLOSION_IMMUNE {@Override public String getNode() {return "BlockExplosion";}},
		ENTITY_EXPLOSION_IMMUNE {@Override public String getNode() {return "EntityExplosion";}},
		FIRE_IMMUNE {@Override public String getNode() {return "Fire";}},
		LAVA_IMMUNE {@Override public String getNode() {return "Lava";}},
		ENCHANT_FIRETICK_IMMUNE {@Override public String getNode() {return "EnchantFireTick";}},
		ENVIRONMENTAL_FIRETICK_IMMUNE {@Override public String getNode() {return "EnvironmentalFireTick";}},
		FALL_IMMUNE {@Override public String getNode() {return "Fall";}},
		CONTACT_IMMUNE {@Override public String getNode() {return "Contact";}},
		DROWNING_IMMUNE {@Override public String getNode() {return "Drowning";}},
		LIGHTNING_IMMUNE {@Override public String getNode() {return "Lightning";}},
		SUFFOCATION_IMMUNE {@Override public String getNode() {return "Suffocation";}},
		MAGIC_IMMUNE {@Override public String getNode() {return "Magic";}},
		POISON_IMMUNE {@Override public String getNode() {return "Poison";}};
		
		private BossImmunity() {
			
		}
		
		public abstract String getNode();
	}
}
