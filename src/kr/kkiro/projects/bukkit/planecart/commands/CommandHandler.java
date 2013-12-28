package kr.kkiro.projects.bukkit.planecart.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

	private static CommandHandler instance;
	
	private Map<String, Command> list;
	
	public void init() {
		instance = this;
		list = new HashMap<String, Command>();
		
		registerCommand(new PlaneCraftCommand());
	}
	
	public void registerCommand(Command command) {
		list.put(command.getCommand(), command);
	}
	
	public static CommandHandler getInstance() {
		return instance;
	}

}
