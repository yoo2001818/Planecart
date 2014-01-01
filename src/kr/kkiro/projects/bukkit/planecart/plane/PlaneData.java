package kr.kkiro.projects.bukkit.planecart.plane;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PlaneData {
  public EntityType cartClass;
  public Material block;
  public PlaneEntityType type;
  public PlaneUpgrade upgrade;
  public Location originalLocation;

  public PlaneData(PlaneEntityType type) {
    this.type = type;
    this.block = type.getDefaultMaterial();
    this.cartClass = type.getEntityClass();
  }

}
