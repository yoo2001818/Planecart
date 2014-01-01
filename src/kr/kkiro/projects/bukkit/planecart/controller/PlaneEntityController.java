package kr.kkiro.projects.bukkit.planecart.controller;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import kr.kkiro.projects.bukkit.planecart.plane.PlaneEntity;

import com.bergerkiller.bukkit.common.controller.EntityController;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.common.utils.MathUtil;

public class PlaneEntityController
    extends EntityController<CommonMinecart<?>> {
  
  private static double LONG_GAP = 1.5f;
  private static double SHORT_GAP = 1.3f;
  
  private PlaneEntity planeEntity;
  
  public PlaneEntityController(PlaneEntity entity) {
    this.planeEntity = entity;
  }
  
  @Override
  public void onTick() {
    CommonMinecart<?> entity = this.getEntity();
    double movedX = 0;
    double movedY = 0;
    double movedZ = 0;
    //follow nearby entities.
    movedX = 0;
    movedY = 0;
    movedZ = 0;
    int movedTotal = 0;
    Vector vec;
    vec = doNeighbor(planeEntity.front(), 0, PlaneEntityController.LONG_GAP);
    if(vec != null) {
      movedX += vec.getX();
      movedY += vec.getY();
      movedZ += vec.getZ();
      movedTotal += 1;
    }
    vec = doNeighbor(planeEntity.left(), 90, PlaneEntityController.SHORT_GAP);
    if(vec != null) {
      movedX += vec.getX();
      movedY += vec.getY();
      movedZ += vec.getZ();
      movedTotal += 1;
    }
    vec = doNeighbor(planeEntity.right(), -90, PlaneEntityController.SHORT_GAP);
    if(vec != null) {
      movedX += vec.getX();
      movedY += vec.getY();
      movedZ += vec.getZ();
      movedTotal += 1;
    }
    vec = doNeighbor(planeEntity.back(), 180, PlaneEntityController.LONG_GAP);
    if(vec != null) {
      movedX += vec.getX();
      movedY += vec.getY();
      movedZ += vec.getZ();
      movedTotal += 1;
    }
    entity.vel.add(round(((movedX/movedTotal)-entity.getLocation().getX()-entity.vel.getX())/10), 
        round(((movedY/movedTotal)-entity.getLocation().getX()-entity.vel.getY())/10), round(((movedZ/movedTotal)-entity.getLocation().getX()-entity.vel.getZ())/10));
    //disables falling damage
    entity.setFallDistance(0);
    //do physics
    entity.setPosition(entity.loc.getX(), entity.loc.getY(), entity.loc.getZ());
    movedX = entity.vel.getX();
    movedY = entity.vel.getY();
    movedZ = entity.vel.getZ();
    boolean moved = Math.abs(movedX) > 0.001 || Math.abs(movedZ) > 0.001;
    this.onMove(entity.vel.x.fixNaN().get(),entity.vel.y.fixNaN().get(),entity.vel.z.fixNaN().get());
    //do velocity.
    entity.vel.x.multiply(0.91);
    entity.vel.z.multiply(0.91);
    //entity.vel.y.clamp(0.2);
    entity.vel.y.add(-0.08);
    //check rotation.
    float oldYaw = this.getEntity().loc.getYaw();
    float oldPitch = this.getEntity().loc.getPitch();
    float newYaw = oldYaw;
    float newPitch = oldPitch;
    if (Math.abs(movedX) > 0.000001 || Math.abs(movedZ) > 0.000001) {
      newYaw = MathUtil.getLookAtYaw(movedX, movedZ);
    }
    if (entity.isOnGround()) {
      //TODO check pitch, if too big then explode.
    } else if (moved && Math.abs(movedY) > 0.001) {
      newPitch = -1.0f * MathUtil.getLookAtPitch(-movedX, -movedY, -movedZ);
    }
    if (MathUtil.getAngleDifference(oldYaw, newYaw) > 90.0f) {
      while ((newYaw - oldYaw) >= 90.0f) {
        newYaw -= 180.0f;
      }
      while ((newYaw - oldYaw) < -90.0f) {
        newYaw += 180.0f;
      }
      newPitch = -newPitch;
    }
    while ((newYaw - oldYaw) <= -180.0f) {
      newYaw += 360.0f;
    }
    while ((newYaw - oldYaw) > 180.0f) {
      newYaw -= 360.0f;
    }
    this.getEntity().setRotation(newYaw,newPitch);
  }
  
  private double round(double i) {
    if(i < 0.1) return 0;
    else return i;
  }
  
  private Vector doNeighbor(PlaneEntity e, float ratio, double gap) {
    if(e == null) return null;
    if(e.entity.isDead()) {
      //meh. I'm dead too.
      entity.setDead(true);
    }
    Location eLoc = e.entity.getLocation();
    Vector dir = MathUtil.getDirection(eLoc.getYaw() + 90 + ratio, eLoc.getPitch());
    dir = dir.multiply(gap);
    Vector dir2 = new Vector();
    dir2.setX(eLoc.getX() + dir.getX());
    dir2.setY(eLoc.getY() + dir.getY());
    dir2.setZ(eLoc.getZ() + dir.getZ());
    return dir2;
  }
  
}
