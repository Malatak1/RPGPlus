package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class AbilitySelectMenus {
	
	private static final ChatColor pri = IconMenuHandler.pri;
	private static final ChatColor sec = IconMenuHandler.sec;
	
	static DataBaseManager db = new DataBaseManager(RPGPlus.inst());;
	static IconMenu abilitySelectMenu;
	
	public static IconMenu createAbilityMenu(Player p) {
		
		abilitySelectMenu = new IconMenu("Ability Selection", 9,
				new IconMenu.OptionClickEventHandler() {

					@Override
					public void onOptionClick(OptionClickEvent event) {
						Player p = event.getPlayer();
						
						switch (event.getPosition()) {

						case 1:
							p.closeInventory();
							MiscMenus.inst().getLight().open(p);
							break;
						case 2:
							p.closeInventory();
							MiscMenus.inst().getMedium().open(p);
							break;
						case 3:
							p.closeInventory();
							MiscMenus.inst().getHeavy().open(p);
							break;
						case 4:
							p.closeInventory();
							MiscMenus.inst().getUltimate().open(p);
							break;

						default:
							p.sendMessage(ChatColor.YELLOW + "That was not clickable!");
						}
						event.setWillClose(false);
						event.setWillDestroy(true);
					}
				}, RPGPlus.inst());
		Map<AbilityType, Ability> abilityMap = db.getAbilityMap(p);
		addSpecialOptions(abilityMap);
		abilitySelectMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		
		return abilitySelectMenu;
		
	}
	
	public static CastableAbility getAbility(Map<AbilityType, CastableAbility> abilityMap, AbilityType type) {
		if (abilityMap.containsKey(type)) {
			return abilityMap.get(type);
		}
		return null;
	}
	
	public static void addSpecialOptions(Map<AbilityType, Ability> abilityMap ) {
		
		for (AbilityType type : AbilityType.values()) {
			Ability ability = abilityMap.get(type);
			
			int pos;
			
			switch(type) {
			case LIGHT: pos = 1; break;
			case MEDIUM: pos = 2; break;
			case HEAVY: pos = 3; break;
			case ULTIMATE: pos = 4; break;
			default: pos = 1; break;
			}
			
			if (abilityMap.containsKey(type)) {
				abilitySelectMenu.addAbility(pos, ability);
			} else {
				abilitySelectMenu.setOption(pos, new ItemStack(Material.EMPTY_MAP), pri + capitalize(type.toString()), sec + "No ability selected");
			}
		}		
	}
	
	private static String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
	
}
