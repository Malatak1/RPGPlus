package com.Github.Malatak1.RPGPlus.Util;

import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileRemover extends BukkitRunnable{
	
	Projectile p;
	
	public ProjectileRemover(Projectile p) {
		
		this.p = p;
		
	}
	
	@Override
	public void run() {
		if (!p.isDead()) p.remove();
	}

}
