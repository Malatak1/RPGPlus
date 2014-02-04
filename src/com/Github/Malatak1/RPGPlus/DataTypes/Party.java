package com.Github.Malatak1.RPGPlus.DataTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;

public class Party {
	private final HashSet<String> members = new HashSet<String>();
	private final HashSet<String> invites = new HashSet<String>();
	private String leader;
	
	public Party(String leader) {
		this.leader = leader;
	}
	
	public HashSet<String> getMembers() {
		return members;
	}
	
	public List<Player> getOnlineMembers() {
		List<Player> onlineMembers = new ArrayList<Player>();
		
		for (String memberName : members) {
			Player member = RPGPlus.inst().getServer().getPlayer(memberName);
			if (member != null) {
				onlineMembers.add(member);
			}
		}
		return onlineMembers;
	}
	
	public String getLeader() {
		return leader;
	}
	
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public void addPlayer(Player p) {
		addPlayer(p.getName());
	}
	
	public void addPlayer(String p) {
		members.add(p);
	}
	
	public void removePlayer(Player p) {
		removePlayer(p.getName());
	}
	
	public void removePlayer(String p) {
		members.remove(p);
	}
	
	public boolean hasPlayer(Player p) {
		return hasPlayer(p.getName());
	}
	
	public boolean hasPlayer(String p) {
		return members.contains(p);
	}
	
	public boolean containsInvite(Player p) {
		return containsInvite(p.getName());
	}
	
	public boolean containsInvite(String p) {
		return invites.contains(p);
	}
	
	public void addInvite(Player p) {
		addInvite(p.getName());
	}
	
	public void addInvite(String p) {
		invites.add(p);
	}
	
	public void removeInvite(Player p) {
		removeInvite(p.getName());
	}
	
	public void removeInvite(String p) {
		invites.remove(p);
	}
}
