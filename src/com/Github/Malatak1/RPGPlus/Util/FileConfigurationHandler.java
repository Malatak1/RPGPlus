package com.Github.Malatak1.RPGPlus.Util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FileConfigurationHandler {
	
	FileConfiguration f;
	Player p;
	
	public FileConfigurationHandler(FileConfiguration f, Player p) {
		this.f = f;
		this.p = p;
	}
	
	public void loadFile() {
		
	}
	
	public void saveFile() {
		FileSaver fileSaver = new FileSaver(f, p);
		new Thread(fileSaver).start();
	}
	
}
