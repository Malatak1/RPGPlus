package com.Github.Malatak1.RPGPlus.Listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		Player p = event.getPlayer();
		DataBaseManager db = new DataBaseManager(RPGPlus.inst());
		
		if (!db.hasPlayerFile(p)) {
			db.createPlayerFile(p);
		}
		
		db.savePlayer(p);
		
	}
	
}
