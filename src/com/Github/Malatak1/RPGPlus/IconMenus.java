package com.Github.Malatak1.RPGPlus;

public class IconMenus{
	
	private static IconMenu baseIconMenu;
	
	private static void initIconMenus(){
		
		baseIconMenu = new IconMenu("Customize Character:", 9, null, RPGPlus.inst());
		
	}
	
	public static IconMenu getBaseIconMenu(){
		
		if(baseIconMenu == null) initIconMenus();
		return baseIconMenu;
		
	}
}
