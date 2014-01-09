package com.Github.Malatak1.RPGPlus.Util;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Github.Malatak1.RPGPlus.RPGPlus;

public class VolleyArrowRunnable extends BukkitRunnable {
	
	Player p;
	Arrow a;
	int power;
	
	public VolleyArrowRunnable(Player p, Arrow a, int power) {
		this.p = p;
		this.a = a;
		this.power = power;
	}

	@SuppressWarnings({ "deprecation"})
	@Override
	public void run() {
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		
		final Location spawnLoc = p.getLocation();
		spawnLoc.setY(spawnLoc.getY() + 20);
		
		final Vector direction = new Vector();
		direction.setY(-2);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.inst(), new Runnable() {
			public void run() {
				if (!a.isOnGround() && !a.isDead()) {
					p.playEffect(a.getLocation(), Effect.SMOKE, 1);
					Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.inst(), this, 3);
				} else {
					final Location loc = a.getLocation();
					final Location effectLoc = loc;
					effectLoc.setY(effectLoc.getY() + 8);
					
					final Location finalLoc = a.getLocation();
					finalLoc.setY(finalLoc.getY() + 20);
					
					final HashSet<Arrow> arrowList = new HashSet<>();
					
					for (int i = 0; i < (4 * power) + 8; i++) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.inst(), new Runnable() {
							public void run() {
								try {
									fplayer.playFirework(a.getWorld(), effectLoc, FireworkEffect.builder().with(Type.STAR).withColor(Color.GREEN).build());
								} catch (Exception e) {
									e.printStackTrace();
								}
								for (int j = 0; j < 8; j++) {
				                    Arrow a = p.getWorld().spawnArrow(finalLoc, direction, 20, 12);
				                    a.setShooter(p);
				                    arrowList.add(a);
								}
							}
						}, (i + 1) * 5);
					}
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.inst(), new Runnable() {
						public void run() {
							for (Arrow a: arrowList) {
								a.remove();
							}
						}
					}, 40 * (power + 1));
				}
			}
		}, 4);
		
	}
	
}
