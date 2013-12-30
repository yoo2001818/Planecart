package kr.kkiro.projects.bukkit.planecart.plane;

import org.bukkit.Material;
import org.bukkit.entity.Minecart;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecartChest;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecartFurnace;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecartRideable;

public enum PlaneEntityType {
  PASSENGER(Material.AIR, CommonMinecartRideable.class),
  CHEST(Material.CHEST, CommonMinecartChest.class),
  ENGINE(Material.FURNACE, CommonMinecartFurnace.class),
  COCKPIT(Material.WOOD_STEP, CommonMinecartRideable.class),
  TANK(Material.WORKBENCH, CommonMinecartFurnace.class),
  DECORATION(Material.AIR, CommonMinecartFurnace.class);

  private Material defaultMaterial;
  private Class<? extends CommonMinecart<? extends Minecart>> entity;
  
  private PlaneEntityType(Material defaultMaterial, Class<? extends CommonMinecart<? extends Minecart>> entity) {
    this.defaultMaterial = defaultMaterial;
    this.entity = entity;
  }
  
  public Material getDefaultMaterial() {
    return defaultMaterial;
  }
  
  public Class<? extends CommonMinecart<? extends Minecart>> getEntityClass() {
    return entity;
  }
}
