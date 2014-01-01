package kr.kkiro.projects.bukkit.planecart.actions.plane;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import kr.kkiro.projects.bukkit.planecart.actions.Action;
import kr.kkiro.projects.bukkit.planecart.exceptions.InvaildBlockException;
import kr.kkiro.projects.bukkit.planecart.plane.PlaneData;
import kr.kkiro.projects.bukkit.planecart.plane.PlaneDataGroup;
import kr.kkiro.projects.bukkit.planecart.plane.PlaneEntityType;
import kr.kkiro.projects.bukkit.planecart.plane.PlaneGroup;
import kr.kkiro.projects.bukkit.planecart.plane.PlaneUpgrade;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class PlaneCraftAction extends Action {

  public static Material BASE = Material.IRON_BLOCK;
  public static final Map<Material, PlaneEntityType> materialType = new HashMap<Material, PlaneEntityType>();

  private Block baseBlock;
  public Player issuer;
  public boolean cartifiy = false;
  
  static {
    //register all material types.
    materialType.put(Material.FURNACE, PlaneEntityType.ENGINE);
    materialType.put(Material.CHEST, PlaneEntityType.CHEST);
    materialType.put(Material.TRAPPED_CHEST, PlaneEntityType.CHEST);
    materialType.put(Material.REDSTONE_BLOCK, PlaneEntityType.COCKPIT);
    materialType.put(Material.WORKBENCH, PlaneEntityType.TANK);
    materialType.put(Material.AIR, PlaneEntityType.PASSENGER);
  }

  public PlaneCraftAction(Block baseBlock) {
    this.baseBlock = baseBlock;
  }

  @Override
  public void onExecute() throws InvaildBlockException {
    //reach to the base block
    Block startBlock = baseBlock;
    while (!startBlock.getType().equals(BASE)) {
      if (startBlock.getY() <= 0 || !isVaild(startBlock)) {
        throw new InvaildBlockException();
      }
      startBlock = startBlock.getRelative(BlockFace.DOWN);
    }
    //find all base blocks
    List<Block> blocks = findBases(startBlock);
    //extract rectangle area
    Rectangle size = getRectangle(blocks);
    //Note: y and x is reversed in 2nd dimension array
    PlaneData[][] planeDatas = new PlaneData[size.height][size.width];
    Block[][] planeBlocks = new Block[size.height][size.width];
    byte cockpitCount = 0;
    PlaneDataGroup.PlaneDataEntryMarked[] cockpitList = new PlaneDataGroup.PlaneDataEntryMarked[8];
    for (Block block : blocks) {
      PlaneData data = generateData(block);
      planeDatas[block.getZ() - size.y][block.getX() - size.x] = data;
      planeBlocks[block.getZ() - size.y][block.getX() - size.x] = block;
      if(data.type == PlaneEntityType.COCKPIT) {
        cockpitList[cockpitCount] = new PlaneDataGroup.PlaneDataEntryMarked(block.getX() - size.x, block.getZ() - size.y, data);
        cockpitCount += 1;
        if(cockpitCount > 2) {
          throw new InvaildBlockException();
        }
      }
    }
    PlaneDataGroup dataGroup = new PlaneDataGroup(planeDatas);
    if(cockpitCount == 0) throw new InvaildBlockException();
    //0th index is main
    cockpitList[0].marked = true;
    //Search conditions; no more than 2 cockpits, cockpit should meet air, etc...
    BlockFace rotation = BlockFace.UP;
    //Check islands.
    for (PlaneDataGroup.PlaneDataEntryMarked entry : cockpitList) {
      if(entry == null) break;
      for (PlaneDataGroup.PlaneDataEntryMarked innerEntry : cockpitList) {
        if(innerEntry == null) break;
        if(entry == innerEntry) continue;
        if(innerEntry.marked && dataGroup.isNeighbor(entry, innerEntry))
          entry.marked = true;
      }
    }
    for (PlaneDataGroup.PlaneDataEntryMarked entry : cockpitList) {
      if(entry == null) break;
      issuer.sendMessage(entry.x+","+entry.y+":"+entry.data.type.name());
      //If there is "island", give up and throw up
      if(!entry.marked) throw new InvaildBlockException();
      int nearCode = dataGroup.findNearAir(entry);
      int nearCount = nearCode & 7;
      int nearData = nearCode >> 3;
      issuer.sendMessage(Integer.toString(nearCode)+","+Integer.toString(nearData)+","+Integer.toString(nearCount) + "..!");
      //You have two sides? then you are an error.
      if(nearCount == 2 || nearCount == 4) throw new InvaildBlockException();
      if(nearCount == 1 || nearCount == 3) {
        if(nearCount == 3) nearData = ~nearData & 0xF;
        issuer.sendMessage(Integer.toString(nearData));
        if(nearData == 1) rotation = BlockFace.NORTH;
        if(nearData == 2) rotation = BlockFace.SOUTH;
        if(nearData == 4) rotation = BlockFace.EAST;
        if(nearData == 8) rotation = BlockFace.WEST;
        if(nearCount == 1) rotation = rotation.getOppositeFace();
      }
    }
    //All blocks will be removed according to data.
    for (Block block : blocks) {
      PlaneData data = planeDatas[block.getZ() - size.y][block.getX() - size.x];
      //invoke remove event
      if(!testPlaneBlockRemove(block, data, issuer)) {
        issuer.sendMessage(_("actionPlaneCraftFailed"));
      }
    }
    for (Block block : blocks) {
      PlaneData data = planeDatas[block.getZ() - size.y][block.getX() - size.x];
      block.setType(Material.AIR);
      blockRemoveEffect(block);
      Block typeBlock = block.getRelative(BlockFace.UP);
      if(typeBlock instanceof Chest) {
        //TODO Just drop it to ground, We will fix it later.
        dropAll(((Chest) typeBlock).getInventory(), typeBlock.getLocation());
      }
      if(typeBlock instanceof Furnace) {
        //Just drop it to ground.
        dropAll(((Furnace) typeBlock).getInventory(), typeBlock.getLocation());
      }
      typeBlock.setType(Material.AIR);
      if(data.type.doAcceptUpgrades()) {
        Block upgradeBlock = typeBlock.getRelative(BlockFace.UP);
        upgradeBlock.setType(Material.AIR);
      }
    }
    //Why don't we spawn the minecarts? It's ready.
    PlaneGroup.spawnEntity(dataGroup, rotation);
  }

  private void blockRemoveEffect(Block block) {
    World world = block.getWorld();
    Location loc = block.getLocation();
    Firework fw = (Firework) world.spawnEntity(loc, EntityType.FIREWORK);
    FireworkMeta fwm = fw.getFireworkMeta();
    fwm.addEffect(FireworkEffect.builder().withColor(Color.RED).withFade(Color.YELLOW).with(FireworkEffect.Type.BALL).build());
    fwm.setPower(0);
    fw.setFireworkMeta(fwm);
    fw.detonate();
  }
  
  private boolean testPlaneBlockRemove(Block block, PlaneData data, Player player) {
    if(testBlockRemove(block, player)) return false;
    Block typeBlock = block.getRelative(BlockFace.UP);
    if(testBlockRemove(typeBlock, player)) return false;
    if(data.type.doAcceptUpgrades()) {
      Block upgradeBlock = typeBlock.getRelative(BlockFace.UP);
      if(testBlockRemove(upgradeBlock, player)) return false;
    }
    return true;
  }
  
  private boolean testBlockRemove(Block block, Player player) {
    BlockBreakEvent event = new BlockBreakEvent(block, player);
    Bukkit.getServer().getPluginManager().callEvent(event);
    return event.isCancelled();
  }
  
  private void dropAll(Inventory inventory, Location loc) {
    for(ItemStack stack : inventory) {
      if(stack.getType().equals(Material.AIR)) continue;
      loc.getWorld().dropItemNaturally(loc, stack);
      stack.setAmount(0);
      stack.setType(Material.AIR);
    }
  }
  
  private PlaneData generateData(Block block) {
    if(!block.getType().equals(BASE)) return null;
    //Try to get type of plane entity
    Block typeBlock = block.getRelative(BlockFace.UP);
    PlaneEntityType type = materialType.get(typeBlock.getType());
    if(type == null) type = PlaneEntityType.DECORATION;
    PlaneUpgrade upgrade = null;
    if(type.doAcceptUpgrades()) {
      //Block upgradeBlock = typeBlock.getRelative(BlockFace.UP);
      //TODO check upgrades
    }
    PlaneData data = new PlaneData(type);
    if(type.getDefaultMaterial() == null) {
      data.block = typeBlock.getType();
    }
    data.upgrade = upgrade;
    data.originalLocation = block.getLocation();
    return data;
  }
  
  private boolean isVaild(Block block) {
    Material mat = block.getType();
    if (mat.equals(BASE))
      return true;
    if (materialType.get(mat) != null && materialType.get(mat) != PlaneEntityType.PASSENGER)
      return true;
    // TODO: test upgrades
    return false;
  }

  private List<Block> findBases(Block base) {
    ArrayList<Block> blocks = new ArrayList<Block>();
    Stack<Block> stacks = new Stack<Block>();
    stacks.push(base);
    blocks.add(base);
    while (!stacks.isEmpty()) {
      Block currentBlock = stacks.pop();
      // Check north, south, west, east.
      Block blockNorth = currentBlock.getRelative(BlockFace.NORTH);
      if (blockNorth.getType().equals(BASE) && !blocks.contains(blockNorth)) {
        stacks.push(blockNorth);
        blocks.add(blockNorth);
      }
      Block blockSouth = currentBlock.getRelative(BlockFace.SOUTH);
      if (blockSouth.getType().equals(BASE) && !blocks.contains(blockSouth)) {
        stacks.push(blockSouth);
        blocks.add(blockSouth);
      }
      Block blockWest = currentBlock.getRelative(BlockFace.WEST);
      if (blockWest.getType().equals(BASE) && !blocks.contains(blockWest)) {
        stacks.push(blockWest);
        blocks.add(blockWest);
      }
      Block blockEast = currentBlock.getRelative(BlockFace.EAST);
      if (blockEast.getType().equals(BASE) && !blocks.contains(blockEast)) {
        stacks.push(blockEast);
        blocks.add(blockEast);
      }
    }
    return blocks;
  }

  private Rectangle getRectangle(List<Block> blocks) {
    int minX = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;
    for (Block block : blocks) {
      int x = block.getX();
      int z = block.getZ();
      minX = Math.min(x, minX);
      minZ = Math.min(z, minZ);
      maxX = Math.max(x, maxX);
      maxZ = Math.max(z, maxZ);
    }
    return new Rectangle(minX, minZ, maxX - minX + 1, maxZ - minZ + 1);
  }

}
