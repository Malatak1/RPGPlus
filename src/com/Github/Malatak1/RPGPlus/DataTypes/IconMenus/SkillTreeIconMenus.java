package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;

public class SkillTreeIconMenus {
	
	private static IconMenu strengthIconMenu;
	private static IconMenu dexterityIconMenu;
	private static IconMenu wisdomIconMenu;
	
	private ChatColor pri;
	private ChatColor sec;
	//private ChatColor ter;
	
	public void init() {
		
		pri = ChatColor.AQUA;
		sec = ChatColor.GREEN;
		//ter = ChatColor.YELLOW;
		
		strengthIconMenu = new IconMenu("Strength Skilltrees", 27, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!"); break;
				
				}
			}
		}, RPGPlus.inst());
		
		strengthIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		
		dexterityIconMenu = new IconMenu("Dexterity Skilltrees", 27, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!"); break;
				
				}
			}
		}, RPGPlus.inst());
		
		dexterityIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		
		wisdomIconMenu = new IconMenu("Wisdom Skilltrees", 27, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!"); break;
				
				}
			}
		}, RPGPlus.inst());
		
		wisdomIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");		
		
	}
	
	public IconMenu getStrengthMenu() {
		return strengthIconMenu;
	}
	public IconMenu getDexterityMenu() {
		return strengthIconMenu;
	}
	public IconMenu getWisdomMenu() {
		return strengthIconMenu;
	}
}
