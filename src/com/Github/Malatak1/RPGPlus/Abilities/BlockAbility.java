package com.Github.Malatak1.RPGPlus.Abilities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface BlockAbility extends Ability {
	
	public void onBlock(Player blocker, Entity damager, int power);
	
}
