package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class EntityDamageListener implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		
		if (event.getEntity() instanceof Player && event.getEntity() instanceof Damageable) {
			
			Player p = (Player) event.getEntity();
			
			//Check to assert that the player is not just an npc - may not be necessary, but better to be safe
			if (p.isOnline()) {
				
				double damage = event.getDamage();
				double health = ((Damageable) p).getHealth();
				double maxHealth = ((Damageable) p).getMaxHealth();
				
				if (health < maxHealth * 0.75 && damage > 3) {
					
					double xp = damage / 2;
					RPGPlus.experienceHandler.handleXp(p, SkillType.CONSTITUTION, (int) xp);
					
				}
				
			}
			
		}
		
	}
	
}
