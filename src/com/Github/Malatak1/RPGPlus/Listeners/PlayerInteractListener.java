package com.Github.Malatak1.RPGPlus.Listeners;

import java.util.Map;

import org.bukkit.ChatColor;
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
import com.Github.Malatak1.RPGPlus.Abilities.CooldownAbility;
import com.Github.Malatak1.RPGPlus.Abilities.ManaAbility;
import com.Github.Malatak1.RPGPlus.DataTypes.AbilityType;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.DataTypes.IconMenus.IconMenuHandler;
import com.Github.Malatak1.RPGPlus.Database.CooldownManager;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class PlayerInteractListener implements Listener {
	
	IconMenuHandler menus = new IconMenuHandler();
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	CooldownManager cdm = new CooldownManager();
	
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
						abilityCast(p, ability, power);
					}
				} else {
					if (hasAbilitySelected(p, AbilityType.MEDIUM,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.MEDIUM);
						abilityCast(p, ability, power);
					}
				}
			} else {
				if (!rightClicked(event)) {
					if (hasAbilitySelected(p, AbilityType.HEAVY,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.HEAVY);
						abilityCast(p, ability, power);
					}
				} else {
					if (hasAbilitySelected(p, AbilityType.ULTIMATE,SkillType.WISDOM)) {
						Ability ability = playerMap.get(AbilityType.ULTIMATE);
						abilityCast(p, ability, power);
					}
				}
			}
		}
		if (p.getItemInHand().getType().equals(Material.BOW)) {
			Map<AbilityType, Ability> playerMap = db.getAbilityMap(p);
			int power = 1;
			if (!p.isSneaking()) {
				if (!rightClicked(event)) {
					if (hasAbilitySelected(p, AbilityType.LIGHT,SkillType.DEXTERITY)) {
						Ability ability = playerMap.get(AbilityType.LIGHT);
						abilityCast(p, ability, power);
					}
				}
			} else {
				if (!rightClicked(event)) {
					if (hasAbilitySelected(p, AbilityType.HEAVY,SkillType.DEXTERITY)) {
						Ability ability = playerMap.get(AbilityType.HEAVY);
						abilityCast(p, ability, power);
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
			if (ability.getSkillType().equals(skill)) {
				return true;
			}
		}
		return false;
	}
	
	private int manaEvaluator(Ability ability) {
		if (ability instanceof ManaAbility) {
			return ((ManaAbility) ability).manaCost();
		} else return 0;
	}
	
	private boolean onCooldown(Player p, Ability ability) {
		return cdm.onCooldown(p, ability.getAbilityType());
	}
	
	private void abilityCast(Player p, Ability ability, int power) {
		if (!onCooldown(p, ability)) {
			if (manaEvaluator(ability) < p.getLevel() && !onCooldown(p, ability) && ability instanceof CastableAbility) {
				((CastableAbility) ability).cast(p, power);
				p.setLevel(p.getLevel() - manaEvaluator(ability));
				if (ability instanceof CooldownAbility) {
					cdm.addAbility(p, (CooldownAbility) ability);
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "That ability is on cooldown!");
		}
	}
	
	
}
