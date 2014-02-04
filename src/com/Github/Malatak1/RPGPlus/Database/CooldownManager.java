package com.Github.Malatak1.RPGPlus.Database;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;

public class CooldownManager {
	
	Map<Player, long[]> cooldownMap = new HashMap<>();
	
	public void addAbility(Player p, CooldownAbility ability) {
		if (cooldownMap.containsKey(p)) {
			
			int index = getIndex(ability.getAbilityType());
			
			long[] abilityList = cooldownMap.get(p);
			if (System.currentTimeMillis() > cooldownMap.get(p)[index]) {
				abilityList[index] = System.currentTimeMillis() + ((ability.cooldownTime() / 20) * 1000);
			}
			cooldownMap.put(p, abilityList);
		} else {
			long[] cooldowns = new long[4];
			cooldownMap.put(p, cooldowns);
			addAbility(p, ability);
		}
	}
	
	public long getCooldownTime(Player p, CooldownAbility ability) {
		return Math.round((cooldownMap.get(p)[getIndex(ability.getAbilityType())] - System.currentTimeMillis()) / 1000);
	}
	
	public boolean getIsOnCooldown(Player p, CooldownAbility ability) {
		if (cooldownMap.containsKey(p)) {
			return cooldownMap.get(p)[getIndex(ability.getAbilityType())] > System.currentTimeMillis();
		} else return false;
	}
	
	private int getIndex(AbilityType type) {
		switch (type) {
        case LIGHT: return 0;
        case MEDIUM: return 1;
        case HEAVY: return 2;
        case ULTIMATE: return 3;
        default: return 0;
		}
	}
}