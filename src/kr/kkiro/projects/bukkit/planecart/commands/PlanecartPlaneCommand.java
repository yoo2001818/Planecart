package kr.kkiro.projects.bukkit.planecart.commands;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;
import org.bukkit.command.CommandSender;

import java.util.List;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

public class PlanecartPlaneCommand extends Command {

    @Override
    public String getCommand() {
        return "plane";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getHelp() {
        return _("commandPlaneHelp");
    }

    @Override
    public boolean onCommand(Planecart plugin, CommandSender sender, String[] args) {
        sender.sendMessage(_("commandPlaneHelpTitle"));
        sender.sendMessage(_("commandPlaneHelpTip1"));
        sender.sendMessage(_("commandPlaneHelpTip2"));
        sender.sendMessage(_("commandPlaneHelpTip3"));
        sender.sendMessage(_("commandPlaneHelpTip4"));
        return true;
    }

}
