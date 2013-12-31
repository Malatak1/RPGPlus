package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class EntityDeathListener implements Listener{
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		
		if (entity.getKiller() != null) {
			
			Player p = entity.getKiller();
			
			if (entity.getLastDamageCause().getCause().equals(DamageCause.PROJECTILE)) {
				
				int xp = 1;
				if (entity instanceof Monster) {
					xp = 4;
				}
				
				RPGPlus.experienceHandler.handleXp(p, SkillType.DEXTERITY, xp);
				
			} else if (entity.getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK)) {
				
				int xp = 1;
				if (entity instanceof Monster) {
					xp = 4;
				}
				
				RPGPlus.experienceHandler.handleXp(p, SkillType.STRENGTH, xp);
				
			}
			
		}
		
//		if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
//			
//			Bukkit.getLogger().info("Stage 1");
//			
//			EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
//			
//			if (damageEvent.getDamager() instanceof Arrow) {
//				Bukkit.getLogger().info("Stage 2");
//				if (((Arrow) damageEvent.getDamager()).getShooter() instanceof Player) {
//					Bukkit.getLogger().info("Stage 3");
//					Player p = (Player) ((Arrow) damageEvent.getDamager()).getShooter();
//					
//					RPGPlus.experienceHandler.handleXp(p, SkillType.DEXTERITY, 10);
//					
//				}
//			}
//			
//		}
		
	}
	
}
