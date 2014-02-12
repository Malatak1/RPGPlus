package com.Github.Malatak1.RPGPlus.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.Github.Malatak1.RPGPlus.Abilities.Dexterity.*;
import com.Github.Malatak1.RPGPlus.Abilities.Strength.*;
import com.Github.Malatak1.RPGPlus.Abilities.Wisdom.*;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillTree;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public final class SkillTreeHandler {
	
	public static List<Ability> abilityList;
	
	public static void initSkillTrees() {
		
		abilityList = new ArrayList<>();
		
		abilityList.add(new WeakenAbility());
		abilityList.add(new ManaDrainAbility());
		abilityList.add(new BloodRageAbility());
		abilityList.add(new WolfAspectAbility());
		abilityList.add(new SwiftStepAbility());
		abilityList.add(new QuickShotAbility());
		abilityList.add(new PenetratingShotAbility());
		abilityList.add(new SmokeBombAbility());
		abilityList.add(new VolleyAbility());
		abilityList.add(new FireboltAbility());
		abilityList.add(new FireballAbility());
		abilityList.add(new ThunderboltAbility());
		abilityList.add(new DeflectAbility());
		abilityList.add(new FreezingWindsAbility());
		
		for (Ability ability : abilityList) {
			switch (ability.getSkillType()) {
			case STRENGTH: SkillTree.WARRIOR.addAbility(ability); break;
			case DEXTERITY: SkillTree.ROGUE.addAbility(ability); break;
			case WISDOM: SkillTree.ELEMENTALIST.addAbility(ability); break;
				default: break;
			}
		}
	}
	
	public static boolean isAbilityName(String s) {
		for (Ability ability : abilityList) {
			if (DataBaseManager.abilityToName(ability).equals(ChatColor.stripColor(DataBaseManager.abilityToName(s)))) {
				return true;
			}
		} return false;
	}
	
	public static Ability getAbility(String s) {
		for (Ability ability : abilityList) {
			if (DataBaseManager.abilityToName(ability).equals(ChatColor.stripColor(DataBaseManager.abilityToName(s)))) {
				return ability;
			}
		} return null;
	}
}
