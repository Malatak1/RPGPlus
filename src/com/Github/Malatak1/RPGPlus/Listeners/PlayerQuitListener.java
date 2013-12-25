package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player p = event.getPlayer();
		DataBaseManager db = new DataBaseManager(RPGPlus.inst());
		
		if (!db.hasPlayerFile(p)) {
			db.createPlayerFile(p);
		}
		
		db.savePlayer(p);
		
	}
	
}
