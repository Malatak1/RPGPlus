package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;

public class IconMenuHandler{
	
	private static IconMenu baseIconMenu;
	private static IconMenu strengthIconMenu;
	private static IconMenu dexterityIconMenu;
	private static IconMenu wisdomIconMenu;
	
	private ChatColor pri;
	private ChatColor sec;
	private ChatColor ter;
	
	public void initIconMenus(){
		
		pri = ChatColor.AQUA;
		sec = ChatColor.GREEN;
		ter = ChatColor.YELLOW;
		
		//TODO Here we will have to generate all iconmenus we will have
		
		baseIconMenu = new IconMenu("Character Customization", 9, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				case 1: strengthIconMenu.open(p);
				case 2: dexterityIconMenu.open(p);
				case 3: wisdomIconMenu.open(p);
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
				
				}
			}
		}, RPGPlus.inst());
		
		baseIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		baseIconMenu.setOption(1, new ItemStack(Material.IRON_SWORD), pri + "Strength", sec + "Click to open " + ter + "strength" + sec + " skilltrees");
		baseIconMenu.setOption(2, new ItemStack(Material.BOW), pri + "Dexterity",sec + "Click to open " + ter + "dexterity" + sec + " skilltrees");
		baseIconMenu.setOption(3, new ItemStack(Material.BOOK), pri + "Wisdom", sec + "Click to open " + ter + "wisdom" + sec + " skilltrees");
		
		strengthIconMenu = new IconMenu("Strength Skilltrees", 27, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
				
				}
			}
		}, RPGPlus.inst());
		
		strengthIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		
		dexterityIconMenu = new IconMenu("Strength Skilltrees", 27, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
				
				}
			}
		}, RPGPlus.inst());
		
		dexterityIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		
		wisdomIconMenu = new IconMenu("Strength Skilltrees", 27, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
				
				}
			}
		}, RPGPlus.inst());
		
		wisdomIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		
		
	}
	
	public IconMenu getBaseIconMenu(){
		
		if(baseIconMenu == null) initIconMenus();
		return baseIconMenu;
		
	}
}
