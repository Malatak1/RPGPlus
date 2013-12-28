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
		
		/**
		 * Geometric Sequence for generating level values at each given interval:
		 * an = a1 * r^(n-1), where an is the value at an increment, a1 is the first
		 * number, r is the common difference and n is the point at which the increment
		 * falls in the sequence.
		 */
		
		for (int i = 0; i < type.getMax(); i++) {
			
			float a1 = 90;
			float r = 0.1F;
			
			increments[i] = (int) Math.round(a1 * Math.pow(r, i - 1));
		}
	}
}
