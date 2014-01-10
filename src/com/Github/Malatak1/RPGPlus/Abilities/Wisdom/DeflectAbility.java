package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class DeflectAbility implements CastableAbility {
	
	HashSet<Byte> transparent; 
	
	@Override
	public String getName() {
		return "Deflect";
	}

	@Override
	public String getInfo() {
		return "Send projectiles back towards their sender";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.REDSTONE);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.HEAVY;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.WISDOM;
	}

	@Override
	public int manaCost() {
		return 4;
	}
	
	@Override
	public int staminaCost() {
		return 0;
	}

	@Override
	public int cooldownTime() {
		return 0;
	}
	
	@Override
	public void cast(Player p, int power) {
		@SuppressWarnings("deprecation")
		Vector to = p.getTargetBlock(transparent, 100).getLocation().toVector().zero();
		for (Entity e : p.getNearbyEntities(6 * power, 6 * power, 6 * power)) {
			if (e instanceof Projectile) {
				Vector from = e.getVelocity();
				e.setVelocity(to.subtract(from).multiply(2));
			}
		}
	}

}
