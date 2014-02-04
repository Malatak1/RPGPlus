package com.Github.Malatak1.RPGPlus.Abilities.Dexterity;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class SwiftStepAbility implements CastableAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Swift Step";
	}

	@Override
	public String getInfo() {
		return "Increase speed for a short amount of time.";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.FEATHER);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.LIGHT;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.DEXTERITY;
	}

	@Override
	public int cooldownTime() {
		return 360;
	}

	@Override
	public void cast(Player p, int power) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80 + (20 * power), 2));
		final FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		try {
			fplayer.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).withFade(Color.BLUE).flicker(true).build());
			fplayer.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().with(Type.BURST).withColor(Color.GRAY).withFade(Color.NAVY).flicker(true).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1F, 0F);
	}

}
