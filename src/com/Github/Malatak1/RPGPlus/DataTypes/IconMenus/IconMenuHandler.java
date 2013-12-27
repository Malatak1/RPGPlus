package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;

public class IconMenuHandler{
	
	private static IconMenu baseIconMenu;
	
	//I changed this to a public method so we can call in the onEnable().
	public void initIconMenus(){
		
		baseIconMenu = new IconMenu("Character Customization", 9, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		}, RPGPlus.inst());
		
	}
	
	public IconMenu getBaseIconMenu(){
		
		if(baseIconMenu == null) initIconMenus();
		return baseIconMenu;
		
	}
}
