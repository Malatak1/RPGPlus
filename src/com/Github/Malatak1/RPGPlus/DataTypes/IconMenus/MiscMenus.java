package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.SkillTreeHandler;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.PlayerData;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEventHandler;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class MiscMenus {
	
	DataBaseManager db;
	
	private static MiscMenus instance;
	
	private static final ChatColor pri = IconMenuHandler.pri;
	private static final ChatColor sec = IconMenuHandler.sec;
	private static OptionClickEventHandler handler;
	
	public void init() {
		db = new DataBaseManager(RPGPlus.inst());
		instance = this;
		
		handler = new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				if (event.getPosition() == 0) {
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
				} else if (SkillTreeHandler.isAbilityName(event.getName())) {
					if (setAbility(p, SkillTreeHandler.getAbility(event.getName()))) {
						p.closeInventory();
						AbilitySelectMenus.createAbilityMenu(p).open(p);
					} else {
						switch(event.getMenu().getName()) {
						case "Light Ability": p.closeInventory(); light(p).open(p); break;
						case "Medium Ability": p.closeInventory(); medium(p).open(p); break;
						case "Heavy Ability": p.closeInventory(); heavy(p).open(p); break;
						case "Ultimate Ability": p.closeInventory(); ultimate(p).open(p); break;
							default:
								p.closeInventory();
								AbilitySelectMenus.createAbilityMenu(p).open(p);
								break;
						}
					}
				} else {
					p.closeInventory();
					AbilitySelectMenus.createAbilityMenu(p).open(p);
				}
				event.setWillClose(false);
				event.setWillDestroy(true);
			}
		};
	}
	
	public IconMenu light(Player p) {
		IconMenu lightSelect = new IconMenu("Light Ability", 36, handler, RPGPlus.inst());
		lightSelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		int i = 1;
		for (Ability ability : SkillTreeHandler.abilityList) {
			if (ability.getAbilityType() == AbilityType.LIGHT) {
				lightSelect.addAbility(i, ability, p);
				i++;
			}
		}
		return lightSelect;
	}
	
	public IconMenu medium(Player p) {
		IconMenu mediumSelect = new IconMenu("Medium Ability", 36, handler, RPGPlus.inst());
		mediumSelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		int i = 1;
		for (Ability ability : SkillTreeHandler.abilityList) {
			if (ability.getAbilityType() == AbilityType.MEDIUM) {
				mediumSelect.addAbility(i, ability, p);
				i++;
			}
		}
		return mediumSelect;
	}
	
	public IconMenu heavy(Player p) {
		IconMenu heavySelect = new IconMenu("Heavy Ability", 36, handler, RPGPlus.inst());
		heavySelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		int i = 1;
		for (Ability ability : SkillTreeHandler.abilityList) {
			if (ability.getAbilityType() == AbilityType.HEAVY) {
				heavySelect.addAbility(i, ability, p);
				i++;
			}
		}
		return heavySelect;
	}
	
	public IconMenu ultimate(Player p) {
		IconMenu ultimateSelect = new IconMenu("Ultimate Ability", 36, handler, RPGPlus.inst());
		ultimateSelect.setOption(0, new ItemStack(Material.WOOL, 1, (byte) 14), pri + "Exit", sec + "Return to Ability Selection");
		int i = 1;
		for (Ability ability : SkillTreeHandler.abilityList) {
			if (ability.getAbilityType() == AbilityType.ULTIMATE) {
				ultimateSelect.addAbility(i, ability, p);
				i++;
			}
		}
		return ultimateSelect;
	}
	
	public static MiscMenus inst() {
		return instance;
	}
	
	private boolean setAbility(Player p, Ability ability) {
		PlayerData playerData = PlayerDataManager.getPlayerData(p);
		FileConfiguration f = playerData.getFile();
		if (f.contains("Abilities." + capitalize(ability.getSkillType().toString()) + "." + DataBaseManager.abilityToName(ability))) {
			Map<AbilityType, Ability> abilityMap = playerData.getAbilityMap();
			abilityMap.put(ability.getAbilityType(), ability);
			playerData.setAbilityMap(abilityMap);
			PlayerDataManager.setPlayerData(p, playerData);
			p.sendMessage(pri + "You have selected " + sec + ability.getName());
			return true;
		} else {
			p.sendMessage(pri + "You have not unlocked " + sec + ability.getName() + pri + " yet.");
			return false;
		}
	}
	
	private static String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
}
