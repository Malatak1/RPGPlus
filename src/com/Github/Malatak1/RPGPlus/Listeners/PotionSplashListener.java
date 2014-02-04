package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

import com.Github.Malatak1.RPGPlus.Party.PartyManager;
import com.Github.Malatak1.RPGPlus.Util.FireworkEffectPlayer;

public class PotionSplashListener implements Listener {
	
	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		event.setCancelled(true);
		ThrownPotion potion = event.getEntity();
		if (potion.hasMetadata("Element")) {
			FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
			potion.getWorld().playEffect(potion.getLocation(), Effect.STEP_SOUND, Material.WATER);
			try {
				fplayer.playFirework(potion.getWorld(), potion.getLocation(), FireworkEffect.builder().with(Type.BALL).withColor(Color.BLUE).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (LivingEntity entity : event.getAffectedEntities()) {
				if (!entity.equals(potion.getShooter()) && !entity.hasMetadata("NPC")) {
					if (potion.getShooter() instanceof Player && entity instanceof Player) {
						if (!PartyManager.inSameParty((Player) potion.getShooter(), (Player) entity)) {
							entity.damage(4D);
						}
					} else {
						entity.damage(4D);
					}
				}
				if (entity.getFireTicks() > 0) {
					entity.setFireTicks(0);
				}
				entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, Material.WATER);
			}
		}
	}
	
}
