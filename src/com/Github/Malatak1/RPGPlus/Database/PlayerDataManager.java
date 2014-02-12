package com.Github.Malatak1.RPGPlus.Database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.PlayerData;

public class PlayerDataManager {
	
	private static Map<String, PlayerData> playerDataMap = new HashMap<>();
	private static DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	public static Map<String, PlayerData> getPlayerDataMap() {
		return playerDataMap;
	}
	
	public static PlayerData getPlayerData(Player player) {
		return playerDataMap.get(player.getName());
	}
	
	public static PlayerData getPlayerData(String name) {
		return playerDataMap.get(name);
	}
	
	public static void setPlayerData(Player player, PlayerData playerData) {
		playerDataMap.put(player.getName(), playerData);
	}
	
	public static void createPlayerData(Player p) {
		try {
			PlayerData playerData = new PlayerData(p, db.getPlayerStats(p));
			playerDataMap.put(p.getName(), playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static void removePlayerData(Player p) {
		playerDataMap.remove(p.getName());
	}
	
}
