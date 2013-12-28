package com.Github.Malatak1.RPGPlus.Database;

import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.LevelIncrements;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class ExperienceHandler {
	
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	LevelIncrements increments;
	FileConfiguration f;
	
	Player p;
	SkillType type;
	int value;
	
	public ExperienceHandler(Player p, SkillType type, int value) {
		
		this.p = p;
		this.type = type;
		this.value = value;
		
		increments = new LevelIncrements(type);
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		f = mp.get(p);
		
		switch (type) {
			case STRENGTH: 
			int str = f.getInt("Exp.Strength");
			str += value;
			f.set("Exp.Strength", str);
			handleExperience(p, type);
			break;
		case DEXTERITY: 
			int dex = f.getInt("Exp.Dexterity");
			dex += value;
			f.set("Skills.Dexterity", dex);
			handleExperience(p, type);
			break;
		case WISDOM: 
			int wis = f.getInt("Exp.Wisdom");
			wis += value;
			f.set("Skills.Wisdom", wis);
			handleExperience(p, type);
			break;
		case CONSTITUTION: 
			int con = f.getInt("Exp.Constitution");
			con += value;
			f.set("Skills.Constitution", con);
			handleExperience(p, type);
			break;
		}
		
	}
	
	private void handleExperience(Player p, SkillType type) {
		
		String skillName = null;
		
		switch (type) {
			case STRENGTH: skillName = "Strength"; break;
			case DEXTERITY: skillName = "Dexterity"; break;
			case WISDOM: skillName = "Wisdom"; break;
			case CONSTITUTION: skillName = "Constitution"; break;
		}
		
		int exp = f.getInt("Exp." + skillName);
		int level = f.getInt("Skills." + skillName);
		
		int increment = increments.getIncrement(level);
		
		if (exp > increment) {
			
			int overFlow = exp - increment;
			
			f.set("Skills." + skillName, level++);
			f.set("Exp." + skillName, overFlow);
			
		}
			
	}
	
}
