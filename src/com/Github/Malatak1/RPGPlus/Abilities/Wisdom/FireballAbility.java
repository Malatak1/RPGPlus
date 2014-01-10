package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.ProjectileEffect;
import com.Github.Malatak1.RPGPlus.Util.ProjectileRemover;

public class FireballAbility implements CastableAbility {

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
	public int manaCost() {
		return 10;
	}
	
	@Override
	public int staminaCost() {
		return 0;
	}

	@Override
	public int cooldownTime() {
		return 0;
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void cast(Player p, int power) {
		Location loc = (Location) p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
		Fireball fireball = p.getWorld().spawn(loc, Fireball.class);
		fireball.setYield(power + 2);
        
		fireball.setShooter(p);
		BukkitTask projectileEffect = new ProjectileEffect((Projectile) fireball, Type.BALL, Color.RED, 3).runTaskLater(RPGPlus.inst(), 4);
		BukkitTask projectileRemove = new ProjectileRemover(fireball).runTaskLater(RPGPlus.inst(), (100L + (10 * power)));
		
        p.playEffect(loc, Effect.SMOKE, 5);
        p.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 5);
        p.playEffect(loc, Effect.BLAZE_SHOOT, 1);
	}

}
