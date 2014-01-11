package kr.kkiro.projects.planecart;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 플레인카트에서 맨 처음 실행되는 클래스입니다.
 */

public class Planecart extends JavaPlugin {
  
  public Logger logger;
  public FileConfiguration config;
  public I18n i18n;
  
  @Override
  public void onEnable() {
    // 로거를 사용하기 편리하게 변수에 담습니다.
    logger = this.getLogger();
    // 마찬가지로 설정도 사용하기 편리하게 변수에 담습니다.
    config = this.getConfig();
    // I18n 객체를 초기화합니다.
    i18n = new I18n(this);
    // 설정에 적혀있는 언어를 로드합니다.
    i18n.setCurrentLocale(config.getString("general.language"));
  }
  
  @Override
  public void onDisable() {
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    
    return false;
  }

}
