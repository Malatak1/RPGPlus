package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class PlayerInteractListener implements Listener {
	
	IconMenuHandler menus = new IconMenuHandler();
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player p = event.getPlayer();
		
		if (p.getItemInHand().getType().equals(Material.NETHER_STAR) && rightClicked(event)) {
			
			menus.getBaseIconMenu().open(p);
			
		}
		
		if (p.getItemInHand().getType().equals(Material.STICK)) {
			
			Map<AbilityType, Ability> playerMap = db.getAbilityMap(p);
			int power = 1;
			if (p.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)) {
				power = p.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
			}
			if (power > 20) power = 20;
			
			if (!p.isSneaking()) {
				if (!rightClicked(event)) {
					if (hasAbilitySelected(p, AbilityType.LIGHT,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.LIGHT);
						if (ability.manaCost() < p.getLevel() && ability instanceof CastableAbility) {
							((CastableAbility) ability).cast(p, power);
							p.setLevel(p.getLevel() - ability.manaCost());
						}
					}
				} else {
					if (hasAbilitySelected(p, AbilityType.MEDIUM,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.MEDIUM);
						if (ability.manaCost() < p.getLevel() && ability instanceof CastableAbility) {
							((CastableAbility) ability).cast(p, power);
							p.setLevel(p.getLevel() - ability.manaCost());
						}
					}
				}
			} else {
				if (!rightClicked(event)) {
					if (hasAbilitySelected(p, AbilityType.HEAVY,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.HEAVY);
						if (ability.manaCost() < p.getLevel() && ability instanceof CastableAbility) {
							((CastableAbility) ability).cast(p, power);
							p.setLevel(p.getLevel() - ability.manaCost());
						}
					}
				} else {
					if (hasAbilitySelected(p, AbilityType.ULTIMATE,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.ULTIMATE);
						if (ability.manaCost() < p.getLevel() && ability instanceof CastableAbility) {
							((CastableAbility) ability).cast(p, power);
							p.setLevel(p.getLevel() - ability.manaCost());
						}
					}
				}
			}
		}
	}
	
	
	private boolean rightClicked(PlayerInteractEvent event) {
		
		Action action = event.getAction();
		return (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK);
		
	}
	
	private boolean hasAbilitySelected(Player p, AbilityType type, SkillType skill) {
		
		Map<AbilityType, Ability> playerMap = db.getAbilityMap(p);
		if(playerMap.containsKey(type)) {
			Ability ability = playerMap.get(type);
			if (ability.getSkillType().equals(SkillType.WISDOM)) {
				return true;
			}
		}
		return false;
	}
}
