package kr.kkiro.projects.bukkit.planecart.commands;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;
import kr.kkiro.projects.bukkit.planecart.actions.plane.PlaneCraftAction;
import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;
import kr.kkiro.projects.bukkit.planecart.exceptions.InvaildBlockException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaneCraftCommand extends Command {

	@Override
	public String getCommand() {
		return "plane craft";
	}

	@Override
	public String getUsage() {
		return "";
	}

	@Override
	public String getHelp() {
	  return _("commandPlaneCraftHelp");
	}

	@Override
	public boolean onCommand(Planecart plugin, CommandSender sender, String[] args) {
		if(sender instanceof Player) {
		  PlaneCraftAction action = new PlaneCraftAction(((Player)sender).getTargetBlock(null, 32));
		  action.issuer = (Player)sender;
		  action.cartifiy = args.length != 0;
		  try {
		    action.onExecute();
		  } catch (InvaildBlockException e) {
		    sender.sendMessage(_("actionPlaneCraftInvaild"));
		    return true;
		  }
		} else {
		  sender.sendMessage(_("onlyIngame"));
		}
    return true;
	}

}
