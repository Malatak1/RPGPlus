package com.Github.Malatak1.RPGPlus.Party;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.DataTypes.Party;

public class PartyManager {
	
	private static List<Party> parties = new ArrayList<>();
	
	public static boolean checkPartyExistence(String partyName) {
		if (getParty(partyName) == null) {
			return false;
		} else
			return true;
	}
	
	public static void joinParty(Player p, String partyName) {
		if (getPlayersParty(p) == null && getParty(partyName) != null) {
			getParty(partyName).addPlayer(p);
		}
	}
	
	public static void leaveParty(Player p) {
		if (getPlayersParty(p) != null) {
			getPlayersParty(p).removePlayer(p);
		}
	}
	
	public static void disbandParty(Party party) {
		for (Player player : party.getOnlineMembers()) {
			party.removePlayer(player);
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
