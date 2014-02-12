package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import java.util.List;

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
import com.Github.Malatak1.RPGPlus.DataTypes.SkillTree;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEventHandler;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class SkillTreeIconMenus {
	
	private ChatColor pri = ChatColor.AQUA;
	private ChatColor sec = ChatColor.GREEN;
	private OptionClickEventHandler handler;
	
	public void init() {
		handler = new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Player p = event.getPlayer();
				event.setWillClose(false);
				event.setWillDestroy(true);
				if (event.getPosition() > 0) {
					processClick(event);
					if (event.getMenu().getName().contains("Strength")) {
						strength(p).open(p);
					} else if (event.getMenu().getName().contains("Dexterity")) {
						dexterity(p).open(p);
					} else if (event.getMenu().getName().contains("Wisdom")) {
						wisdom(p).open(p);
					}
				} else {
					IconMenuHandler.getBaseIconMenu().open(p);
					event.getMenu().destroy();
				}
			}
		};
	}
	
	public IconMenu strength(Player p) {
		IconMenu strengthIconMenu = new IconMenu("Strength Skilltrees", 36, handler, RPGPlus.inst());
		strengthIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Exit", sec + "Click to return to main menu");
		addSkillTree(strengthIconMenu, SkillTree.WARRIOR, p);
		return strengthIconMenu;
	}
	
	public IconMenu dexterity(Player p) {
		IconMenu dexterityIconMenu = new IconMenu("Dexterity Skilltrees", 36, handler, RPGPlus.inst());
		dexterityIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		addSkillTree(dexterityIconMenu, SkillTree.ROGUE, p);
		return dexterityIconMenu;
	}
	
	public IconMenu wisdom(Player p) {
		IconMenu wisdomIconMenu = new IconMenu("Wisdom Skilltrees", 36, handler, RPGPlus.inst());
		wisdomIconMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");		
		addSkillTree(wisdomIconMenu, SkillTree.ELEMENTALIST, p);
		return wisdomIconMenu;
	}
	
	private void processClick(OptionClickEvent event) {
		Player p = event.getPlayer();
		PlayerData playerData = PlayerDataManager.getPlayerData(p);
		Ability ability = SkillTreeHandler.getAbility(format(event.getName()));
		FileConfiguration f = playerData.getFile();
		
		if (f.contains("SkillPoints." + capitalize(ability.getSkillType().toString()))) {
			int skillPoints = f.getInt("SkillPoints." + capitalize(ability.getSkillType().toString()));
			if (skillPoints > 0) {
				if (f.contains("Abilities." + capitalize(ability.getSkillType().toString()) + "." + format(event.getName()))) {
					int level = f.getInt("Abilities." + capitalize(ability.getSkillType().toString()) + "." + format(event.getName()));
					if (level < 3) {
						f.set("Abilities." + capitalize(ability.getSkillType().toString()) + "." + format(event.getName()), (level + 1));
						f.set("SkillPoints." + capitalize(ability.getSkillType().toString()), skillPoints - 1);
						playerData.setFile(f);
						p.sendMessage(ChatColor.GREEN + "You have upgraded " + ability.getName() + " to level " + (level + 1));
					} else {
						p.sendMessage(ChatColor.GREEN + "That skill cannot be upgraded further!");
					}
				} else {
					f.set("Abilities." + capitalize(ability.getSkillType().toString()) + "." + format(event.getName()), 1);
					f.set("SkillPoints." + capitalize(ability.getSkillType().toString()), skillPoints - 1);
					playerData.setFile(f);
					p.sendMessage(ChatColor.GREEN + "You have upgraded " + ability.getName() + " to level " + 1);
				}
			} else {
				p.sendMessage(ChatColor.GREEN + "You do not have enough Skill Points!");
			}
		} else {
			p.sendMessage(ChatColor.GREEN + "You do not have enough Skill Points!");
		}
	}
	
	private String format(String s) {
		return DataBaseManager.abilityToName(ChatColor.stripColor(s));
	}
	
	private void addSkillTree(IconMenu menu, SkillTree skillTree, Player p) {
		List<Ability> lightAbilities = skillTree.getSkills(AbilityType.LIGHT);
		List<Ability> mediumAbilities = skillTree.getSkills(AbilityType.MEDIUM);
		List<Ability> heavyAbilities = skillTree.getSkills(AbilityType.HEAVY);
		List<Ability> ultimateAbilities = skillTree.getSkills(AbilityType.ULTIMATE);
		for (int i = 0; i < lightAbilities.size(); i++) {
			menu.addAbility(1 + i, lightAbilities.get(i), p);
		}
		for (int i = 0; i < mediumAbilities.size(); i++) {
			menu.addAbility(10 + i, mediumAbilities.get(i), p);
		}
		for (int i = 0; i < heavyAbilities.size(); i++) {
			menu.addAbility(19 + i, heavyAbilities.get(i), p);
		}
		for (int i = 0; i < ultimateAbilities.size(); i++) {
			menu.addAbility(28 + i, ultimateAbilities.get(i), p);
		}
	}
	
	private static String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
}
