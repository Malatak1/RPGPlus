package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.ManaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class WaterBlastAbility implements CastableAbility, ManaAbility, CooldownAbility {

	@Override
	public String getName() {
		return "Water Blast";
	}

	@Override
	public String getInfo() {
		return "Blast targets with a torrent of water";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.WATER_BUCKET);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.HEAVY;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.WISDOM;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public int cooldownTime() {
		return 0;
	}
	
	@Override
	public void cast(Player p, int power) {
		
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		Location start = p.getLocation();
		@SuppressWarnings("deprecation")
		Location newLocation = p.getTargetBlock(null, 50).getLocation();
		
		BlockIterator blocksToAdd = new BlockIterator(start.getWorld(), start.toVector(), new Vector(newLocation.getBlockX()-start.getBlockX(), newLocation.getBlockY()-start.getBlockY(), newLocation.getBlockZ()-start.getBlockZ()), 0, (int) Math.floor(start.distanceSquared(newLocation)));
		Location location;
		while(blocksToAdd.hasNext()) {
		    location = blocksToAdd.next().getLocation();
		    try {
				fplayer.playFirework(p.getWorld(), location, FireworkEffect.builder().with(Type.BALL).withColor(Color.BLUE).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
//		
//		Location location;
//		LocationIterator locationsToAdd = new LocationIterator(p);
//		
//		int i = 0;
//		while(locationsToAdd.hasNext()) {
//			if (i < 100) {
//				location = locationsToAdd.next();
//				try {
//					fplayer.playFirework(p.getWorld(), location, FireworkEffect.builder().with(Type.BALL).withColor(Color.RED).build());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				i++;
//			}
//		}
		
		
		
		
	}

}
