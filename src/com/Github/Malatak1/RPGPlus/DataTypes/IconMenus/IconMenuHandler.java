package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;

public class IconMenuHandler{
	
	private static IconMenu baseIconMenu;
	
	//I changed this to a public method so we can call it in the onEnable().
	public void initIconMenus(){
		
		//TODO: Here we will have to generate all iconmenus we will have.
		
		baseIconMenu = new IconMenu("Character Customization", 9, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		}, RPGPlus.inst());
		baseIconMenu.setOption(0, new ItemStack(Material.PAPER), ChatColor.AQUA + "Info", ChatColor.GREEN + "Use the menus to customize your character");
	}
	
	public IconMenu getBaseIconMenu(){
		
		if(baseIconMenu == null) initIconMenus();
		return baseIconMenu;
		
	}
}
