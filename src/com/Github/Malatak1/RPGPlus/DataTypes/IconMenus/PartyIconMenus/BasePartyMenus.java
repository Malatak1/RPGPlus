package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.PartyIconMenus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.Party;
import com.Github.Malatak1.RPGPlus.DataTypes.PlayerData;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEventHandler;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;

public class BasePartyMenus {
	
	static ChatColor pri = IconMenuHandler.pri;
	static ChatColor sec = IconMenuHandler.sec;
	static ChatColor ter = IconMenuHandler.ter;
	
	private OptionClickEventHandler eventHandler = new IconMenu.OptionClickEventHandler() {
		@Override
		public void onOptionClick(OptionClickEvent event) {
			Player p = event.getPlayer();
			Bukkit.getLogger().info(ChatColor.stripColor(event.getName()));
			switch (ChatColor.stripColor(event.getName())) {
			case "Create Party":
				PartyManager.createParty(p);
				Party newParty = PartyManager.getParty(p.getName());
				newParty.addPlayer(p);
				PlayerData data = PlayerDataManager.getPlayerData(p);
				data.setParty(newParty);
				PlayerDataManager.setPlayerData(p, data);
				p.closeInventory();
				baseMenu(p).open(p);
				p.sendMessage(ChatColor.GREEN + "You have created a new party");
				break;
			case "Leave Party":
				PartyManager.leaveParty(p);
				p.sendMessage(pri + "You have left that party");
				p.closeInventory();
				baseMenu(p).open(p);
			case "Disband Party":
				PartyManager.disbandParty(PartyManager.getParty(p.getName()));
				p.closeInventory();
				baseMenu(p).open(p);
				break;
			case "Refresh":
				p.closeInventory();
				baseMenu(p).open(p);
				break;
			case "Invite Players":
				p.closeInventory();
				new InviteMenus().leaderInvites(p).open(p);
				break;
			case "Invitations":
				p.closeInventory();
				new InviteMenus().playerInvites(p).open(p);
				break;
			default: p.sendMessage(ChatColor.YELLOW + "That was not clickable!"); 
			Bukkit.getLogger().info(event.getName());
			break;
			}
			event.setWillClose(false);
			event.setWillDestroy(true);
		}
	};
	
	public IconMenu baseMenu(Player p) {
		if (PlayerDataManager.getPlayerData(p).getParty() != null) {
			if (PlayerDataManager.getPlayerData(p).getParty().getLeader().equals(p.getName())) {
				IconMenu baseMenu = new IconMenu("Party Management", size(p), eventHandler, RPGPlus.inst());
				generatePartyIcons(baseMenu, p);
				return baseMenu;
			} else {
				IconMenu baseMenu = new IconMenu("Current Party", size(p), eventHandler, RPGPlus.inst());
				generatePartyIcons(baseMenu, p);
				return baseMenu;
			}
		} else {
			IconMenu baseMenu = new IconMenu("No Party", size(p), eventHandler, RPGPlus.inst());
			generatePartyIcons(baseMenu, p);
			return baseMenu;
		}
	}
	
	private int size(Player p) {
		Party party = PlayerDataManager.getPlayerData(p).getParty();
		if (party != null) {
			if (party.getMembers().size() < 9) {
				return 18;
			} else if (party.getMembers().size() < 18) {
				return 27;
			} else if (party.getMembers().size() < 27) {
				return 36;
			} else if (party.getMembers().size() < 36) {
				return 45;
			} else {
				return 54;
			}
		} else return 9;
	}
	
	private void generatePartyIcons(IconMenu menu, Player p) {
		PlayerData playerData = PlayerDataManager.getPlayerData(p);
		Party party = playerData.getParty();
		if (party != null) {
			if (!party.getLeader().equals(p.getName())) {
				/** if player is NOT leader **/
				menu.setOption(0, wool((byte)10,1), pri + "Refresh", sec + "Refresh menu information");
				menu.setOption(1, wool((byte)14,1), pri + "Leave Party", sec + "Leave your current party");
			} else {
				/** if player is leader **/
				menu.setOption(0, wool((byte)10,1), pri + "Refresh", sec + "Refresh menu information");
				menu.setOption(1, wool((byte)14,1), pri + "Disband Party", sec + "Disband your current party");
				menu.setOption(2, wool((byte)10,1), pri + "Invite Players", sec + "Invite players to your party");
			}
			addPlayers(menu, party);
		} else {
			int invites = playerData.getInvites().size();
			menu.setOption(0, wool((byte)13, 1), pri + "Create Party", sec + "Create a new party, with you as leader");
			menu.setOption(1, wool((byte)11, invites), pri + "Invitations", new String[]{sec + "Manage personal party invitations", ter + "" + invites + " pending"});
		}
	}
	
	private void addPlayers(IconMenu menu, Party party) {
		int i = 9;
		for (String name : party.getMembers()) {
			if (Bukkit.getPlayerExact(name) != null) {
				Player p = Bukkit.getPlayerExact(name);
				PlayerData playerData = PlayerDataManager.getPlayerData(p);
				int level = calculateLevel(playerData.getFile());
				ItemStack icon = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
				List<String> info = new ArrayList<String>();
				info.add(sec + "Level " + level);
				info.add(sec + "" + ((Damageable)p).getHealth() + " Health");
				if (party.getLeader().equals(p.getName())) {
					info.add(ter + "Party Leader");
				}
				menu.setOption(i, icon, pri + name, info.toArray(new String[info.size()]));
			} else {
				ItemStack icon = new ItemStack(Material.SKULL_ITEM);
				menu.setOption(i, icon, name, ChatColor.DARK_RED + "Offline");
			}
			i++;
		}
	}
	
	private ItemStack wool(byte data, int amount) {
		if (amount == 0) {
			amount++;
		}
		return new ItemStack(Material.WOOL, amount, data);
	}
	
	private int calculateLevel(FileConfiguration f) {
		int str = f.getInt("Skills.Strength");
		int dex = f.getInt("Skills.Dexterity");
		int wis = f.getInt("Skills.Wisdom");
		int con = f.getInt("Skills.Constitution");
		
		return Math.max(str, Math.max(dex, wis)) + con;
	}
}
