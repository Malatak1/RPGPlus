package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.ProjectileAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class EntityShootBowListener implements Listener {
	
	DataBaseManager db;
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		if (event.getBow() != null && event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow) {
			db = new DataBaseManager(RPGPlus.inst());
			
			Player p = (Player) event.getEntity();
			Arrow arrow = (Arrow) event.getProjectile();
			
			if (db.getAbilityMap(p).containsKey(AbilityType.ULTIMATE) && p.isSneaking()) {
				if (db.getAbilityMap(p).get(AbilityType.ULTIMATE).getSkillType().equals(SkillType.DEXTERITY)) {
					((ProjectileAbility) db.getAbilityMap(p).get(AbilityType.ULTIMATE)).onShoot(arrow, 1);
				}
			}
		}
	}
	
}
