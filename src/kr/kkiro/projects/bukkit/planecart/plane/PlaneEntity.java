package kr.kkiro.projects.bukkit.planecart.plane;

import org.bukkit.entity.Minecart;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;

public class PlaneEntity {

  public CommonMinecart<? extends Minecart> entity;
  public PlaneData data;
  public PlaneGroup group;
  public int x;
  public int y;

  public PlaneEntity(PlaneGroup group, PlaneData data) {
    this.group = group;
    this.data = data;
  }
  
  public PlaneEntity front() {
    return group.front(this);
  }
  
  public PlaneEntity back() {
    return group.back(this);
  }
  
  public PlaneEntity left() {
    return group.left(this);
  }
  
  public PlaneEntity right() {
    return group.right(this);
  }

}
