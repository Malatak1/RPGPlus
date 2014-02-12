package com.Github.Malatak1.RPGPlus.DataTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.Abilities.Ability;

public class PlayerData {
	
	Map<AbilityType, Ability> abilityMap = new HashMap<>();
	HashSet<Party> invites = new HashSet<>();
	
	Party party;
	String name;
	FileConfiguration f;
	
	public PlayerData(String name, FileConfiguration f) {
		this.name = name;
		this.f = f;
	}
	
	public PlayerData(Player player, FileConfiguration f) {
		this.name = player.getName();
		this.f = f;
	}
	
	public Map<AbilityType, Ability> getAbilityMap() {
		return abilityMap;
	}
	
	public void setAbilityMap(Map<AbilityType, Ability> abilityMap) {
		this.abilityMap = abilityMap;
	}
	
	public FileConfiguration getFile() {
		return f;
	}
	
	public void setFile(FileConfiguration f) {
		this.f = f;
	}
	
	public Party getParty() {
		return party;
	}
	
	public void setParty(Party party) {
		this.party = party;
	}
	
	public HashSet<Party> getInvites() {
		return invites;
	}
	
	public void setInvites(HashSet<Party> invites) {
		this.invites = invites;
	}
}
