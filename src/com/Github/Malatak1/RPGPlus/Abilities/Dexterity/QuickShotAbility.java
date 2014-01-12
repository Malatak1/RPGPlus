package com.Github.Malatak1.RPGPlus.Abilities.Dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class QuickShotAbility implements Ability, CooldownAbility, CastableAbility {

	@Override
	public String getName() {
		return "Quick Shot";
	}

	@Override
	public String getInfo() {
		return "Fire an arrow in an instant";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.FLINT);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.LIGHT;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.DEXTERITY;
	}
	
	@Override
	public int cooldownTime() {
		return 100;
	}
	
	@Override
	public void cast(Player p, int power) {
		Vector direction = p.getEyeLocation().getDirection().multiply(1.25);
		Arrow a = p.getWorld().spawnArrow(p.getEyeLocation(), direction, 5 + power, 2);
		a.setShooter(p);
		a.setTicksLived(1100);
	}
}
