package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.AbilityCastHandler;
import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.TargetableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Database.CooldownManager;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;

public class EntityDamageByEntityListener implements Listener {

	DataBaseManager db;
	FileConfiguration f;
	CooldownManager cdm = new CooldownManager();
	AbilityCastHandler ach = new AbilityCastHandler();
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (!event.getDamager().hasMetadata("NPC")) {
				Player damager = (Player) event.getDamager();
				db = new DataBaseManager(RPGPlus.inst());

				f = PlayerDataManager.getPlayerData(damager).getFile();

				int strength = f.getInt("Skills.Strength");
				
				double damage = event.getDamage();

				if (strength == 60) {
					event.setDamage(damage + 3);
				} else if (strength >= 40) {
					event.setDamage(damage + 2);
				} else if (strength >= 20) {
					event.setDamage(damage + 1);
				}
				
				int power = 1;
				Ability ability = getCorrectAbility(damager, false, SkillType.STRENGTH);
				if (ability instanceof TargetableAbility && event.getEntity() instanceof LivingEntity) {
					ach.targetableAbility(damager, (LivingEntity) event.getEntity(), (TargetableAbility) ability, power);
				}
//				if (!damager.isSneaking()) {
//					if (hasAbilitySelected(damager, AbilityType.LIGHT, SkillType.STRENGTH)) {
//						Ability ability = playerMap.get(AbilityType.LIGHT);
//						abilityTarget((LivingEntity) event.getEntity(), damager, ability, power);
//						}
//					} else if (hasAbilitySelected(damager, AbilityType.HEAVY, SkillType.STRENGTH)) {
//						Ability ability = playerMap.get(AbilityType.HEAVY);
//						abilityTarget((LivingEntity) event.getEntity(), damager, ability, power);
//				} 
			}
		}
		if (event.getDamager() instanceof Arrow) {
			if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
				Player damager = (Player) ((Arrow) event.getDamager()).getShooter();
				if (!damager.hasMetadata("NPC")) {
					if (!damager.getItemInHand().getType().equals(Material.STICK)) {
						db = new DataBaseManager(RPGPlus.inst());

						f = PlayerDataManager.getPlayerData(damager).getFile();
						
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
				if (event.getDamager().hasMetadata("Penetrating")) {
					if (event.getEntity() instanceof Damageable
							&& !event.getEntity().isDead()) {
						Entity entity = event.getEntity();
						if (entity.getType().equals(EntityType.SKELETON) || entity.getType().equals(EntityType.ZOMBIE)) {
							Monster m = (Monster) entity;
							ItemStack[] equipment = m.getEquipment()
									.getArmorContents();
							int armorValue = armorEvaluator(equipment);
							double damage = event.getDamage();
							if (armorValue > 2)
								entity.getWorld().playSound(entity.getLocation(), Sound.ANVIL_LAND, 1, 0);
								damage++;
							if (armorValue > 4)
								damage++;
							if (armorValue > 6)
								damage++;
							event.setDamage(damage);
						} else if (entity instanceof Player) {
							Player p = (Player) entity;
							ItemStack[] equipment = p.getInventory().getArmorContents();
							int armorValue = armorEvaluator(equipment);
							double damage = event.getDamage();
							if (armorValue > 2)
								entity.getWorld().playSound(entity.getLocation(), Sound.ANVIL_LAND, 1, 0);
								damage++;
							if (armorValue > 4)
								damage++;
							if (armorValue > 6)
								damage++;
							event.setDamage(damage);
						}
					}
				}
				if (damager instanceof Player && event.getEntity() instanceof Player) {
					Player target = (Player) event.getEntity();
					if (!damager.hasMetadata("NPC") && !target.hasMetadata("NPC")) {
						if (PartyManager.inSameParty(damager, target)) {
							event.setCancelled(true);
						}
					}
				}
			}
		}

		if (event.getDamager() instanceof Fireball
				&& event.getEntity() instanceof Monster) {
			Fireball fireball = (Fireball) event.getDamager();
			if (fireball.getShooter() instanceof Player) {
				Player damager = (Player) fireball.getShooter();
				RPGPlus.experienceHandler.handleXp(damager, SkillType.WISDOM, 1);
			}
		}
		
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Player target = (Player) event.getEntity();
			if (!damager.hasMetadata("NPC") && !target.hasMetadata("NPC")) {
				if (PartyManager.inSameParty(damager, target)) {
					event.setCancelled(true);
				}
			}
		}
		if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
			Projectile projectile = (Projectile) event.getDamager();
			if (projectile.getShooter() instanceof Player) {
				Player damager = (Player) projectile.getShooter();
				Player target = (Player) event.getEntity();
				if (!damager.hasMetadata("NPC") && !target.hasMetadata("NPC")) {
					if (PartyManager.inSameParty(damager, target)) {
						event.setCancelled(true);
					}
				}
			}
		}

	}
	
	private boolean hasAbilitySelected(Player p, AbilityType type, SkillType skill) {
		
		Map<AbilityType, Ability> playerMap = PlayerDataManager.getPlayerData(p).getAbilityMap();
		if(playerMap.containsKey(type)) {
			Ability ability = playerMap.get(type);
			if (ability.getSkillType().equals(skill)) {
				return true;
			}
		}
		return false;
	}
	
	private Ability getCorrectAbility(Player p, boolean rightClicked, SkillType type) {
		Map<AbilityType, Ability> playerMap = PlayerDataManager.getPlayerData(p).getAbilityMap();
		if (!p.isSneaking()) {
			if (!rightClicked) {
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
	private int armorEvaluator(ItemStack[] armor) {
		int value = 0;
		for (int i = 0; i < armor.length; i++) {
			if (armor[i].getType().toString().toLowerCase().contains("golden")) {
				value += 1;
			} else if (armor[i].getType().toString().toLowerCase().contains("iron")) {
				value += 2;
			}else if (armor[i].getType().toString().toLowerCase().contains("diamond")) {
				value += 2;
			}
		}
		return value;
	}
}
