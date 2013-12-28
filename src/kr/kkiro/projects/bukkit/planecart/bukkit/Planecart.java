package kr.kkiro.projects.bukkit.planecart.bukkit;

import java.util.List;

import javax.persistence.PersistenceException;

import kr.kkiro.projects.bukkit.planecart.database.AirportDB;
import kr.kkiro.projects.bukkit.planecart.database.Database;
import kr.kkiro.projects.bukkit.planecart.locale.DebugLocale;

import org.bukkit.command.CommandSender;

import com.bergerkiller.bukkit.common.Common;
import com.bergerkiller.bukkit.common.PluginBase;

public class Planecart extends PluginBase {
	
	private static Planecart instance;

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enable() {
		instance = this;

	}
	
	@Override
	public boolean command(CommandSender arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMinimumLibVersion() {
		return Common.VERSION;
	}
	
	@Override
	public List<Class<?>> getDatabaseClasses() {
		return Database.getDatabaseClasses();
	}
	
	public void installDatabase() {
		try {
			this.getDatabase().find(AirportDB.class).findRowCount();
		} catch (PersistenceException ex) {
			info(DebugLocale.INSTALL_DDL.get());
			this.installDDL();
		}
	}
	
	public static Planecart getInstance() {
		return instance;
	}
	
	public static void info(String message) {
		getInstance().getLogger().info(message);
	}

	public static void warning(String message) {
		getInstance().getLogger().warning(message);
	}

	public static void severe(String message) {
		getInstance().getLogger().severe(message);
	}
	
}
