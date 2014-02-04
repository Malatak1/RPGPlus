package com.Github.Malatak1.RPGPlus.Abilities.Dexterity;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.ProjectileAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class DrainArrowAbility implements ProjectileAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Drain Arrow";
	}

	@Override
	public String getInfo() {
		return "Drain mana from enemies near where your shot lands.";
	}

	@Override
	public ItemStack getIcon() {
		return null;
	}

	@Override
	public AbilityType getAbilityType() {
		return null;
	}

	@Override
	public SkillType getSkillType() {
		return null;
	}

	@Override
	public int cooldownTime() {
		return 0;
	}

	@Override
	public void onShoot(Projectile projectile, int power) {
		
	}

}
