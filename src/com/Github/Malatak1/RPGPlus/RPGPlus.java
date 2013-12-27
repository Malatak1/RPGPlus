package com.Github.Malatak1.RPGPlus;

import java.io.File;
import java.io.InputStream;

import org.bukkit.plugin.java.JavaPlugin;

import com.Github.Malatak1.RPGPlus.Commands.CommandHandler;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerJoinListener;

public class RPGPlus extends JavaPlugin {
	
	//Global Variables
	public static final IconMenuHandler iconMenuHandler = new IconMenuHandler();
	public static final DataBaseManager dataBaseManager = new DataBaseManager(RPGPlus.inst());
	
	public static InputStream baseFile;
	
	private static RPGPlus instance;
	
    @Override
    public void onEnable(){
    	
    	//Instantiate variables
    	instance = this;
    	baseFile = this.getResource("baseFile.yml");
    	
    	//Setting Command Executors
    	getCommand("rpg").setExecutor(new CommandHandler());
    	
    	//Registering Listeners
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    	
    	//Setting up Database
    	saveDefaultConfig();
    	if (getDataFolder() == null) {
    	    File file = new File("RPGPlus");
    	    file.mkdirs();
    	}
    	getDataFolder().mkdirs();
        new DataBaseManager(this).prepareDataBase();
        
        //Preparing IconMenus
        iconMenuHandler.initIconMenus();
    }
 
    @Override
    public void onDisable() {
    	new DataBaseManager(this).closeDataBase();
        instance = null;
    }
    
	public static RPGPlus inst() {
    	return instance;
    }
	
	public static InputStream getBaseFile() {
		return baseFile;
	}
}