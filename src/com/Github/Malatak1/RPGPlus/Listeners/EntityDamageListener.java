package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class EntityDamageListener implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		
		if (event.getEntity() instanceof Player && event.getEntity() instanceof Damageable && event.getEntity().hasMetadata("NPC")) {
			
			Player p = (Player) event.getEntity();
			
			//Check to assert that the player is not just an npc
			if (p.isOnline()) {
				
				double damage = event.getDamage();
				double health = ((Damageable) p).getHealth();
				double maxHealth = ((Damageable) p).getMaxHealth();
				
				boolean giveXp = true;
				int mod = 3;
				
				if (event instanceof EntityDamageByEntityEvent) {
					mod = 2;
				}
				
				if (event.getCause().equals(DamageCause.LAVA)) {
					giveXp = false;
				}
				
				if (giveXp && health < maxHealth * 0.75 && damage > mod && damage < health) {
					
					double xp = damage / 2;
					RPGPlus.experienceHandler.handleXp(p, SkillType.CONSTITUTION, (int) xp);
					
				}
				
			}
			
		}
		
	}
	
}
