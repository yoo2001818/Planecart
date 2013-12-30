package kr.kkiro.projects.bukkit.planecart.actions.plane;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import kr.kkiro.projects.bukkit.planecart.actions.Action;
import kr.kkiro.projects.bukkit.planecart.exceptions.InvaildBlockException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class PlaneCraftAction extends Action {

  public static Material BASE = Material.IRON_BLOCK;
  public static Material ENGINE = Material.FURNACE;
  public static Material[] CHESTS = {Material.CHEST, Material.TRAPPED_CHEST};
  public static Material COCKPIT = Material.REDSTONE_BLOCK;
  public static Material TANK = Material.WORKBENCH;
  
	private Block baseBlock;
	public Player issuer;
	public boolean cartifiy = false;
	
	public PlaneCraftAction(Block baseBlock) {
		this.baseBlock = baseBlock;
	}

	@Override
	public void onExecute() throws InvaildBlockException {
		//is this vaild block?
	  Block startBlock = baseBlock;
	  while(!startBlock.getType().equals(BASE)) {
	    if(startBlock.getY() <= 0 || !isVaild(startBlock)) {
	      throw new InvaildBlockException();
	    }
	    startBlock = startBlock.getRelative(BlockFace.DOWN);
	  }
	  List<Block> blocks = findBases(startBlock);
	  Rectangle size = getRectangle(blocks);
	  Block[][] planeData = new Block[size.width][size.height];
	  for(Block block : blocks) {
	    planeData[block.getX()-size.x][block.getZ()-size.y] = block;
	  }
	  //plane database is ready!
	  for(int y=0; y<size.height; ++y) {
	    String row = "";
	    for(int x=0; x<size.width; ++x) {
	      if(planeData[x][y] == null) {
	        row = row + ChatColor.GRAY+"@";
	        continue;
	      }
	      Block block = planeData[x][y];
	      ChatColor color = ChatColor.WHITE;
	      if(block.getRelative(BlockFace.UP).getType().equals(ENGINE)) {
	        color = ChatColor.DARK_GRAY;
	      }
        if(block.getRelative(BlockFace.UP).getType().equals(COCKPIT)) {
          color = ChatColor.RED;
        }
        if(block.getRelative(BlockFace.UP).getType().equals(TANK)) {
          color = ChatColor.BLUE;
        }
        for(Material chest : CHESTS) if(block.getRelative(BlockFace.UP).getType().equals(chest)) {
          color = ChatColor.YELLOW;
          break;
        }
        row = row + color+"@";
        if(cartifiy) {
          BlockBreakEvent event = new BlockBreakEvent(block, issuer);
          //call block break event to remove protections from LWC, etc..
          Bukkit.getServer().getPluginManager().callEvent(event);
          if(event.isCancelled()) {
            issuer.sendMessage("I think you don't have permission to do that.");
            return;
          }
          block.setType(Material.AIR);
          event = new BlockBreakEvent(block.getRelative(BlockFace.UP), issuer);
          //call block break event to remove protections from LWC, etc..
          Bukkit.getServer().getPluginManager().callEvent(event);
          if(event.isCancelled()) {
            issuer.sendMessage("I think you don't have permission to do that.");
            return;
          }
          block.getRelative(BlockFace.UP).setType(Material.AIR);
          Minecart mc = block.getWorld().spawn(new Location(block.getWorld(), size.x + x * 1.2, block.getY() + 1, size.y + y * 1.2), Minecart.class);
        }
	    }
	    issuer.sendMessage(row);
	  }
	  issuer.sendMessage("Your configuration has "+blocks.size()+" blocks.");
	  //meh. I'm done.
	}
	
	private boolean isVaild(Block block) {
	  Material mat = block.getType();
	  if(mat.equals(BASE)) return true;
	  if(mat.equals(ENGINE)) return true;
	  if(mat.equals(COCKPIT)) return true;
	  if(mat.equals(TANK)) return true;
	  for(Material chest : CHESTS) if(mat.equals(chest)) return true;
	  //TODO: test upgrades
	  return false;
	}
	
	private List<Block> findBases(Block base) {
	  ArrayList<Block> blocks = new ArrayList<Block>();
	  Stack<Block> stacks = new Stack<Block>();
	  stacks.push(base);
	  blocks.add(base);
	  while(!stacks.isEmpty()) {
	    Block currentBlock = stacks.pop();
	    //Check north, south, west, east.
	    Block blockNorth = currentBlock.getRelative(BlockFace.NORTH);
	    if(blockNorth.getType().equals(BASE) && !blocks.contains(blockNorth)) {
	      stacks.push(blockNorth);
	      blocks.add(blockNorth);
	    }
	    Block blockSouth = currentBlock.getRelative(BlockFace.SOUTH);
	    if(blockSouth.getType().equals(BASE) && !blocks.contains(blockSouth)) {
	      stacks.push(blockSouth);
	      blocks.add(blockSouth);
	    }
	    Block blockWest = currentBlock.getRelative(BlockFace.WEST);
	    if(blockWest.getType().equals(BASE) && !blocks.contains(blockWest)) {
	      stacks.push(blockWest);
	      blocks.add(blockWest);
	    }
	    Block blockEast = currentBlock.getRelative(BlockFace.EAST);
	    if(blockEast.getType().equals(BASE) && !blocks.contains(blockEast)) {
	      stacks.push(blockEast);
	      blocks.add(blockEast);
	    }
	  }
	  return blocks;
	}
	
	private Rectangle getRectangle(List<Block> blocks) {
	  int minX = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
	  int maxX = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;
	  for(Block block : blocks) {
	    int x = block.getX();
	    int z = block.getZ();
	    minX = Math.min(x, minX);
	    minZ = Math.min(z, minZ);
	    maxX = Math.max(x, maxX);
	    maxZ = Math.max(z, maxZ);
	  }
	  return new Rectangle(minX, minZ, maxX-minX+1, maxZ-minZ+1);
	}
	
}
