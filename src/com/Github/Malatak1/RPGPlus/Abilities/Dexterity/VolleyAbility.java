package com.Github.Malatak1.RPGPlus.Abilities.Dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.ProjectileAbility;
import com.Github.Malatak1.RPGPlus.Abilities.StaminaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.VolleyArrowRunnable;

public class VolleyAbility implements ProjectileAbility, StaminaAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Volley";
	}

	@Override
	public String getInfo() {
		return "Call in a volley with a flare arrow";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.EYE_OF_ENDER);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.ULTIMATE;
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
	public int cooldownTime() {
		return 400;
	}

	@Override
	public void onShoot(Projectile projectile, int power) {
		@SuppressWarnings("unused")
		BukkitTask volleyRunnable = new VolleyArrowRunnable((Player) projectile.getShooter(), (Arrow) projectile, power).runTaskLater(RPGPlus.inst(), 5);
		
	}
	
}
