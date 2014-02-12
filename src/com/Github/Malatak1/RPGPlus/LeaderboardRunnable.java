package com.Github.Malatak1.RPGPlus;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.LeaderboardManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class LeaderboardRunnable implements Runnable {
	
	DataBaseManager db;
	
	@Override
	public void run() {
		db = new DataBaseManager(RPGPlus.inst());
		HashSet<FileConfiguration> configs = new HashSet<>();
		
		String[] pathList = db.getDataFolder().list();
		for (String s : pathList) {
			File f = new File(db.getDataFolder().getAbsolutePath(), s.toString());
			if (f.getTotalSpace() > 0) {
				FileConfiguration playerStats = new YamlConfiguration();
				try {
					playerStats.load(f);
				} catch (IOException | InvalidConfigurationException e) {
					e.printStackTrace();
				}
				configs.add(playerStats);
			}
		}
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			FileConfiguration playerFile = PlayerDataManager.getPlayerData(p).getFile();
			configs.add(playerFile);
		}
		
		Scoreboard board = LeaderboardManager.getScoreboard();
		Objective objective = board.getObjective("combatLevels");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		for (FileConfiguration config : configs) {
			Score score = objective.getScore(Bukkit.getOfflinePlayer(config.getName()));
			
			score.setScore(config.getInt("Skills.Strength"));
		}
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.setScoreboard(board);
		}
		
		LeaderboardManager.setScoreboard(board);
		
	}

}
