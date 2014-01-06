package com.Github.Malatak1.RPGPlus.Util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.Github.Malatak1.RPGPlus.RPGPlus;

public class ProjectileEffect extends BukkitRunnable {
	
	Projectile p;
	Type type;
	Color color;
	long delay;
	
	public ProjectileEffect(Projectile p, Type type, Color color, long delay) {
		this.p = p;
		this.type = type;
		this.color = color;
		this.delay = delay;
	}
	
	@Override
	public void run() {
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		if (!p.isDead() && !p.isOnGround()) {
			try {
				fplayer.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().with(type).withColor(color).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
			@SuppressWarnings("unused")
			BukkitTask task = new ProjectileEffect(p, type, color, delay).runTaskLater(RPGPlus.inst(), delay);
		}
	}
}
