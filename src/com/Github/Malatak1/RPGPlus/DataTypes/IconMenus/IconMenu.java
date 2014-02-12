package com.Github.Malatak1.RPGPlus.DataTypes.IconMenus;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.Github.Malatak1.RPGPlus.Abilities.Ability;
import com.Github.Malatak1.RPGPlus.Database.DataBaseManager;
import com.Github.Malatak1.RPGPlus.Database.PlayerDataManager;
 
public class IconMenu implements Listener {
	
    private String name;
    private int size;
    private OptionClickEventHandler handler;
    private Plugin plugin;
   
    private String[] optionNames;
    private ItemStack[] optionIcons;
    
	ChatColor pri = IconMenuHandler.pri;
	ChatColor sec = IconMenuHandler.sec;
    ChatColor ter = IconMenuHandler.ter;
	
    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
   
    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
   
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
   
    public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }
   
    @EventHandler(priority=EventPriority.NORMAL)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name) && !event.isCancelled()) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                Plugin plugin = this.plugin;
                OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);
                if (e.willClose()) {
                    final Player p = (Player)event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if (e.willDestroy()) {
                    destroy();
                }
            }
        }
    }
//    @EventHandler(priority=EventPriority.MONITOR)
//    void onInventoryClick(InventoryClickEvent event) {
//    	if (event.getInventory().getTitle().equals(name)) {
//    		event.setCancelled(true);
//    		int slot = event.getRawSlot();
//    		if (slot >= 0 && slot < size && optionNames[slot] != null) {
//    			Plugin plugin = this.plugin;
//    			OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot]);
//    			handler.onOptionClick(e);
//    			if (e.willClose()) {
//    				final Player p = (Player)event.getWhoClicked();
//    				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//    					public void run() {
//    						p.closeInventory();
//    					}
//    				}, 1);
//    			}
//    			if (e.willDestroy()) {
//    				destroy();
//    			}
//    		}
//    	}
//    }
    
    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);       
    }
    
    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
       
        public OptionClickEvent(Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }
        
        public Player getPlayer() {
            return player;
        }
       
        public int getPosition() {
            return position;
        }
       
        public String getName() {
            return name;
        }
        
        public IconMenu getMenu() {
        	return inst();
        }
        
        public boolean willClose() {
            return close;
        }
       
        public boolean willDestroy() {
            return destroy;
        }
        
        public void setWillClose(boolean close) {
            this.close = close;
        }
       
        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
   
    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
            im.setDisplayName(name);
            im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }
    
    public IconMenu inst() {
    	return this;
    }
    
    public String getName() {
    	return name;
    }
    
	public void addAbility(int position, Ability ability) {
		
		ChatColor ita = ChatColor.ITALIC;
		String skillType = ability.getSkillType().toString().toLowerCase();
		String desc = Character.toUpperCase(skillType.charAt(0)) + skillType.substring(1);
		String[] info = {sec + ability.getInfo(), ter + "" + ita + desc};
		setOption(position, ability.getIcon(), pri + ability.getName(), info);
		
	}
	public void addAbility(int position, Ability ability, Player p) {
		
		ChatColor ita = ChatColor.ITALIC;
		String skillType = ability.getSkillType().toString().toLowerCase();
		String desc = Character.toUpperCase(skillType.charAt(0)) + skillType.substring(1);
		String[] info = {sec + ability.getInfo(), ter + "" + ita + desc};
		FileConfiguration f = PlayerDataManager.getPlayerData(p).getFile();
		
		ItemStack icon = ability.getIcon();
		if (f.contains("Abilities." + capitalize(ability.getSkillType().toString()) + "." + DataBaseManager.abilityToName(ability))) {
			icon.setAmount(f.getInt("Abilities." + capitalize(ability.getSkillType().toString()) + "." + DataBaseManager.abilityToName(ability)));
		} else {
			icon = new ItemStack(Material.ENDER_PEARL);
		}
		
		setOption(position, icon, pri + ability.getName(), info);
		
	}
	
	private static String capitalize(String s) {
		char[] charArray = s.toLowerCase().toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
}
