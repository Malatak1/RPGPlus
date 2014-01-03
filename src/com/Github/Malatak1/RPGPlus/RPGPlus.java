package com.Github.Malatak1.RPGPlus;

import java.io.File;
import java.io.InputStream;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Github.Malatak1.RPGPlus.Commands.CommandHandler;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.ExperienceHandler;
import com.Github.Malatak1.RPGPlus.Listeners.EntityDamageByEntityListener;
import com.Github.Malatak1.RPGPlus.Listeners.EntityDamageListener;
import com.Github.Malatak1.RPGPlus.Listeners.EntityDeathListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerInteractListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerJoinListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerQuitListener;

public class RPGPlus extends JavaPlugin {
	
	//Global Variables
	public static final IconMenuHandler iconMenuHandler = new IconMenuHandler();
	public static final DataBaseManager dataBaseManager = new DataBaseManager(RPGPlus.inst());
	public static final ExperienceHandler experienceHandler = new ExperienceHandler();
	
	public static InputStream baseFile;
	
	private static RPGPlus instance;
	
    @Override
    public void onEnable(){
    	PluginManager pm = getServer().getPluginManager();
    	
    	//Instantiate variables
    	instance = this;
    	baseFile = this.getResource("baseFile.yml");
    	
    	//Setting Command Executors
    	getCommand("rpg").setExecutor(new CommandHandler());
    	
    	//Registering Listeners
    	pm.registerEvents(new PlayerJoinListener(), this);
    	pm.registerEvents(new PlayerQuitListener(), this);
    	pm.registerEvents(new PlayerInteractListener(), this);
    	pm.registerEvents(new EntityDamageListener(), this);
    	pm.registerEvents(new EntityDamageByEntityListener(), this);
    	pm.registerEvents(new EntityDeathListener(), this);
    	
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