package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;


import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.Github.Malatak1.RPGPlus.Abilities.ActiveAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class FireboltAbility implements ActiveAbility {

	@Override
	public String getName() {
		return "Firebolt";
	}

	@Override
	public String getInfo() {
		return "A mid ranged flaming bolt";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BLAZE_ROD);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.LIGHT;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.WISDOM;
	}

	@Override
	public int level() {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void cast(Player p, int power) {
        Vector direction = p.getEyeLocation().getDirection().multiply(1.5);
        Projectile projectile = p.getWorld().spawn(p.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Arrow.class);
        projectile.setShooter(p);
        projectile.setVelocity(direction.multiply(0.75 + (0.5 * power)));
        projectile.setFireTicks(1200);
        
		projectile.setShooter(p);
		
        Location loc = p.getLocation();
        
        p.playEffect(loc, Effect.SMOKE, 5);
        p.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 5);
        p.playEffect(loc, Effect.BLAZE_SHOOT, 1);
	}
	
}
