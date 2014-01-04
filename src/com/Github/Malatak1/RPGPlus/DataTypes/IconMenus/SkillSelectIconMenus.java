package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;

public class SkillSelectIconMenus {
	
	private static IconMenu abilitySelectMenu;
	
	private ChatColor pri;
	private ChatColor sec;
	
	public void init() {
		
		pri = ChatColor.AQUA;
		sec = ChatColor.GREEN;
		
		abilitySelectMenu = new IconMenu("Ability Selection", 9, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				switch (event.getPosition()) {
				
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
				
				}
			}
		}, RPGPlus.inst());
		
		abilitySelectMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Select your chosen abilities");
		
		
	}
	
}
