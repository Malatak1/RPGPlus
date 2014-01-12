package com.Github.Malatak1.RPGPlus.Database;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;

/**
 * TODO Add a synchronized repeating task along with a timer
 * for more efficient cooldown management, instead of the
 * current method of a delayed task to remove it from the
 * cooldown list.
 */

public class CooldownManager {
	
	Map<Player, boolean[]> cooldownMap = new HashMap<Player, boolean[]>();
	
	public void addAbility(Player p, CooldownAbility ability) {
		boolean[] abilityList = cooldownMap.get(p);
		int temp = 0;
		switch (ability.getAbilityType()) {
		case LIGHT: temp = 0; break;
		case MEDIUM: temp = 1; break;
		case HEAVY: temp = 2; break;
		case ULTIMATE: temp = 3; break;
		}
		final int index = temp;
		
		if (abilityList == null) {
			abilityList = new boolean[4];
		}
		abilityList[index] = true;
		cooldownMap.put(p, abilityList);
		
		final Player fp = p;
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RPGPlus.inst(), new BukkitRunnable() {
            public void run() {
            	boolean[] aList = cooldownMap.get(fp);
                aList[index] = false;
                cooldownMap.put(fp, aList);
            }
        }, ability.cooldownTime());
	}
	
	public boolean onCooldown(Player p, AbilityType type) {
		int temp = 0;
		switch (type) {
		case LIGHT: temp = 0; break;
		case MEDIUM: temp = 1; break;
		case HEAVY: temp = 2; break;
		case ULTIMATE: temp = 3; break;
		}
		boolean[] coolDowns = cooldownMap.get(p);
		if (coolDowns != null) {
			return coolDowns[temp];
		} else {
			coolDowns = new boolean[4];
			return false;
		}

	}
	
//	public void startCooldownTimer() {
//		Bukkit.getServer().getScheduler()
//				.scheduleSyncRepeatingTask(RPGPlus.inst(), new BukkitRunnable() {
//					public void run() {
//					    Iterator<Entry<Player, int[]>> it = coolDownMap.entrySet().iterator();
//					    while (it.hasNext()) {
//					        Map.Entry<Player, int[]> pairs = (Map.Entry<Player, int[]>)it.next();
//					        int[] value = pairs.getValue();
//					        for (int i = 0; i < value.length; i++) {
//								if (value[i] < 0) value[i]--;
//							}
//	                        coolDownMap.put(pairs.getKey(), value);
//					        it.remove();
//					    }
//					}
//				}, 0L, 20L);
//	}S
}
