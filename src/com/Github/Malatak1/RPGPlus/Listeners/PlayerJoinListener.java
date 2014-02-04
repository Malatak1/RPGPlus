package com.Github.Malatak1.RPGPlus.Listeners;

import java.io.IOException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public final class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		
		Player p = event.getPlayer();
		DataBaseManager db = new DataBaseManager(RPGPlus.inst());
		
		if (!db.hasPlayerFile(p)) {
			Bukkit.getLogger().info("Creating new player file...");
			db.createPlayerFile(p);
		}
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		try {
			mp.put(p, db.getPlayerStats(p));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		db.setFileMap(mp);
		
		
		/**
		 * Temporary code - may change
		 */
		
		FileConfiguration f = mp.get(p);
		
		float level = f.getInt("Skills.Constitution");
		double increase = Math.round(level / 3);
		
		p.setHealthScale(20F + increase);
		p.setHealthScaled(true);
		p.setMaxHealth(20F + increase);
	}
	
}
