package kr.kkiro.projects.bukkit.planecart.plane;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum PlaneEntityType {
  PASSENGER(Material.AIR, EntityType.MINECART, false), CHEST(
      Material.CHEST, EntityType.MINECART_CHEST, false), ENGINE(
      Material.FURNACE, EntityType.MINECART_FURNACE, true), COCKPIT(
      Material.WOOD_STEP, EntityType.MINECART, false), TANK(
      Material.WORKBENCH, EntityType.MINECART_FURNACE, true), DECORATION(
      null, EntityType.MINECART_FURNACE, false);

  private Material defaultMaterial;
  private EntityType entity;
  private boolean acceptUpgrades;

  private PlaneEntityType(Material defaultMaterial,
      EntityType entity,
      Boolean acceptUpgrades) {
    this.defaultMaterial = defaultMaterial;
    this.entity = entity;
    this.acceptUpgrades = acceptUpgrades;
  }

  public Material getDefaultMaterial() {
    return defaultMaterial;
  }

  public EntityType getEntityClass() {
    return entity;
  }

  public boolean doAcceptUpgrades() {
    return acceptUpgrades;
  }
}
