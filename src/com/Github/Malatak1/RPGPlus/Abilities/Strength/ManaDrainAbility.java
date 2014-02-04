package com.Github.Malatak1.RPGPlus.Abilities.Strength;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.TargetableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class ManaDrainAbility implements TargetableAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Mana Drain";
	}

	@Override
	public String getInfo() {
		return "Steal your target's mana from them.";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.INK_SACK, 1, (byte) 6);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.HEAVY;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.STRENGTH;
	}

	@Override
	public int cooldownTime() {
		return 200;
	}

	@Override
	public void onTarget(LivingEntity target, Player damager, int power) {
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		if (target instanceof Player && !target.hasMetadata("NPC")) {
			((Player) target).setLevel(((Player) target).getLevel() / 2);
		} else {
			damager.giveExpLevels(25);
		}
		
		try {
			fplayer.playFirework(damager.getWorld(), target.getLocation(), FireworkEffect.builder().with(Type.BURST).withColor(Color.PURPLE).withFade(Color.BLACK).flicker(true).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		damager.playEffect(target.getLocation(), Effect.STEP_SOUND, Material.PORTAL);
	}

}
