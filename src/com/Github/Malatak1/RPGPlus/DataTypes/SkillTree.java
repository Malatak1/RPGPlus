package com.Github.Malatak1.RPGPlus.DataTypes;

import java.util.ArrayList;
import java.util.List;

import com.Github.Malatak1.RPGPlus.Abilities.Ability;

public enum SkillTree {
	
	WARRIOR(SkillType.STRENGTH), 
	PALADIN(SkillType.STRENGTH), 
	BERSERKER(SkillType.STRENGTH), 
	ROGUE(SkillType.DEXTERITY), 
	ASSASSIN(SkillType.DEXTERITY), 
	RANGER(SkillType.DEXTERITY), 
	ELEMENTALIST(SkillType.WISDOM), 
	ARCHANIST(SkillType.WISDOM), 
	SHAMAN(SkillType.WISDOM);
	
	private List<Ability> skills = new ArrayList<>();
	private SkillType type;
	
	SkillTree(SkillType type) {
		this.type = type;
	}
	
	public List<Ability> getSkills() {
		return skills;
	}
	
	public void addAbility(Ability ability) {
		skills.add(ability);
	}
	
	public void addAbility(Ability ability, int index) {
		skills.add(index, ability);
	}
	
	public List<Ability> getSkills(AbilityType type) {
		List<Ability> skillsWithType = new ArrayList<>();
		for (Ability ability : skills) {
			if (ability.getAbilityType().equals(type)) {
				skillsWithType.add(ability);
			}
		}
		return skillsWithType;
	}
	
	public SkillType getType() {
		return type;
	}
}
