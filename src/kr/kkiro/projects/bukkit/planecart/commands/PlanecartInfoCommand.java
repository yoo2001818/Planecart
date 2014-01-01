package kr.kkiro.projects.bukkit.planecart.commands;

//TODO:: /planecart

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;
import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

import org.bukkit.command.CommandSender;

public class PlanecartInfoCommand extends Command {

  @Override
  public String getCommand() {
    return "planecart";
  }

  @Override
  public String getUsage() {
    return "";
  }

  @Override
  public String getHelp() {
    return _("commandPlanecartInfo");
  }

  @Override
  public boolean onCommand(Planecart plugin, CommandSender sender, String[] args) {
    // TODO use i18n
    sender.sendMessage("Planecart ver " + plugin.getVersion());
    return true;
  }

}
