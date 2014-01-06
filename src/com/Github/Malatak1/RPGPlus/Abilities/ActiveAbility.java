package com.Github.Malatak1.RPGPlus.Abilities;

import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public interface ActiveAbility extends Ability {
	
	public AbilityType getAbilityType();
	public SkillType getSkillType();
	public int manaCost();
	
	public void cast(Player p, int power);
	
}
