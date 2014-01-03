package com.Github.Malatak1.RPGPlus.Commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.DataTypes.LevelIncrements;
import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Events.PlayerLevelUpEvent;

public class CommandHandler implements CommandExecutor {
	
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("rpg")){
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				Map<Player, FileConfiguration> mp = db.getFileMap();
				FileConfiguration f = mp.get(p);
				
				LevelIncrements strIncrements = new LevelIncrements(SkillType.STRENGTH);
				LevelIncrements dexIncrements = new LevelIncrements(SkillType.DEXTERITY);
				LevelIncrements wisIncrements = new LevelIncrements(SkillType.WISDOM);
				LevelIncrements conIncrements = new LevelIncrements(SkillType.CONSTITUTION);
				
				
				
				String strength = ChatColor.YELLOW + "Strength: " + ChatColor.GREEN + f.getInt("Skills.Strength") + ChatColor.AQUA + " (" + ChatColor.GRAY + f.getInt("Exp.Strength") + ChatColor.AQUA + "/" + ChatColor.GRAY + strIncrements.getIncrement(f.getInt("Skills.Strength")) + ChatColor.AQUA + ")";
				String dexterity = ChatColor.YELLOW + "Dexterity: " + ChatColor.GREEN + f.getInt("Skills.Dexterity") + ChatColor.AQUA + " (" + ChatColor.GRAY + f.getInt("Exp.Dexterity") + ChatColor.AQUA + "/" + ChatColor.GRAY + dexIncrements.getIncrement(f.getInt("Skills.Dexterity")) + ChatColor.AQUA + ")";
				String wisdom = ChatColor.YELLOW + "Wisdom: " + ChatColor.GREEN + f.getInt("Skills.Wisdom") + ChatColor.AQUA + " (" + ChatColor.GRAY + f.getInt("Exp.Wisdom") + ChatColor.AQUA + "/" + ChatColor.GRAY + wisIncrements.getIncrement(f.getInt("Skills.Wisdom")) + ChatColor.AQUA + ")";
				String constitution = ChatColor.YELLOW + "Constitution: " + ChatColor.GREEN + f.getInt("Skills.Constitution") + ChatColor.AQUA + " (" + ChatColor.GRAY + f.getInt("Exp.Constitution") + ChatColor.AQUA + "/" + ChatColor.GRAY + conIncrements.getIncrement(f.getInt("Skills.Constitution")) + ChatColor.AQUA + ")";
				
				if (args.length == 0) {
					//Print stats
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "---SKILL LEVELS:---");
					p.sendMessage(strength);
					p.sendMessage(dexterity);
					p.sendMessage(wisdom);
					p.sendMessage(constitution);
					
					return true;
					
				} else if (args.length == 1) {
					String s = args[0];
					
					switch(s.toLowerCase()) {
					
					case "strength": p.sendMessage(strength); break;
					case "dexterity": p.sendMessage(dexterity); break;
					case "wisdom": p.sendMessage(wisdom); break;
					case "constitution": p.sendMessage(constitution); break;
					default: p.sendMessage("Error: " + args[1] + " is not an acceptable argument.");
					
					}
					return true;
					
				} else if (args.length == 2) {
					
					if (sender.isOp() && Integer.parseInt(args[1]) <= 60) {
						
						int change = Integer.parseInt(args[1]);
						String s = capitalize(args[0]);
						
						f.set("Skills." + s, change);
						p.sendMessage(ChatColor.YELLOW + "Skill changed to: " + ChatColor.GREEN + change);
						
						boolean acceptableArgument = false;
						
						for (SkillType type : SkillType.values()) {
							if (s.equalsIgnoreCase(type.toString())) {
								
								p.sendMessage(ChatColor.YELLOW + "Skill changed to: " + ChatColor.GREEN + change);
								
								Event event = new PlayerLevelUpEvent(p, type, change);
								Bukkit.getServer().getPluginManager().callEvent(event);
								
								acceptableArgument = true;
							}
						}
						
						if (acceptableArgument == false) {
							p.sendMessage("Error: " + args[0] + " is not an acceptable argument.");
						}
						
						finalizeData(p, f);
						
						return true;
						
				} else {
					sender.sendMessage("Invalid Arguments! (or you may lack permissions)");
				}
				
					return true;
					
				} else {
					sender.sendMessage("Only players can use this command");
					return false;
					}
				
				}
			
			}
		return false;
		}
	
	private void finalizeData(Player p, FileConfiguration f) {
		
		Map<Player, FileConfiguration> mp = db.getFileMap();
		mp.put(p, f);
		db.setFileMap(mp);

	}
	
	private String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
	
}
