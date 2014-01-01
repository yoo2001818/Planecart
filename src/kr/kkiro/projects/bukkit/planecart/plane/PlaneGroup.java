package kr.kkiro.projects.bukkit.planecart.plane;

import java.awt.Dimension;

import kr.kkiro.projects.bukkit.planecart.controller.PlaneEntityController;
import kr.kkiro.projects.bukkit.planecart.plane.PlaneDataGroup.PlaneDataEntry;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.common.utils.FaceUtil;

public class PlaneGroup {

  public PlaneEntity[][] data;
  private BlockFace rotation;

  protected PlaneGroup(int width, int height, BlockFace rotation) {
    data = new PlaneEntity[height][width];
  }
  
  public int getWidth() {
    return data[0].length;
  }
  
  public int getHeight() {
    return data.length;
  }
  
  public PlaneEntity getUnrotated(int x, int y) {
    if(x < 0 || y < 0 || x >= data[0].length || y >= data.length) {
      return null;
    }
    return data[y][x];
  }
  
  public PlaneEntity front(PlaneEntity e) {
    return get(e.x, e.y - 1);
  }
  
  public PlaneEntity back(PlaneEntity e) {
    return get(e.x, e.y + 1);
  }
  
  public PlaneEntity left(PlaneEntity e) {
    return get(e.x - 1, e.y);
  }
  
  public PlaneEntity right(PlaneEntity e) {
    return get(e.x + 1, e.y);
  }
  
  public PlaneEntity get(int x, int y) {
    Dimension rotated = rotate(x, y, FaceUtil.faceToYaw(rotation));
    return getUnrotated(rotated.width, rotated.height);
  }
  
  public void put(int x, int y, PlaneEntity entity) {
    Dimension rotated = rotate(x, y, FaceUtil.faceToYaw(rotation));
    entity.x = x;
    entity.y = y;
    data[rotated.height][rotated.width] = entity;
  }
  
  private Dimension rotate(int x, int y, int rotation) {
    int realRotation = rotation;
    if(realRotation < 0) {
      realRotation = - realRotation + 180;
    }
    realRotation = realRotation % 360;
    switch(realRotation) {
      case 0:
        return new Dimension(x, y);
      case 90:
        return new Dimension(getHeight()-y, x);
      case 180:
        return new Dimension(getWidth()-x, getHeight()-y);
      case 270:
        return new Dimension(y, getWidth()-x);
    }
    return new Dimension();
  }
  
  public static PlaneGroup spawnEntity(PlaneDataGroup group, BlockFace rotation) {
    PlaneGroup entityGroup = new PlaneGroup(group.getWidth(), group.getHeight(), rotation);
    for(PlaneDataEntry entry : group) {
      if(entry.data == null) continue;
      PlaneEntity planeEntity = new PlaneEntity(entityGroup, entry.data);
      CommonMinecart<?> entity = (CommonMinecart<?>) CommonMinecart.create(entry.data.cartClass);
      Location loc = entry.data.originalLocation;
      entity.spawn(loc);
      entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), FaceUtil.faceToYaw(rotation), 0);
      entity.setController(new PlaneEntityController(planeEntity));
      entity.setBlock(entry.data.block);
      planeEntity.entity = entity;
      planeEntity.x = entry.x;
      planeEntity.y = entry.y;
      entityGroup.put(entry.x, entry.y, planeEntity);
    }
    return entityGroup;
  }
}
