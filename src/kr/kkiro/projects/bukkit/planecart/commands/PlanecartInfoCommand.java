package kr.kkiro.projects.bukkit.planecart.commands;

//TODO:: /planecart

import kr.kkiro.projects.bukkit.planecart.actions.plane.PlaneCraftAction;
import kr.kkiro.projects.bukkit.planecart.exceptions.InvaildBlockException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

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
    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage("Planecart");
         return true;
    }

}
