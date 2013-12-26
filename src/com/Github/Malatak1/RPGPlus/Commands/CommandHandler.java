package com.Github.Malatak1.RPGPlus.Commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Github.Malatak1.RPGPlus.RPGPlus;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;

public class CommandHandler implements CommandExecutor {
	
	DataBaseManager db = new DataBaseManager(RPGPlus.inst());
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("rpg")){
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				Map<Player, FileConfiguration> mp = db.getFileMap();
				FileConfiguration f = mp.get(p);
				
				String strength = "Strength: " + f.getInt("Skills.Strength");
				String dexterity = "Dexterity: " + f.getInt("Skills.Dexterity");
				String wisdom = "Wisdom: " + f.getInt("Skills.Wisdom");
				String constitution = "Constitution: " + f.getInt("Skills.Constitution");
				
				if (args.length == 0) {
					//Print stats
					p.sendMessage("Stats:");
					p.sendMessage(strength);
					p.sendMessage(dexterity);
					p.sendMessage(wisdom);
					p.sendMessage(constitution);
					
					return true;
				} else if (args.length == 1) {
					String s = args[0];
					switch(s) {
					
					case "Strength": p.sendMessage(strength); break;
					case "Dexterity": p.sendMessage(dexterity); break;
					case "Wisdom": p.sendMessage(wisdom); break;
					case "Constitution": p.sendMessage(constitution); break;
					default: p.sendMessage("Error: " + args[1] + " is not an acceptable argument.");
					
					}
				} else if (args.length == 2) {
					int change = Integer.parseInt(args[1]);
					String s = args[0];
					switch (s) {
					
					case "Strength": f.set("Skills.Strength", change); p.sendMessage("Skill changed to: " + change); break;
					case "Dexterity": f.set("Skills.Dexterity", change); p.sendMessage("Skill changed to: " + change); break;
					case "Wisdom": f.set("Skills.Wisdom", change); p.sendMessage("Skill changed to: " + change); break;
					case "Constitution": f.set("Skills.Constitution", change); p.sendMessage("Skill changed to: " + change); break;
					default: p.sendMessage("Error: " + args[1] + " is not an acceptable argument.");
					
					finalizeData(p, f);
				}
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
	
	
	
}
