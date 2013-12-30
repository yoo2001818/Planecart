package kr.kkiro.projects.bukkit.planecart.plane;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;

public class PlaneData {
  public Class<? extends CommonMinecart<? extends Minecart>> cartClass;
  public Material block;
  public PlaneEntityType type;
  public PlaneUpgrade upgrade;

  public PlaneData(PlaneEntityType type) {
    this.type = type;
    this.block = type.getDefaultMaterial();
    this.cartClass = type.getEntityClass();
  }

}
