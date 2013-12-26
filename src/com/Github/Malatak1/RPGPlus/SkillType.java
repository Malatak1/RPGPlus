package com.Github.Malatak1.RPGPlus;

public enum SkillType {
	
	STRENGTH(10), DEXTERITY(10), WISDOM(10), CONSTITUTION(10);
	
	private final maxLevel;
	
	SkillType(int maxLevel){
		this.maxLevel = maxLevel;
		level = 1;
	}
	
	int getMax(){
		return maxLevel;
	}
	
}
