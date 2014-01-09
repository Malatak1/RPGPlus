package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Dexterity.VolleyAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Strength.WeakenAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.DeflectAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.FireballAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.FireboltAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.FreezingWindsAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class MiscMenus {
	
	DataBaseManager db;
	
	private static MiscMenus instance;
	
	private IconMenu lightSelect;
	private IconMenu mediumSelect;
	private IconMenu heavySelect;
	private IconMenu ultimateSelect;
	
	private static final ChatColor pri = IconMenuHandler.pri;
	private static final ChatColor sec = IconMenuHandler.sec;
	
	public void init() {
		
		db = new DataBaseManager(RPGPlus.inst());
		instance = this;
		
		lightSelect = new IconMenu("Light Ability", 36, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				case 0: 
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				case 1:
					db.setAbility(p, new FireboltAbility());
					p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				case 2:
					db.setAbility(p, new WeakenAbility());
					p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				default: 
					p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				}
				event.setWillClose(false);
			}
		}, RPGPlus.inst());
		lightSelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		lightSelect.addAbility(1, new FireboltAbility());
		lightSelect.addAbility(2, new WeakenAbility());
		
		mediumSelect = new IconMenu("Medium Ability", 36, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				case 0: 
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				case 1:
					db.setAbility(p, new FireballAbility());
					p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				default: 
					p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				}
				event.setWillClose(false);
			}
		}, RPGPlus.inst());
		mediumSelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		mediumSelect.addAbility(1, new FireballAbility());
		
		heavySelect = new IconMenu("Heavy Ability", 36, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				case 0: 
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				case 1:
					db.setAbility(p, new DeflectAbility());
					p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				default: 
					p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				}
				event.setWillClose(false);
			}
		}, RPGPlus.inst());
		heavySelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		heavySelect.addAbility(1, new DeflectAbility());
		
		ultimateSelect = new IconMenu("Ultimate Ability", 36, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				case 0: 
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				case 1:
					db.setAbility(p, new FreezingWindsAbility());
					p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				case 2:
					db.setAbility(p, new VolleyAbility());
					p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				default: 
					p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
					break;
				}
				event.setWillClose(false);
			}
		}, RPGPlus.inst());
		ultimateSelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		ultimateSelect.addAbility(1, new FreezingWindsAbility());
		ultimateSelect.addAbility(2, new VolleyAbility());
	}
	
	public IconMenu getLight() {
		return lightSelect;
	}
	public IconMenu getMedium() {
		return mediumSelect;
	}
	public IconMenu getHeavy() {
		return heavySelect;
	}
	public IconMenu getUltimate() {
		return ultimateSelect;
	}
	
	public static MiscMenus inst() {
		return instance;
	}
}
