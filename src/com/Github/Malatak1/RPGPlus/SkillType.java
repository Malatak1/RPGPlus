package com.Github.Malatak1.RPGPlus;

public enum SkillType {
	
	STRENGTH(60), DEXTERITY(60), WISDOM(60), CONSTITUTION(60);
	
	private final int maxLevel;
	
	SkillType(int maxLevel){
		this.maxLevel = maxLevel;
	}
	
	int getMax(){
		return maxLevel;
	}
	
}
