package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Github.Malatak1.RPGPlus.AbilityCastHandler;
import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Abilities.CastableAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.CooldownManager;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class PlayerInteractListener implements Listener {
	
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	CooldownManager cdm = new CooldownManager();
	AbilityCastHandler ach = new AbilityCastHandler();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player p = event.getPlayer();
		
		if (p.getItemInHand().getType().equals(Material.NETHER_STAR) && rightClicked(event)) {
			IconMenuHandler.getBaseIconMenu().open(p);
		}
		
		if (p.getItemInHand().getType().equals(Material.STICK)) {
			int power = 1;
			if (p.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)) {
				power = p.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
			}
			if (power > 20) power = 20;
			Ability ability = getCorrectAbility(p, rightClicked(event), SkillType.WISDOM);
			if (ability != null && ability instanceof CastableAbility) {
				ach.castAbility(p, (CastableAbility) ability, power);
			}
		}
		if (p.getItemInHand().getType().equals(Material.BOW)) {
			int power = 1;
			Ability ability = getCorrectAbility(p, rightClicked(event), SkillType.DEXTERITY);
			if (ability != null && ability instanceof CastableAbility) {
				ach.castAbility(p, (CastableAbility) ability, power);
			}
		}
		if (p.getItemInHand().getType().toString().toLowerCase().contains("sword") ||
			p.getItemInHand().getType().toString().toLowerCase().contains("axe") ||
			p.getItemInHand().getType().toString().toLowerCase().contains("spade") ||
			p.getItemInHand().getType().toString().toLowerCase().contains("shovel")) {
			int power = 1;
			if (p.getItemInHand().getType().toString().toLowerCase().contains("axe")) power++;
			
			Ability ability = getCorrectAbility(p, rightClicked(event), SkillType.STRENGTH);
			if (ability != null && ability instanceof CastableAbility) {
				ach.castAbility(p, (CastableAbility) ability, power);
			}
		}
	}
	
	private Ability getCorrectAbility(Player p, boolean rightClicked, SkillType type) {
		Map<AbilityType, Ability> playerMap = PlayerDataManager.getPlayerData(p).getAbilityMap();
		if (!p.isSneaking()) {
			if (!rightClicked) {
				if (hasAbilitySelected(p, AbilityType.LIGHT, type)) {
					return playerMap.get(AbilityType.LIGHT);
				}
			} else {
				if (hasAbilitySelected(p, AbilityType.MEDIUM, type)) {
					return playerMap.get(AbilityType.MEDIUM);
				}
			}
		}
		else {
			if (!rightClicked) {
				if (hasAbilitySelected(p, AbilityType.HEAVY, type)) {
					return playerMap.get(AbilityType.HEAVY);
				}
			} else {
				if (hasAbilitySelected(p, AbilityType.ULTIMATE, type)) {
					return playerMap.get(AbilityType.ULTIMATE);
				}
			}
		}
		return null;
	}
	
	private boolean rightClicked(PlayerInteractEvent event) {
		Action action = event.getAction();
		return (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK);
	}
	
	private boolean hasAbilitySelected(Player p, AbilityType type, SkillType skill) {
		Map<AbilityType, Ability> playerMap = PlayerDataManager.getPlayerData(p).getAbilityMap();
		if(playerMap.containsKey(type)) {
			Ability ability = playerMap.get(type);
			if (ability.getSkillType().equals(skill)) {
				return true;
			}
		}
		return false;
	}
}
