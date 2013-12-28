package kr.kkiro.projects.bukkit.planecart.commands;

import kr.kkiro.projects.bukkit.planecart.locale.HelpLocale;

import org.bukkit.command.CommandSender;

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
		return HelpLocale.PLANE_CRAFT.get();
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		
		return false;
	}

}
