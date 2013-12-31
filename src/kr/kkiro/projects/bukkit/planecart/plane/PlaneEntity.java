package kr.kkiro.projects.bukkit.planecart.plane;

import org.bukkit.entity.Minecart;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;

public abstract class PlaneEntity {

  public CommonMinecart<? extends Minecart> entity;
  public PlaneData data;
  public PlaneGroup group;
  
	public PlaneEntity() {
		// TODO Auto-generated constructor stub
	}

}
