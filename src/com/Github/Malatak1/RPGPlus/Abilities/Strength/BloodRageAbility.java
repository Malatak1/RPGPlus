package com.Github.Malatak1.RPGPlus.Abilities.Strength;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.StaminaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class BloodRageAbility implements CastableAbility, StaminaAbility {

	@Override
	public String getName() {
		return "Blood Rage";
	}

	@Override
	public String getInfo() {
		return "Gain strength, at the cost of your health";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BLAZE_POWDER);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.MEDIUM;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.STRENGTH;
	}
	
	@Override
	public int staminaCost() {
		return 10;
	}

	@Override
	public void cast(Player p, int power) {
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		if (((Damageable)p).getHealth() - 4 > 0) {
			p.setHealth(((Damageable)p).getHealth() - 4);
		} else p.setHealth(0D);

		if (!p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120 + (40 * power), 0));
		} else {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
					int dur = effect.getDuration();
					p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, dur + 120 + (40 * power), 0));
				}
			}
		}
		p.playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
		try {
			fplayer.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().with(Type.BURST).withColor(Color.MAROON).withFade(Color.BLACK).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 1F, 0F);
	}

}
