package kr.kkiro.projects.bukkit.planecart.bukkit;

import static kr.kkiro.projects.bukkit.planecart.utils.I18n._;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import kr.kkiro.projects.bukkit.planecart.commands.CommandHandler;
import kr.kkiro.projects.bukkit.planecart.database.AirportDB;
import kr.kkiro.projects.bukkit.planecart.database.Database;
import kr.kkiro.projects.bukkit.planecart.utils.I18n;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.bergerkiller.bukkit.common.Common;
import com.bergerkiller.bukkit.common.PluginBase;

public class Planecart extends PluginBase {

  public FileConfiguration config;
  public I18n i18n;
  public Logger logger;
  public CommandHandler commander;

  @Override
  public void disable() {
    // TODO Auto-generated method stub

  }

  @Override
  public void enable() {
    logger = this.getLogger();
    // Read configuration first!
    config = this.getConfig();
    i18n = new I18n(this);
    i18n.setCurrentLocale(config.getString("general.language"));
    commander = new CommandHandler(this);
  }

  @Override
  public boolean command(CommandSender arg0, String arg1, String[] arg2) {
    commander.processCommand(arg0, arg1, arg2);
    return true;
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
      info(_("installDDL"));
      this.installDDL();
    }
  }

  public void info(String message) {
    getLogger().info(message);
  }

  public void warning(String message) {
    getLogger().warning(message);
  }

  public void severe(String message) {
    getLogger().severe(message);
  }

}
