package com.Github.Malatak1.RPGPlus.Abilities;

import org.bukkit.entity.Player;

public interface CastableAbility extends Ability {
	
	public void cast(Player p, int power);
	
}
