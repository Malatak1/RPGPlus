package com.Github.Malatak1.RPGPlus.Abilities.Strength;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.StaminaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class WolfAspectAbility implements CastableAbility, CooldownAbility, StaminaAbility {

	@Override
	public String getName() {
		return "Wolf Aspect";
	}

	@Override
	public String getInfo() {
		return "Imbue yourself with speed and strength";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.INK_SACK, 1, (byte) 15);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.ULTIMATE;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.STRENGTH;
	}
	
	@Override
	public int staminaCost() {
		return 60;
	}
	
	@Override
	public int cooldownTime() {
		return 2000;
	}
	
	@Override
	public void cast(Player p, int power) {
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 800, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 800, power - 1));
		try {
			fplayer.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).withFade(Color.SILVER).flicker(true).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 1F, 0F);
	}
}
