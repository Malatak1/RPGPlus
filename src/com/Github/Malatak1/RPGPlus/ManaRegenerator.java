package com.Github.Malatak1.RPGPlus;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class ManaRegenerator extends BukkitRunnable {
	
	DataBaseManager db;
	
	public ManaRegenerator() {
		db = new DataBaseManager(RPGPlus.inst());
	}
	
	@Override
	public void run() {
		Map<Player,FileConfiguration> fileMap = db.getFileMap();
		for (Player p : Bukkit.getOnlinePlayers()) {
			FileConfiguration f = fileMap.get(p);
			int level = f.getInt("Skills.Wisdom");
			
			int maxMana = 50 + ((level - 1) * 25);
			int currentMana = p.getLevel();
			int recharge = 2;
			if (currentMana + recharge < maxMana) {
				p.setLevel(currentMana + recharge);
			} else p.setLevel(maxMana);
		}
	}

}
