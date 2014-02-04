package com.Github.Malatak1.RPGPlus.Database;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class LeaderboardManager {
	
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private static Scoreboard board = manager.getNewScoreboard();
	private static Objective combatLevels;
	
	public static void init() {
		combatLevels = board.registerNewObjective("combatLevels", "dummy");
		combatLevels.setDisplayName("Combat Levels");
	}
	
	public static Scoreboard getScoreboard() {
		return board;
	}
	
	public static void setScoreboard(Scoreboard board) {
		LeaderboardManager.board = board;
	}
}
