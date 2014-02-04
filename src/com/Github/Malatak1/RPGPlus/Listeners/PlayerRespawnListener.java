package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class PlayerRespawnListener implements Listener {
	
	DataBaseManager db;
	FileConfiguration f;
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
		Player p = event.getPlayer();
		db = new DataBaseManager(RPGPlus.inst());
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		f = mp.get(p);
		
		int level = f.getInt("Skills.Constitution");
		
		double increase = Math.round(level / 3);
		
		p.setHealthScale(20F + increase);
		p.setHealthScaled(true);
		p.setMaxHealth(20F + increase);
		
	}
	
}
