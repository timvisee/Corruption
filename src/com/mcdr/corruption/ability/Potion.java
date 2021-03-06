package com.mcdr.corruption.ability;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mcdr.corruption.entity.Boss;


public class Potion extends Ability {
	private int amplifier = 0;
	private int duration = 3;
	private String effect = "";
	private boolean targetSelf = false;
	protected double activationChance = 25.0;
	
	public Potion clone(){
		Potion pot = new Potion();
		copySettings(pot);
		pot.setAmplifier(this.amplifier);
		pot.setDuration(this.duration);
		pot.setEffect(this.effect);
		pot.setTarget(this.targetSelf?"self":"other");
		return pot;
	}
	
	/**
	 * Normal Execute
	 */
	public void Execute(LivingEntity livingEntity, Boss boss) {
		super.Execute(livingEntity, boss);
		if(effect != ""){
			PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(effect), duration, amplifier);
			if (targetSelf) {
				boss.getLivingEntity().addPotionEffect(potionEffect, true);
				sendAreaMessage(boss);
			} else {
				livingEntity.addPotionEffect(potionEffect, true);
				sendMessage(boss, livingEntity);
			}
			useCooldown(boss);
		}
	}
	
	public void setEffect(String effect){
		this.effect = effect;
	}
	
	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setTarget(String target){
		targetSelf = target.equalsIgnoreCase("self");
	}
}
