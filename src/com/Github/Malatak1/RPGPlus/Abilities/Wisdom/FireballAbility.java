package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.Abilities.ActiveAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class FireballAbility implements ActiveAbility {

	@Override
	public String getName() {
		return "Fireball";
	}

	@Override
	public String getInfo() {
		return "A powerful ball of fire";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.FIREBALL);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.MEDIUM;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.WISDOM;
	}

	@Override
	public int level() {
		return 0;
	}

	@Override
	public void cast(Player p, int power) {
		Location loc = (Location) p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
		Fireball fireball = p.getWorld().spawn(loc, Fireball.class);
		fireball.setYield(power + 2);
	}

}
