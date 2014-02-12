package com.Github.Malatak1.RPGPlus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class TitleRunnable extends BukkitRunnable {

	DataBaseManager db = new DataBaseManager(RPGPlus.inst());

	@Override
	public void run() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();

		Objective objective = board.registerNewObjective("Level", "dummy");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName("Level");

		for (Player online : Bukkit.getOnlinePlayers()) {
			if (!online.hasMetadata("NPC")) {
				FileConfiguration f = PlayerDataManager.getPlayerData(online).getFile();
				int str = f.getInt("Skills.Strength");
				int dex = f.getInt("Skills.Dexterity");
				int wis = f.getInt("Skills.Wisdom");
				int max = Math.max(str, Math.max(dex, wis));
				Score score = objective.getScore(online);
				score.setScore(max + f.getInt("Skills.Constitution"));
			}
		}
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.setScoreboard(board);
		}
	}
}
