package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.TargetableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class EntityDamageByEntityListener implements Listener {
	
	DataBaseManager db;
	FileConfiguration f;
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (!event.getDamager().hasMetadata("NPC")) {
				
				Player damager = (Player) event.getDamager();
				db = new DataBaseManager(RPGPlus.inst());
				
				Map<Player, FileConfiguration> mp = db.getFileMap();
				f = mp.get(damager);
				
				int strength = f.getInt("Skills.Strength");
				
				double damage = event.getDamage();
				
				if (strength == 60) {
					event.setDamage(damage + 3);
				} else if (strength >= 40) {
					event.setDamage(damage + 2);
				} else if (strength >= 20) {
					event.setDamage(damage + 1);
				}
				
				if (db.getAbilityMap(damager).containsKey(AbilityType.LIGHT)) {
					Ability ability = db.getAbilityMap(damager).get(AbilityType.LIGHT);
					if (ability instanceof TargetableAbility && ability.getSkillType().equals(SkillType.STRENGTH) && event.getEntity() instanceof LivingEntity) {
						((TargetableAbility) ability).onTarget((LivingEntity) event.getEntity(), damager, 1);
					}
				}
			}
		}
		if (event.getDamager() instanceof Arrow) {
			if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
				
				Player damager = (Player) ((Arrow) event.getDamager()).getShooter();
				if (!damager.hasMetadata("NPC")) {
					if (damager.getItemInHand().getType() != Material.STICK) {
						db = new DataBaseManager(RPGPlus.inst());
						
						Map<Player, FileConfiguration> mp = db.getFileMap();
						f = mp.get(damager);
						
						int dexterity = f.getInt("Skills.Dexterity");
						
						double damage = event.getDamage();
						
						if (dexterity == 60) {
							event.setDamage(damage + 3);
						} else if (dexterity >= 40) {
							event.setDamage(damage + 2);
						} else if (dexterity >= 20) {
							event.setDamage(damage + 1);
						}
					} else {
						if (event.getEntity() instanceof Monster && event.getEntity().getFireTicks() <= 1) {
							RPGPlus.experienceHandler.handleXp(damager, SkillType.WISDOM, 1);
						}
					}
					
				}
			}
		}
		
		if (event.getDamager() instanceof Fireball) {
			Fireball fireball = (Fireball) event.getDamager();
			if (fireball.getShooter() instanceof Player) {
				Player damager = (Player) fireball.getShooter();
				RPGPlus.experienceHandler.handleXp(damager, SkillType.WISDOM, 1);
			}
		}
		
		
	}
	
}
