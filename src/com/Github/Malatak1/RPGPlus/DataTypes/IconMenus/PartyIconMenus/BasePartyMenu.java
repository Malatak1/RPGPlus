package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.PartyIconMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;

public class BasePartyMenu extends IconMenu {
	
	ChatColor pri = IconMenuHandler.pri;
	ChatColor sec = IconMenuHandler.sec;
	ChatColor ter = IconMenuHandler.ter;
	
	private static String name = "Party Management";
	private static int size = 9;
	
	@SuppressWarnings("unused")
	private Player p;
	
	private static OptionClickEventHandler eventHandler = new OptionClickEventHandler() {
		
		@Override
		public void onOptionClick(OptionClickEvent event) {
			Player p = event.getPlayer();
			
			switch (event.getPosition()) {
			
			case 0:
				if (PartyManager.getParty(p.getName()) != null) {
					PartyManager.createParty(p);
					p.sendMessage("You have created a new party.");
				} else {
					p.sendMessage("You are already in a party!");
				}
				break;
			default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!"); break;
			
			}
			
			event.setWillClose(true);
			event.setWillDestroy(true);
			
		}
	};
	
	public BasePartyMenu(Player p) {
		super(name, size, eventHandler, RPGPlus.inst());
		this.p = p;
        
		setOption(0, new ItemStack(Material.GOLD_INGOT), pri + "Create", sec + "Create a new party");
	}

}
