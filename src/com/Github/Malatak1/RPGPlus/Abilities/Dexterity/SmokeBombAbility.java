package com.Github.Malatak1.RPGPlus.Abilities.Dexterity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class SmokeBombAbility implements CastableAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Smoke Bomb";
	}

	@Override
	public String getInfo() {
		return "Blind and disorient nearby monsters";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.INK_SACK);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.HEAVY;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.DEXTERITY;
	}

	@Override
	public int cooldownTime() {
		return 400;
	}

	@Override
	public void cast(Player p, int power) {
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		for (Entity e : p.getNearbyEntities(10 + (4 * power), 10 + (4 * power), 10 + (4 * power))) {
			if (e instanceof Player && !e.hasMetadata("NPC")) {
				if (!PartyManager.inSameParty(p, (Player) e)) {
					addEffects(e);
				}
			} else addEffects(e);
		}
		List<Location> circle = drawCircle(p, p.getLocation(), 8, 1, true, false, 1);
		for (int i = 0; i < circle.size(); i++) {
			try {
				fplayer.playFirework(p.getWorld(), circle.get(i), FireworkEffect.builder().with(Type.BURST).withColor(Color.BLACK).withFade(Color.GRAY).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			fplayer.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().with(Type.STAR).withColor(Color.BLACK).withFade(Color.GRAY).build());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void addEffects(Entity e) {
		if (e instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) e;
			target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0));
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 0));
			target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 0));
			target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 140, 0));
		}
	}
	
    private static List<Location> drawCircle(Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }
}
