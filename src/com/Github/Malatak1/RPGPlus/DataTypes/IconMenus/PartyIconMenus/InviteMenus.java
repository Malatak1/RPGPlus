package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.PartyIconMenus;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.Party;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenu.OptionClickEvent;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;

public class InviteMenus {
	
	static ChatColor pri = IconMenuHandler.pri;
	static ChatColor sec = IconMenuHandler.sec;
	static ChatColor ter = IconMenuHandler.ter;
	
	public IconMenu leaderInvites(final Player p) {
		Player[] online = Bukkit.getOnlinePlayers();
		IconMenu leaderInvites = new IconMenu("Invite Players", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Bukkit.getLogger().info(ChatColor.stripColor(event.getName()));
				Player p = event.getPlayer();
				if (event.getPosition() >= 9) {
					Player player = Bukkit.getPlayerExact(ChatColor.stripColor(event.getName()));
					if (player != null && !PartyManager.inSameParty(player, p)) {
						Party party = PartyManager.getParty(p.getName());
						if (!PlayerDataManager.getPlayerData(player).getInvites().contains(party)) {
							PlayerDataManager.getPlayerData(player).getInvites().add(party);
							p.closeInventory();
							leaderInvites(p).open(p);
						} else {
							PlayerDataManager.getPlayerData(player).getInvites().remove(party);
							p.closeInventory();
							leaderInvites(p).open(p);
						}
					}
				} else {
					
				}
				event.setWillClose(false);
				event.setWillDestroy(true);
			}
		}, RPGPlus.inst());
		int i = 9;
		Party party = PartyManager.getParty(p.getName());
		for (int j = 0; j < online.length; j++) {
			if (!PartyManager.inSameParty(p, online[j]) && !PlayerDataManager.getPlayerData(online[j]).getInvites().contains(party)) {
				leaderInvites.setOption(i, skull((byte)3), pri + online[j].getName(), sec + "Click to invite to party");
				i++;
			} else if (PlayerDataManager.getPlayerData(online[j]).getInvites().contains(party)) {
				leaderInvites.setOption(i, skull((byte)2), pri + online[j].getName(), sec + "Click to revoke invite");
				i++;
			}
		}
		return leaderInvites;
	}
	
	public IconMenu playerInvites(final Player p) {
		HashSet<Party> invites = PlayerDataManager.getPlayerData(p).getInvites();
		IconMenu playerInvites = new IconMenu("Invitations", 45, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Bukkit.getLogger().info(ChatColor.stripColor(event.getName()));
				Player p = event.getPlayer();
				if (event.getPosition() >= 9) {
					PartyManager.joinParty(p, ChatColor.stripColor(event.getName()));
					p.closeInventory();
					playerInvites(p).open(p);
				} else {
					p.closeInventory();
					new BasePartyMenus().baseMenu(p).open(p);
				}
				event.setWillClose(false);
				event.setWillDestroy(true);
			}
		}, RPGPlus.inst());
		if (invites.size() > 0) {
			int i = 9;
			for (Party party : invites) {
				HashSet<String> names = party.getMembers();
				String[] info = new String[names.size() - 1];
				String leader = "Party";
				int j = 0;
				for (String name : names) {
					if (!party.getLeader().equals(name)){
						info[j] = sec + name;
					} else {
						leader = name;
					} j++;
				}
				playerInvites.setOption(i, skull((byte)3), pri + leader, info); 
				i++;
			}
			playerInvites.setOption(0, wool((byte)14), pri + "" + invites.size() + " invites", "Return to main party menu");
		} else {
			playerInvites.setOption(0, wool((byte)14), pri + "No invites", "Return to main party menu");
		}
		return playerInvites;
	}
	
	private ItemStack skull(byte data) {
		return new ItemStack(Material.SKULL_ITEM, 1, data);
	}
	
	private ItemStack wool(byte data) {
		return new ItemStack(Material.WOOL, 1, data);
	}
}
