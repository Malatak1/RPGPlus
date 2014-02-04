package com.Github.Malatak1.RPGPlus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.Party;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;

public class PartyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "leave":
					Party party = PartyManager.getPlayersParty(p);
					if (party != null) {
						if (party.getLeader().equals(p.getName())) {
							p.sendMessage(ChatColor.GREEN + "You can't leave the party if you are the leader!");
						} else {
							PartyManager.leaveParty(p);
							p.sendMessage(ChatColor.GREEN + "You have left the party.");
						}
					} else {
						p.sendMessage(ChatColor.GREEN + "You are not in a party!");
					} return true;
				case "disband":
					Party party1 = PartyManager.getPlayersParty(p);
					if (party1 != null) {
						if (party1.getLeader().equals(p.getName())) {
							PartyManager.disbandParty(party1);
						} else {
							p.sendMessage(ChatColor.GREEN + "You are not the party leader!");
						}
					} else {
						p.sendMessage(ChatColor.GREEN + "You are not in a party!");
					} return true;
				case "info":
					Party party2 = PartyManager.getPlayersParty(p);
					if (party2 != null) {
						p.sendMessage(ChatColor.GREEN + "Party Information:");
						for (String member : party2.getMembers()) {
							StringBuilder sb = new StringBuilder(member);
							if (party2.getLeader().equals(member)) {
								sb.append("*");
							}
							ChatColor online = ChatColor.YELLOW;
							if (RPGPlus.inst().getServer().getPlayer(member) == null) {
								online = ChatColor.RED;
							}
							p.sendMessage(online + " - " + sb.toString());
						}
					} return true;
				}
			}
			if (args.length == 2) {
				switch (args[0].toLowerCase()) {
				case "join":
					Party currentParty = PartyManager.getPlayersParty(p);
					Party party = PartyManager.getParty(args[1]);
					if (currentParty == null) {
						if (party != null) {
							if (party.containsInvite(p)) {
								party.addPlayer(p);
								party.removeInvite(p);
								p.sendMessage(ChatColor.GREEN + "You have joined the party.");
								for (Player member : party.getOnlineMembers()) {
									member.sendMessage(ChatColor.GREEN + p.getName() + " has joined the party.");
								}
							} else {
								p.sendMessage(ChatColor.GREEN + "You must be invited before joining a party!");
							}
						} else {
							p.sendMessage(ChatColor.GREEN + "That party does not exist!");
						}
					} else {
						p.sendMessage(ChatColor.GREEN + "You are already in a party!");
					} return true;
				case "create":
					if (PartyManager.getPlayersParty(p) == null) {
						if (!PartyManager.checkPartyExistence(args[1])) {
							PartyManager.createParty(p);
							Party newParty = PartyManager.getParty(args[1]);
							newParty.addPlayer(p);
							p.sendMessage(ChatColor.GREEN + "You have created a new party");
						} else {
							p.sendMessage(ChatColor.GREEN + "That name is already taken!");
						}
					} else {
						p.sendMessage(ChatColor.GREEN + "You are already in a party!");
					} return true;
				case "invite":
					Party partyInvite = PartyManager.getPlayersParty(p);
					Player toInvite = RPGPlus.inst().getServer().getPlayer(args[1]);
					if (partyInvite != null && toInvite != null) {
						if (partyInvite.getLeader().equals(p.getName())) {
							if (!partyInvite.containsInvite(toInvite)) {
								partyInvite.addInvite(toInvite);
							}
							p.sendMessage(ChatColor.GREEN + toInvite.getName() + " has been invited to the party.");
							toInvite.sendMessage(ChatColor.GREEN + "You have been invited to " + ChatColor.YELLOW + partyInvite.getLeader());
							toInvite.sendMessage(ChatColor.GREEN + "To accept, do: /party join " + partyInvite.getLeader());
						} else {
							p.sendMessage(ChatColor.GREEN + "You are not the party leader!");
						}
					} else {
						p.sendMessage(ChatColor.GREEN + "Error: Either your party doesn't exist, or the target player is not online.");
					} return true;
				}
			}			
		}
		return false;
	}
}
