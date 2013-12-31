package kr.kkiro.projects.bukkit.planecart.commands;

//TODO Planecart help command

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;
import org.bukkit.command.CommandSender;

import java.util.List;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

public class PlanecartHelpCommand extends Command {
    @Override
    public String getCommand() {
        return "planecart help";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getHelp() {
        return _("commandPlanecartHelp");
    }

    @Override
    public boolean onCommand(Planecart plugin, CommandSender sender, String[] args) {
        sender.sendMessage(_("commandPlanecartHelpTitle", (args[0].isEmpty()?"1":args[0])));
        List<Command> commands = plugin.commander.getCommands();
        for (int i=0;i<commands.size();++i) {
            Command entry = commands.get(i);
            sender.sendMessage("/" + entry.getCommand() + " => " + entry.getHelp() );
        }
        return true;
    }
}