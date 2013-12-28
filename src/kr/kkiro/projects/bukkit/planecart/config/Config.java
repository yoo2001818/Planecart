package kr.kkiro.projects.bukkit.planecart.config;

public class Config {

	private static Config instance;
	
	public void init() {
		instance = this;
	}
	
	public static Config getInstance() {
		return instance;
	}

}
