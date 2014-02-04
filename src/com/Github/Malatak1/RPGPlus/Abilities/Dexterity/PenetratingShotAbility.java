package com.Github.Malatak1.RPGPlus.Abilities.Dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.ProjectileAbility;
import com.Github.Malatak1.RPGPlus.Abilities.StaminaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class PenetratingShotAbility implements ProjectileAbility, StaminaAbility{

	@Override
	public String getName() {
		return "Penetrating Shot";
	}

	@Override
	public String getInfo() {
		return "Deal increased damage against armored foes";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.PUMPKIN_SEEDS);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.MEDIUM;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.DEXTERITY;
	}

	@Override
	public int staminaCost() {
		return 0;
	}

	@Override
	public void onShoot(Projectile projectile, int power) {
		projectile.setMetadata("Penetrating", new FixedMetadataValue(RPGPlus.inst(), "True"));
	}

}
