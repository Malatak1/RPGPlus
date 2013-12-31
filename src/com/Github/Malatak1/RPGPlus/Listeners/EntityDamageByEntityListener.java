package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class EntityDamageByEntityListener implements Listener {
	
	DataBaseManager db;
	FileConfiguration f;
	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (((Player) event.getDamager()).isOnline()) {
				
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
			}
		}
		if (event.getDamager() instanceof Arrow) {
			if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
				
				Player damager = (Player) ((Arrow) event.getDamager()).getShooter();
				if (damager.isOnline()) {
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
					
				}
			}
		}
	
	}
	
}
