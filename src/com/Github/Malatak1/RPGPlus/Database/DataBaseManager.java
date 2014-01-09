package com.Github.Malatak1.RPGPlus.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.Util.FileSaver;
import com.Github.Malatak1.RPGPlus.Util.StreamHandler;

/**
 * DataBaseManager Class
 * TODO: Add in functionality for server reload handling
 */

public class DataBaseManager {
	
	String folderName = "PlayerData";
	RPGPlus rpgPlus = new RPGPlus();
	static File fileDataBase;
	public static Map<Player, FileConfiguration> playerFileConfigMap;
	public static Map<Player,HashMap<AbilityType, Ability>> playerAbilityMap;
	
	Plugin plugin;
	
	public DataBaseManager(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void prepareDataBase() {
		if (plugin.getDataFolder() != null) {
			File dataFolder = plugin.getDataFolder();
			if (dataFolder.isDirectory()) {
				File[] subFiles = dataFolder.listFiles();
				boolean dbExists = false;
				for (int i = 0; i < subFiles.length; i++) {
					if (subFiles[i].isDirectory() && subFiles[i].getName() == folderName) {
						dbExists = true;
					}
				}
				if (dbExists != true) {
					if (dataFolder.isDirectory()) {
						StringBuilder path = new StringBuilder(dataFolder.getAbsolutePath() + File.separator);
						
						path.append(folderName + File.separator);
						
						File f = new File(path.toString());
						f.mkdirs(); 
						try {
							f.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						dataFolder = f;
					}
				}
			}
		} else {
			plugin.getDataFolder().mkdirs();
			this.prepareDataBase();
			}
		
		StringBuilder path = new StringBuilder(plugin.getDataFolder().getAbsolutePath() + File.separator);
		
		path.append(folderName + File.separator);
		fileDataBase = new File(path.toString());
		playerFileConfigMap = new HashMap<Player, FileConfiguration>();
		playerAbilityMap = new HashMap<Player, HashMap<AbilityType, Ability>>();
	}
	
	public void closeDataBase() {
		try {
			savePlayerFiles(playerFileConfigMap);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public File getDataFolder() {
		return fileDataBase;
	}
	
	public YamlConfiguration getPlayerStats(Player p) throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		File f = getPlayerFile(p);
		
		YamlConfiguration playerStats = new YamlConfiguration();
		playerStats.load(f);
		return playerStats;
		
	}
	
	public File getPlayerFile(Player p) {
		
		StringBuilder s = new StringBuilder(p.getName().toLowerCase());
		s.append(".yml");
		
		return new File(fileDataBase.getAbsolutePath(), s.toString());
		
	}
	
	public boolean hasPlayerFile(Player p) {
		File[] files = fileDataBase.listFiles();
		boolean exists = false;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(p.getName().toLowerCase() + ".yml")) {
				exists = true;
			}
		}
		return exists;
	}
	
	public void createPlayerFile(Player p) {
		File file = getPlayerFile(p);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new StreamHandler(RPGPlus.getBaseFile(), file).run();
	}
	
	public void savePlayerFiles(Map<Player, FileConfiguration> mp) throws InvalidConfigurationException {
	    Iterator<Entry<Player, FileConfiguration>> it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Player, FileConfiguration> pairs = (Map.Entry<Player, FileConfiguration>)it.next();
        	FileConfiguration f = pairs.getValue();
	        try {
				f.save(getPlayerFile(pairs.getKey()));
			} catch (IOException e) {
				e.printStackTrace();
			}
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public void savePlayer(Player p) throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		FileConfiguration f = playerFileConfigMap.get(p);
		
		FileSaver fileSaver = new FileSaver(f, p);
		new Thread(fileSaver).start();
		
		playerFileConfigMap.remove(p);
		
	}
	
	public Map<Player, FileConfiguration> getFileMap() {
		return playerFileConfigMap;
	}
	
	public void setFileMap(Map<Player, FileConfiguration> mp) {
		playerFileConfigMap = mp;
	}
	
	public Map<AbilityType, Ability> getAbilityMap(Player p) {
		
		if(!playerAbilityMap.containsKey(p)) {
			playerAbilityMap.put(p, new HashMap<AbilityType, Ability>());
		}
		return playerAbilityMap.get(p);
		
	}
	
	public void setAbility(Player p, Ability ability) {
		
		Map<AbilityType , Ability> playerMap = getAbilityMap(p);
		playerMap.put(ability.getAbilityType(), ability);
		
		playerAbilityMap.put(p, (HashMap<AbilityType, Ability>) playerMap);
		
	}
}
