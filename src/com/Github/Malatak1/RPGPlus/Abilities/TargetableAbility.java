package com.Github.Malatak1.RPGPlus.Abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface TargetableAbility extends Ability {
	
	public void onTarget(LivingEntity target, Player damager, int power);
	
}
