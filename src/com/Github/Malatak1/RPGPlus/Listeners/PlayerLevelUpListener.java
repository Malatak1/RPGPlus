package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Events.PlayerLevelUpEvent;

public class PlayerLevelUpListener implements Listener {
	
	DataBaseManager db;
	
	@EventHandler
	public void onPlayerLevelUP(PlayerLevelUpEvent event) {
		
		db = new DataBaseManager(RPGPlus.inst());
		
		Player p = event.getPlayer();
		SkillType type = event.getSkillType();
		int level = event.getNewLevel();
		String s = capitalize(type.toString());
		
		Map<Player, FileConfiguration> playerMap = db.getFileMap();
		FileConfiguration playerStats = playerMap.get(p);
		int skillPoints = playerStats.getInt("SkillPoints." + s);
		playerStats.set("SkillPoints." + s, skillPoints + 1);
		playerMap.put(p, playerStats);
		db.setFileMap(playerMap);
		
		if (type.equals(SkillType.CONSTITUTION)) {
			
			double increase = Math.round(level / 3);
			
			p.setHealthScale(20F + increase);
			p.setHealthScaled(true);
			p.setMaxHealth(20F + increase);
			
		}
	}
	
	private String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
	
}
