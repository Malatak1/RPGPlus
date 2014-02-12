package com.Github.Malatak1.RPGPlus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

import com.Github.Malatak1.RPGPlus.Abilities.*;
import com.Github.Malatak1.RPGPlus.Database.CooldownManager;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class AbilityCastHandler {
	
	CooldownManager cdm = new CooldownManager();
	
	public void castAbility(Player p, CastableAbility ability, int power) {
		power += (getBasePower(p, ability) - 1);
		if (ability instanceof CooldownAbility) {
			CooldownAbility cAbility = (CooldownAbility) ability;
			if (manaEvaluator(ability) < p.getLevel() && staminaEvaluator(ability) < p.getExp()) {
				if (!cdm.getIsOnCooldown(p, cAbility)) {
					ability.cast(p, power);
					p.setLevel(p.getLevel() - manaEvaluator(ability));
					p.setExp(p.getExp() - staminaEvaluator(ability));
					cdm.addAbility(p, cAbility);
				} else {
					p.sendMessage(ChatColor.RED
							+ "That ability is on cooldown for "
							+ Math.round(cdm.getCooldownTime(p, cAbility))
							+ " more seconds");
				}
			}
		} else if (manaEvaluator(ability) < p.getLevel() && staminaEvaluator(ability) < p.getExp()) {
			ability.cast(p, power);
			p.setLevel(p.getLevel() - manaEvaluator(ability));
			p.setExp(p.getExp() - staminaEvaluator(ability));
		}
	}
	
	public void projectileAbility(Player p, Projectile projectile, ProjectileAbility ability, int power) {
		power += (getBasePower(p, ability) - 1);
		if (ability instanceof CooldownAbility) {
			CooldownAbility cAbility = (CooldownAbility) ability;
			if (manaEvaluator(ability) < p.getLevel() && staminaEvaluator(ability) < p.getExp()) {
				if (!cdm.getIsOnCooldown(p, cAbility)) {
					((ProjectileAbility) ability).onShoot(projectile, power);
					p.setLevel(p.getLevel() - manaEvaluator(ability));
					p.setExp(p.getExp() - staminaEvaluator(ability));
					cdm.addAbility(p, cAbility);
				} else {
					p.sendMessage(ChatColor.RED
							+ "That ability is on cooldown for "
							+ Math.round(cdm.getCooldownTime(p, cAbility))
							+ " more seconds");
				}
			}
		} else if (manaEvaluator(ability) < p.getLevel() && staminaEvaluator(ability) < p.getExp()) {
			((ProjectileAbility) ability).onShoot(projectile, power);
			p.setLevel(p.getLevel() - manaEvaluator(ability));
			p.setExp(p.getExp() - staminaEvaluator(ability));
		}
	}
	
	public void targetableAbility(Player p, LivingEntity target, TargetableAbility ability, int power) {
		power += (getBasePower(p, ability) - 1);
		if (ability instanceof CooldownAbility) {
			CooldownAbility cAbility = (CooldownAbility) ability;
			if (manaEvaluator(ability) < p.getLevel() && staminaEvaluator(ability) < p.getExp()) {
				if (!cdm.getIsOnCooldown(p, cAbility)) {
					ability.onTarget(target, p, power);
					p.setLevel(p.getLevel() - manaEvaluator(ability));
					p.setExp(p.getExp() - staminaEvaluator(ability));
					cdm.addAbility(p, cAbility);
				} else {
					p.sendMessage(ChatColor.RED
							+ "That ability is on cooldown for "
							+ Math.round(cdm.getCooldownTime(p, cAbility))
							+ " more seconds");
				}
			}
		} else if (manaEvaluator(ability) < p.getLevel() && staminaEvaluator(ability) < p.getExp()) {
			ability.onTarget(target, p, power);
			p.setLevel(p.getLevel() - manaEvaluator(ability));
			p.setExp(p.getExp() - staminaEvaluator(ability));
		}
	}
	
	public void onBlockAbility(Player p, BlockAbility ability) {
		
	}
	
	private int manaEvaluator(Ability ability) {
		if (ability instanceof ManaAbility) {
			return ((ManaAbility) ability).manaCost();
		} else return 0;
	}
	
	private float staminaEvaluator(Ability ability) {
		if (ability instanceof StaminaAbility) {
			return ((StaminaAbility) ability).staminaCost() / 100F;
		} else return 0;
	}
	
	private int getBasePower(Player p, Ability ability) {
		FileConfiguration f = PlayerDataManager.getPlayerData(p).getFile();
		return f.getInt("Abilities." + capitalize(ability.getSkillType().toString()) + "." + DataBaseManager.abilityToName(ability));
	}
	
	private static String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
}
