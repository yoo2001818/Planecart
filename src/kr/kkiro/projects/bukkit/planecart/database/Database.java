package kr.kkiro.projects.bukkit.planecart.database;

import java.util.ArrayList;
import java.util.List;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

public class Database {
	private static Database instance;
	private static List<Class<?>> databaseClasses;
	private Planecart plugin;

	public Database() {
		plugin = Planecart.getInstance();
		plugin.installDatabase();
	}

	public static void init() {
		instance = new Database();
	}

	public static Database getInstance() {
		return instance;
	}

	public static List<Class<?>> getDatabaseClasses() {
		if (databaseClasses == null) {
			databaseClasses = new ArrayList<Class<?>>();
			databaseClasses.add(AirportDB.class);
			databaseClasses.add(BeaconDB.class);
			databaseClasses.add(PlaneDirectionDB.class);
			databaseClasses.add(PlaneEntityDB.class);
			databaseClasses.add(PlaneGroupDB.class);
			databaseClasses.add(RunwayDB.class);
		}
		return databaseClasses;
	}
}