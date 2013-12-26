package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			
			Player damager = (Player) event.getDamager();
			
			if (damager.isSleeping()) {
				
			}
			
			
		}
	}
	
}
