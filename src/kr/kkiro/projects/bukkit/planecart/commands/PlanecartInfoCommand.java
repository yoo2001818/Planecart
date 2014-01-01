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
        sender.sendMessage("Planecart version " + plugin.getVersion());
        sender.sendMessage("Plugin by yooduknam(yoo2001818), exfl.kr(ghks1353).");
        sender.sendMessage("--------------------------");
        sender.sendMessage("License: GPLv3, Plugin info(Korean): http://goo.gl/LC3GkX");
        sender.sendMessage("GitHub: https://github.com/yoo2001818/Planecart");
        sender.sendMessage("--------------------------");
        sender.sendMessage("Type /planecart help to view plugin command list.");
        return true;
    }

}
