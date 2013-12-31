package kr.kkiro.projects.bukkit.planecart.commands;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

import java.util.HashMap;
import java.util.Map;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

import org.bukkit.command.CommandSender;

public class CommandHandler {
	
	private Map<String, Command> list;
	private Planecart plugin;
	
	public CommandHandler(Planecart plugin) {
	  this.plugin = plugin;
		list = new HashMap<String, Command>();
		
		registerCommand(new PlaneCraftCommand());
	}
	
	public void registerCommand(Command command) {
		list.put(command.getCommand(), command);
	}
	
	public void processCommand(CommandSender arg0, String arg1, String[] arg2) {
	  String issuedCommand = arg1;
	  if(arg2.length != 0) issuedCommand = arg1 + " " + arg2[0];
	  Command command = list.get(issuedCommand.toLowerCase());
	  if(command == null) {
	    arg0.sendMessage(_("commandInvaild"));
	    return;
	  }
	  String[] args = new String[Math.max(0, arg2.length-1)];
	  for(int i = 1; i < arg2.length; ++i) {
	    args[i-1] = arg2[i];
	  }
	  command.onCommand(plugin, arg0, args);
	}
	

}
