package kr.kkiro.projects.bukkit.planecart.plane;

import org.bukkit.Material;
import org.bukkit.entity.Minecart;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecartChest;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecartFurnace;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecartRideable;

public enum PlaneEntityType {
  PASSENGER(Material.AIR, CommonMinecartRideable.class, false),
  CHEST(Material.CHEST, CommonMinecartChest.class, false),
  ENGINE(Material.FURNACE, CommonMinecartFurnace.class, true),
  COCKPIT(Material.WOOD_STEP, CommonMinecartRideable.class, false),
  TANK(Material.WORKBENCH, CommonMinecartFurnace.class, true),
  DECORATION(Material.AIR, CommonMinecartFurnace.class, false);

  private Material defaultMaterial;
  private Class<? extends CommonMinecart<? extends Minecart>> entity;
  private boolean acceptUpgrades;
  
  private PlaneEntityType(Material defaultMaterial, Class<? extends CommonMinecart<? extends Minecart>> entity, Boolean acceptUpgrades) {
    this.defaultMaterial = defaultMaterial;
    this.entity = entity;
    this.acceptUpgrades = acceptUpgrades;
  }
  
  public Material getDefaultMaterial() {
    return defaultMaterial;
  }
  
  public Class<? extends CommonMinecart<? extends Minecart>> getEntityClass() {
    return entity;
  }

  public boolean doAcceptUpgrades() {
      return acceptUpgrades;
  }
}
