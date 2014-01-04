package com.Github.Malatak1.RPGPlus.Util;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class FileSaver implements Runnable {
	
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	FileConfiguration f;
	Player p;
	
	public FileSaver(FileConfiguration f, Player p) {
		this.f = f;
		this.p = p;
	}
	
	
	@Override
	public void run() {
		
		try {
			f.save(db.getPlayerFile(p));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
