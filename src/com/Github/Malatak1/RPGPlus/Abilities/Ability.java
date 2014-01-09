package com.Github.Malatak1.RPGPlus.Abilities;

import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public interface Ability {
	
	public String getName();
	public String getInfo();
	public ItemStack getIcon();
	
	public AbilityType getAbilityType();
	public SkillType getSkillType();
	public int manaCost();
	
}
