package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Events.PlayerLevelUpEvent;

public class PlayerLevelUpListener implements Listener {
	
	@EventHandler
	public void onPlayerLevelUP(PlayerLevelUpEvent event) {
		
		Player p = event.getPlayer();
		SkillType type = event.getSkillType();
		int level = event.getNewLevel();
		
		if (type.equals(SkillType.CONSTITUTION)) {
			
			double increase = ((level + 1) / 3) - level % 3;
			
			p.setHealthScale(20D + increase);
			p.setHealthScaled(true);
			p.setMaxHealth(20D + increase);
			
		}
	}
	
}
