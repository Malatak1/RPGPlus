package com.Github.Malatak1.RPGPlus.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;

public class PlayerInteractListener implements Listener {
	
	IconMenuHandler menus = new IconMenuHandler();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player p = event.getPlayer();
		
		if (p.getItemInHand().getType().equals(Material.NETHER_STAR) && rightClicked(event)) {
			
			menus.getBaseIconMenu().open(p);
			
		}
		
	}
	
	
	private boolean rightClicked(PlayerInteractEvent event) {
		
		Action action = event.getAction();
		
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			return true;
		} else return false;
		
	}
}
