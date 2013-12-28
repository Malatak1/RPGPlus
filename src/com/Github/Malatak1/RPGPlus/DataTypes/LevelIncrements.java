package com.Github.Malatak1.RPGPlus.DataTypes;

public class LevelIncrements {
	
	private int[] increments;
	private SkillType type;
	
	public LevelIncrements(SkillType type) {
		
		this.type = type;
		
		if (increments == null) {
			generateValues();
		}
		
	}
	
	public int getIncrement(int i) {
		
		return increments[i];
		
	}
	
	private void generateValues() {
		
		for (int i = 0; i < type.getMax(); i++) {
			
			increments[i] = 200 * (i - 1);
			
			// This might be changed. Currently it would yield:
			// 0, 200, 400, 800, 1600, 3200, 6400 etc.
			
		}
		
	}
	
}
