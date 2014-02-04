package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.Github.Malatak1.RPGPlus.AbilityCastHandler;
import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.ProjectileAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Database.CooldownManager;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class EntityShootBowListener implements Listener {
	
	DataBaseManager db;
	CooldownManager cdm = new CooldownManager();
	AbilityCastHandler ach = new AbilityCastHandler();
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		if (event.getBow() != null && event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow) {
			db = new DataBaseManager(RPGPlus.inst());
			
			Player p = (Player) event.getEntity();
			Arrow arrow = (Arrow) event.getProjectile();
			
			Ability ability = getCorrectAbility(p, true, SkillType.DEXTERITY);
			if (ability instanceof ProjectileAbility) {
				ach.projectileAbility(p, arrow, (ProjectileAbility) ability, 1);
			}
		}
	}
	
	private Ability getCorrectAbility(Player p, boolean rightClicked, SkillType type) {
		Map<AbilityType, Ability> playerMap = db.getAbilityMap(p);
		if (!p.isSneaking()) {
			if (!rightClicked) {
				Bukkit.getLogger().info("Light");
				if (hasAbilitySelected(p, AbilityType.LIGHT, type)) {
					return playerMap.get(AbilityType.LIGHT);
				}
			} else {
				if (hasAbilitySelected(p, AbilityType.MEDIUM, type)) {
					return playerMap.get(AbilityType.MEDIUM);
				}
			}
		}
		else {
			if (!rightClicked) {
				if (hasAbilitySelected(p, AbilityType.HEAVY, type)) {
					return playerMap.get(AbilityType.HEAVY);
				}
			} else {
				if (hasAbilitySelected(p, AbilityType.ULTIMATE, type)) {
					return playerMap.get(AbilityType.ULTIMATE);
				}
			}
		}
		return null;
	}
	
	private boolean hasAbilitySelected(Player p, AbilityType type, SkillType skill) {
		Map<AbilityType, Ability> playerMap = db.getAbilityMap(p);
		if(playerMap.containsKey(type)) {
			Ability ability = playerMap.get(type);
			if (ability.getSkillType().equals(skill)) {
				return true;
			}
		}
		return false;
	}
}
