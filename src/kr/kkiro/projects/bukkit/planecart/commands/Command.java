package kr.kkiro.projects.bukkit.planecart.commands;

import org.bukkit.command.CommandSender;

public abstract class Command {
	
	public abstract String getCommand();
	public abstract String getUsage();
	public abstract String getHelp();
	public abstract boolean onCommand(CommandSender sender, String[] args);
}
