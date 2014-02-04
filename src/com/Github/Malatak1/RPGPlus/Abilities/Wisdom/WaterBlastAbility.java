package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.ManaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.ProjectileEffect;

public class WaterBlastAbility implements CastableAbility, ManaAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Water Blast";
	}

	@Override
	public String getInfo() {
		return "A mid distance cone of water";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.POTION);
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
	public int manaCost() {
		return 8;
	}

	@Override
	public int cooldownTime() {
		return 30;
	}
	
	@Override
	public void cast(Player p, int power) {
		
		Potion potion = new Potion(PotionType.SLOWNESS, 1).splash();
		ItemStack itemStack = new ItemStack(Material.POTION);
		potion.apply(itemStack);
		
		Location loc = (Location) p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
		ThrownPotion splash = (ThrownPotion) p.getWorld().spawn(loc, ThrownPotion.class);
		Vector vector = p.getLocation().getDirection().multiply(2);
		splash.setVelocity(vector);
		splash.setItem(itemStack);
		splash.setMetadata("Element", new FixedMetadataValue(RPGPlus.inst(), "Water"));
		splash.setShooter(p);
		
		@SuppressWarnings("unused")
		BukkitTask projectileEffect = new ProjectileEffect((Projectile) splash, Type.BURST, Color.BLUE, 3).runTaskLater(RPGPlus.inst(), 3);
	}

}
