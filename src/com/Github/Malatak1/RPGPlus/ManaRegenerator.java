package com.Github.Malatak1.RPGPlus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;

public class ManaRegenerator extends BukkitRunnable {
	
	DataBaseManager db;
	
	public ManaRegenerator() {
		db = new DataBaseManager(RPGPlus.inst());
	}
	
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			FileConfiguration f = PlayerDataManager.getPlayerData(p).getFile();
			int level = f.getInt("Skills.Wisdom");
			
			int maxMana = 50 + ((level - 1) * 5);
			int currentMana = p.getLevel();
			int recharge = rechargeCalc(p) + 3;
			
			float currentStamina = p.getExp();
			
			if (currentMana + recharge < maxMana) {
				p.setLevel(currentMana + recharge);
			} else p.setLevel(maxMana);
			
			if (currentStamina + 0.025F <= 0.9999) {
				p.setExp(currentStamina + 0.025F);
			} else p.setExp(0.9999F);
		}
	}
	
	private int rechargeCalc(Player p) {
		int value = 0;
		for (ItemStack item : p.getInventory().getArmorContents()) {
			if (item.hasItemMeta() ? item.getItemMeta().hasLore() : false) {
				for (String s : item.getItemMeta().getLore()) {
					s = ChatColor.stripColor(s);
					if (s.toLowerCase().startsWith("focus ")) {
						StringBuilder sb = new StringBuilder(s);
						sb.delete(0, 5);
						String romanNumber = sb.toString();
						value += romanToDecimal(romanNumber);
					}
				}
			}
		}
		return value;
	}
	
	public static int romanToDecimal(String romanNumber) {
	    int decimal = 0;
	    int lastNumber = 0;
	    String romanNumeral = romanNumber.toUpperCase();
	         /* operation to be performed on upper cases even if user enters roman values in lower case chars */
	    for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
	        char convertToDecimal = romanNumeral.charAt(x);

	        switch (convertToDecimal) {
	            case 'M':
	                decimal = processDecimal(1000, lastNumber, decimal);
	                lastNumber = 1000;
	                break;

	            case 'D':
	                decimal = processDecimal(500, lastNumber, decimal);
	                lastNumber = 500;
	                break;

	            case 'C':
	                decimal = processDecimal(100, lastNumber, decimal);
	                lastNumber = 100;
	                break;

	            case 'L':
	                decimal = processDecimal(50, lastNumber, decimal);
	                lastNumber = 50;
	                break;

	            case 'X':
	                decimal = processDecimal(10, lastNumber, decimal);
	                lastNumber = 10;
	                break;

	            case 'V':
	                decimal = processDecimal(5, lastNumber, decimal);
	                lastNumber = 5;
	                break;

	            case 'I':
	                decimal = processDecimal(1, lastNumber, decimal);
	                lastNumber = 1;
	                break;
	        }
	    }
	    return decimal;
	}
	
	public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
	    if (lastNumber > decimal) {
	        return lastDecimal - decimal;
	    } else {
	        return lastDecimal + decimal;
	    }
	}

}
