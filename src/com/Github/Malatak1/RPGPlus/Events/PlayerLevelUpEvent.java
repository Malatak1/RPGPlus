package com.Github.Malatak1.RPGPlus.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.Github.Malatak1.RPGPlus.DataTypes.SkillType;

public class PlayerLevelUpEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    
    Player p;
    SkillType type;
    int level;
    
    public PlayerLevelUpEvent(Player p, SkillType type, int level) {
    	this.p = p;
    	this.type = type;
    	this.level = level;
    }
    
    public Player getPlayer() {
    	return p;
    }
    
    public SkillType getSkillType() {
    	return type;
    }
    
    public int getNewLevel() {
    	return level;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}
