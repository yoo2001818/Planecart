package kr.kkiro.projects.bukkit.planecart.commands;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

import org.bukkit.command.CommandSender;

public abstract class Command {
	
	public abstract String getCommand();
	public abstract String getUsage();
	public abstract String getHelp();
	public abstract boolean onCommand(Planecart plugin, CommandSender sender, String[] args);
}
