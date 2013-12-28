package kr.kkiro.projects.bukkit.planecart.controller;

import org.bukkit.entity.Minecart;

import com.bergerkiller.bukkit.common.controller.EntityController;
import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;

public abstract class PlaneEntityController<T extends CommonMinecart<?>> extends EntityController<CommonMinecart<Minecart>> {

}
