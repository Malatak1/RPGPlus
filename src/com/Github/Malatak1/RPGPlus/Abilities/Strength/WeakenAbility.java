package com.Github.Malatak1.RPGPlus.Abilities.Strength;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Github.Malatak1.RPGPlus.Abilities.StaminaAbility;
import com.Github.Malatak1.RPGPlus.Abilities.TargetableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class WeakenAbility implements TargetableAbility, StaminaAbility {

	@Override
	public String getName() {
		return "Weaken";
	}

	@Override
	public String getInfo() {
		return "Weaken enemies after each strike";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.NETHER_STALK);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.LIGHT;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.STRENGTH;
	}

	@Override
	public int staminaCost() {
		return 5;
	}
	
	@Override
	public void onTarget(LivingEntity target, Player damager, int power) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40 + (20 * power), 0, true), true);
		damager.playEffect(target.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
	}

}
