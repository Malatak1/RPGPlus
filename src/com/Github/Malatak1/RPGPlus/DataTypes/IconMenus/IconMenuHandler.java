package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.PartyIconMenus.BasePartyMenus;

public class IconMenuHandler{
	
	private static IconMenu baseIconMenu;
	private static SkillTreeIconMenus stMenus;
	private static MiscMenus misc;
	
	public static ChatColor pri = ChatColor.AQUA;
	public static ChatColor sec = ChatColor.GREEN;
	public static final ChatColor ter = ChatColor.YELLOW;;
	
	public static void initIconMenus(){
		
		stMenus = new SkillTreeIconMenus();
		misc = new MiscMenus();
		
		baseIconMenu = new IconMenu("Menu", 9, new IconMenu.OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				
				
				event.setWillClose(false);
				
				switch (event.getPosition()) {
				case 1: stMenus.strength(p).open(p); break;
				case 2: stMenus.dexterity(p).open(p); break;
				case 3: stMenus.wisdom(p).open(p); break;
				case 4: AbilitySelectMenus.createAbilityMenu(p).open(p); break;
				case 8: 
					if (p.isOp()) {
						p.closeInventory(); new BasePartyMenus().baseMenu(p).open(p);
						p.sendMessage(ChatColor.YELLOW + "Parties are in development. Use for testing only");
						break;
					} else {
						p.sendMessage(ChatColor.YELLOW + "Parties are in development. Check back later!");
						break;
					}
				default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
				
				}
			}
		}, RPGPlus.inst());
		
		baseIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		baseIconMenu.setOption(1, new ItemStack(Material.IRON_SWORD), pri + "Strength", sec + "Click to open " + ter + "strength" + sec + " skilltrees");
		baseIconMenu.setOption(2, new ItemStack(Material.BOW), pri + "Dexterity",sec + "Click to open " + ter + "dexterity" + sec + " skilltrees");
		baseIconMenu.setOption(3, new ItemStack(Material.BOOK), pri + "Wisdom", sec + "Click to open " + ter + "wisdom" + sec + " skilltrees");
		baseIconMenu.setOption(4, new ItemStack(Material.EMERALD), pri + "Abilities", sec + "Choose the " + ter + "abilities" + sec + " you want selected");
		baseIconMenu.setOption(8, new ItemStack(Material.ENDER_PEARL), pri + "Parties", sec + "Create, join and manage parties");
		
		
		stMenus.init();
		misc.init();
	}
	
	public static IconMenu getBaseIconMenu(){
		
		if(baseIconMenu == null) initIconMenus();
		return baseIconMenu;
		
	}
}
