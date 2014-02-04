package com.Github.Malatak1.RPGPlus.Abilities.Wisdom;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Party.PartyManager;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class ThunderboltAbility implements CastableAbility, CooldownAbility {
	
	HashSet<Byte> transparent;
	
	@Override
	public String getName() {
		return "Thunderbolt";
	}

	@Override
	public String getInfo() {
		return "Call down a bolt of lightning";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BOOK);
	}

	@Override
	public AbilityType getAbilityType() {
		return AbilityType.MEDIUM;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.WISDOM;
	}

	@Override
	public int cooldownTime() {
		return 80;
	}

	@Override
	public void cast(Player p, int power) {
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		if (transparent == null) {
			init();
		}
		@SuppressWarnings("deprecation")
		Location target = p.getTargetBlock(transparent, 80).getLocation();
		p.getWorld().strikeLightningEffect(target);
		Entity e = p.getWorld().spawnEntity(target, EntityType.EXPERIENCE_ORB);
		List<Entity> n = e.getNearbyEntities(2 + power, 2 + power, 2 + power);
		Entity[] nearby = n.toArray(new Entity[n.size()]);
		Entity[] temp = nearby;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] instanceof Player) {
				if (temp[i].equals(p)) {
					nearby[i] = null;
				}
				if (PartyManager.inSameParty(p, (Player) temp[i])) {
					nearby[i] = null;
				}
			}
		}
		for (Entity i : nearby) {
			if (i != null && i instanceof Damageable && !i.hasMetadata("NPC")) {
				Location distance = target.subtract(i.getLocation());
				double x = distance.getX();
				double z = distance.getZ();
				double amt = Math.sqrt((x*x) + (z*z));
				double relative = (2 + power - amt) / 2;
				((Damageable) i).damage((3 + power) * relative);
				try {
					fplayer.playFirework(i.getWorld(), i.getLocation(), FireworkEffect.builder().with(Type.STAR).withColor(Color.PURPLE).withFade(Color.AQUA).build());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		e.remove();
	}
	
	@SuppressWarnings("deprecation")
	private void init() {
		 transparent = new HashSet<>();
		 for (Material m : Material.values()) {
			 if (m.isTransparent()) {
				 transparent.add((byte) m.getId());
			 }
		 }
	}
}
