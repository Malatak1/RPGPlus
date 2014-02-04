package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.ManaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;
import com.Github.Malatak1.RPGPlus.Util.BlockRemover;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class FreezingWindsAbility implements CastableAbility, ManaAbility, CooldownAbility {
	
	private ArrayList<Block> blocks;
	
	@Override
	public String getName() {
		return "Freezing Winds";
	}

	@Override
	public String getInfo() {
		return "Conjures an icey storm that freezes enemies solid";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.QUARTZ);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.ULTIMATE;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.WISDOM;
	}

	@Override
	public int manaCost() {
		return 30;
	}

	@Override
	public int cooldownTime() {
		return 400;
	}

	@Override
	public void cast(Player p, int power) {
		List<Entity> nearby = p.getNearbyEntities(10 + 2 * power, 8, 10 + 2 * power);
		blocks = new ArrayList<Block>();
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		
		for (Entity e : nearby) {
			if (e instanceof Player) {
				if (PartyManager.inSameParty(p, (Player) e)) {
					nearby.remove(e);
				}
			}
		}
		
		for (int i = 0; i < nearby.size(); i++) {
			if (nearby.get(i) instanceof Damageable && !nearby.get(i).hasMetadata("NPC")) {
				Damageable entity = (Damageable) nearby.get(i);
				freezeEntity(entity);
				if (power > 2) {
					Double power2 = (double) power;
					entity.damage(power2 / 2D - (power2 % 2));
				}
				Type type = Type.BURST;
				if (power > 2) type = Type.STAR;
				Location newLoc = entity.getLocation();
				newLoc.setY(entity.getLocation().getY() + 3);
				try {
					fplayer.playFirework(entity.getWorld(), newLoc, FireworkEffect.builder().with(type).withColor(Color.AQUA).build());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		List<Location> circle = drawCircle(p, p.getLocation(), 10, 1, true, false, 15);
		for (int i = 0; i < circle.size(); i++) {
			try {
				fplayer.playFirework(p.getWorld(), circle.get(i), FireworkEffect.builder().with(Type.BURST).withColor(Color.AQUA).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings({ "deprecation", "unused" })
		BukkitTask blockRemover = new BlockRemover(blocks , Material.SNOW_BLOCK.getId()).runTaskLater(RPGPlus.inst(), (80L + (10 * power)));
		
	}

	@SuppressWarnings("deprecation")
	private void freezeEntity(Damageable entity) {

		Block feet = entity.getLocation().getBlock();
		Block temp = feet.getRelative(1, 0, 0);
		
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(1, 1, 0);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(-1, 0, 0);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(-1, 1, 0);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(0, 0, 1);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(0, 1, 1);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(0, 0, -1);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(0, 1, -1);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(0, -1, 0);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
		}
		temp = feet.getRelative(0, 2, 0);
		if (temp.getType() == Material.AIR) {
			temp.setTypeId(80);
			blocks.add(temp);
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
