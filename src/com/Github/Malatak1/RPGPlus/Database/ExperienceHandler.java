package com.Github.Malatak1.RPGPlus.Database;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.LevelIncrements;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class ExperienceHandler {
	
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	LevelIncrements increments;
	
	Player p;
	SkillType type;
	int value;
	
	public void handleXp(Player p, SkillType type, int value) {
		
		this.p = p;
		this.type = type;
		this.value = value;
		
		increments = new LevelIncrements(type);
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		FileConfiguration f = mp.get(p);
		
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
			f.set("Exp.Dexterity", dex);
			handleExperience(p, type);
			break;
		case WISDOM: 
			int wis = f.getInt("Exp.Wisdom");
			wis += value;
			f.set("Exp.Wisdom", wis);
			handleExperience(p, type);
			break;
		case CONSTITUTION: 
			int con = f.getInt("Exp.Constitution");
			con += value;
			f.set("Exp.Constitution", con);
			handleExperience(p, type);
			break;
		}
		
	}
	
	private void handleExperience(Player p, SkillType type) {
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		FileConfiguration f = mp.get(p);
		
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
		
		if (exp >= increment) {
			
			int overFlow = exp - increment;
			
			if (level + 1 <= 60) {
				f.set("Skills." + skillName, level + 1);
				f.set("Exp." + skillName, overFlow);
				
				/**
				 * This is a very temporary piece of code - soon we will create 
				 * a PlayerLevelUpEvent event that we ourselves can listen to,
				 * and act on. This will be thrown here in the future.
				 * 
				 * For now however, this is just to set the player's max health.
				 */
				
				if (type.equals(SkillType.CONSTITUTION)) {
					
					if (level + 1 % 3 == 0) {
						
						double increase = ((level + 1) / 3) - level % 3;
						
						p.setHealthScale(20D + increase);
						p.setHealthScaled(true);
						p.setMaxHealth(20D + increase);
					}
					
				}
				
				p.sendMessage(ChatColor.YELLOW + "Your " + skillName + " level has increased to " + ChatColor.GREEN + (level + 1));
				
				finalizeData(p,f);
				handleExperience(p,type);
			}
			
		}
			
	}
	
	private void finalizeData(Player p, FileConfiguration f) {
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		mp.put(p, f);
		db.setFileMap(mp);

	}
	
}
