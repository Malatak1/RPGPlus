package com.Github.Malatak1.RPGPlus.Util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockRemover extends BukkitRunnable {
	
	ArrayList<Block> blocks;
	int id;
	
	public BlockRemover(ArrayList<Block> blocks, int id) {
		this.blocks = blocks;
		this.id = id;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
        for (Block block : blocks) {
            if (block.getTypeId() == id) {
                    block.setType(Material.AIR);
            }
            
        }
    blocks.removeAll(blocks);

	}

}
