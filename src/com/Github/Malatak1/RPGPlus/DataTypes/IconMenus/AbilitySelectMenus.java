package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.FireballAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.FireboltAbility;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.FreezingWindsAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class AbilitySelectMenus {
	
	ChatColor pri = IconMenuHandler.pri;
	ChatColor sec = IconMenuHandler.sec;
	
	DataBaseManager db;
	
	public IconMenu createAbilityMenu() {
		
		db = new DataBaseManager(RPGPlus.inst());
		
		IconMenu abilitySelectMenu = new IconMenu("Ability Selection", 9,
				new IconMenu.OptionClickEventHandler() {

					@Override
					public void onOptionClick(OptionClickEvent event) {
						Player p = event.getPlayer();

						switch (event.getPosition()) {

						case 1:
							db.setAbility(p, new FireballAbility());
							p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
							p.closeInventory();
							createAbilityMenu().open(p);
							break;
						case 2:
							db.setAbility(p, new FireboltAbility());
							p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
							p.closeInventory();
							createAbilityMenu().open(p);
							break;
						case 3:
							db.setAbility(p, new FreezingWindsAbility());
							p.sendMessage(ChatColor.YELLOW + "You have selected " + event.getName());
							p.closeInventory();
							createAbilityMenu().open(p);
							break;

						default:
							p.sendMessage(ChatColor.YELLOW
									+ "That was not clickable!");

						}
						event.setWillClose(false);
						event.setWillDestroy(true);
					}
				}, RPGPlus.inst());

		abilitySelectMenu.setOption(0, new ItemStack(Material.PAPER), pri + "Info", sec + "Use the menus to customize your character");
		abilitySelectMenu.addAbility(1, new FireballAbility());
		abilitySelectMenu.addAbility(2, new FireboltAbility());
		abilitySelectMenu.addAbility(3, new FreezingWindsAbility());
		return abilitySelectMenu;
		
	}
	
}
