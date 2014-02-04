package com.Github.Malatak1.RPGPlus;

import java.io.File;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Github.Malatak1.RPGPlus.Commands.CommandHandler;
import com.Github.Malatak1.RPGPlus.Commands.PartyCommand;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.ExperienceHandler;
import com.Github.Malatak1.RPGPlus.Database.LeaderboardManager;
import com.Github.Malatak1.RPGPlus.Listeners.EntityDamageByEntityListener;
import com.Github.Malatak1.RPGPlus.Listeners.EntityDamageListener;
import com.Github.Malatak1.RPGPlus.Listeners.EntityDeathListener;
import com.Github.Malatak1.RPGPlus.Listeners.EntityShootBowListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerInteractListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerJoinListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerLevelUpListener;
import com.Github.Malatak1.RPGPlus.Listeners.PlayerQuitListener;
import com.Github.Malatak1.RPGPlus.Listeners.PotionSplashListener;

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
    	getCommand("focus").setExecutor(new CommandHandler());
    	getCommand("party").setExecutor(new PartyCommand());
    	
    	//Registering Listeners
    	pm.registerEvents(new PlayerJoinListener(), this);
    	pm.registerEvents(new PlayerQuitListener(), this);
    	pm.registerEvents(new PlayerInteractListener(), this);
    	pm.registerEvents(new EntityDamageListener(), this);
    	pm.registerEvents(new EntityDamageByEntityListener(), this);
    	pm.registerEvents(new EntityDeathListener(), this);
    	pm.registerEvents(new EntityShootBowListener(), this);
    	pm.registerEvents(new PotionSplashListener(), this);
    	pm.registerEvents(new PlayerLevelUpListener(), this);
    	
    	//Setting up Database
    	saveDefaultConfig();
    	if (getDataFolder() == null) {
    	    File file = new File("RPGPlus");
    	    file.mkdirs();
    	}
    	getDataFolder().mkdirs();
        new DataBaseManager(this).prepareDataBase();
        LeaderboardManager.init();
        
        //Preparing IconMenus
        iconMenuHandler.initIconMenus();
        
        //Start repeating tasks
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ManaRegenerator(), 50, 50);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TitleRunnable(), 50, 50);
        
    }
 
    @Override
    public void onDisable() {
    	saveConfig();
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