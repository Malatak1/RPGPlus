package com.Github.Malatak1.RPGPlus.Party;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.DataTypes.Party;
import com.Github.Malatak1.RPGPlus.DataTypes.PlayerData;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class PartyManager {
	
	private static List<Party> parties = new ArrayList<>();
	
	public static boolean checkPartyExistence(String partyName) {
		if (getParty(partyName) == null) {
			return false;
		} else
			return true;
	}
	
	public static void joinParty(Player p, String partyLeader) {
		if (getPlayersParty(p) == null && getParty(partyLeader) != null) {
			getParty(partyLeader).addPlayer(p);
			PlayerDataManager.getPlayerData(p).setParty(getParty(partyLeader));
			PlayerDataManager.getPlayerData(p).getInvites().remove(getParty(partyLeader));
		}
	}
	
	public static void leaveParty(Player p) {
		if (getPlayersParty(p) != null) {
			getPlayersParty(p).removePlayer(p);
		}
		PlayerData playerData = PlayerDataManager.getPlayerData(p);
		playerData.setParty(null);
		PlayerDataManager.setPlayerData(p, playerData);
	}
	
	public static void disbandParty(Party party) {
		for (Player player : party.getOnlineMembers()) {
			party.removePlayer(player);
			PlayerDataManager.getPlayerData(player).setParty(null);
			player.sendMessage(ChatColor.GREEN + "Your party has been disbanded.");
		}
		parties.remove(party);
	}
	
	public static Party getPlayersParty(Player p) {
		for (Party party : parties) {
			if (party.hasPlayer(p)) {
				return party;
			}
		}
		return null;
	}
	
	public static Party getParty(String partyLeader) {
        for (Party party : parties) {
            if (party.getLeader().equals(partyLeader)) {
                return party;
            }
        }
        return null;
    }
	
	public static boolean inSameParty(Player first, Player second) {
		Party party1 = getPlayersParty(first);
		Party party2 = getPlayersParty(second);
		
		if (party1 == null || party2 == null) {
			return false;
		}
		return party1.equals(party2);
	}
	
	public static void createParty(Player p) {
		Party party = new Party(p.getName());
		parties.add(party);
	}
	
	public static List<Party> getParties() {
		return parties;
	}
	
}
