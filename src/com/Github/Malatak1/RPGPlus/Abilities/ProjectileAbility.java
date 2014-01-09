package com.Github.Malatak1.RPGPlus.Abilities;

import org.bukkit.entity.Projectile;

public interface ProjectileAbility extends Ability{
	
	public void onShoot(Projectile projectile, int power);
	
}
