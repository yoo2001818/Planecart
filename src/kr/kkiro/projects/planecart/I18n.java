package kr.kkiro.projects.planecart;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * 언어 파일을 로드하는 클래스입니다.
 * Java의 ResourceBundle을 사용해서 언어 파일을 로드하게 됩니다.
 * 
 * 이 클래스는 총 3가지 경로에서 언어 파일을 찾습니다.
 * 1. 기본 설정 파일입니다 (플러그인 jar 내부의 language.properties 파일)
 * 2. 언어 설정 파일입니다 (플러그인 jar 내부의 language_ko_KR.properties 파일)
 * 3. 커스텀 설정 파일입니다 (/plugins/Planecart/language_ko_KR.properties 파일)
 * 커스텀 설정 파일이 없으면 언어 설정 파일로, 
 * 언어 설정 파일이 없으면 기본 설정 파일로 failback합니다. 
 */
public class I18n {

  /**
   * 한번 생성한 객체를 _("key") 따위로 접근하기 위해 두는 변수입니다.
   */
  private static I18n instance;
  
  /**
   * 언어 설정 파일의 이름입니다.
   */
  public static final String LANGUAGE = "language";
  
  /**
   * 색상 코드를 이스케이프하기 위해 사용되는 글자입니다.
   */
  public static final char COLOR_ESCAPE = '&';
  
  /**
   * Planecart에 접근하기 위해 사용되는 변수입니다.
   */
  private Planecart plugin;
  
  /**
   * /plugins/Planecart/ 폴더의 리소스를 불러오기 위해 사용하는 커스텀 로더입니다.
   */
  private URLClassLoader customLoader;
  
  /**
   * 플러그인 기본으로 사용할 언어를 나타냅니다. (보통 영어)
   */
  private final Locale defaultLocale = Locale.getDefault();
  
  /**
   * 현재 사용하고 있는 언어를 담는 변수입니다.
   */
  private Locale currentLocale = defaultLocale;
  
  /**
   * 커스텀 설정 파일을 담는 변수입니다.
   */
  private ResourceBundle customBundle;
  
  /**
   * 언어 설정 파일을 담는 변수입니다.
   */
  private ResourceBundle localeBundle;
  
  /**
   * 기본 설정 파일을 담는 변수입니다.
   */
  private final ResourceBundle defaultBundle;
  
  /**
   * 메시지를 포맷해주는 객체들을 캐시하는 변수입니다..
   */
  private final Map<String, MessageFormat> messageFormatCache = new HashMap<String, MessageFormat>();
 
  public I18n(Planecart plugin) {
    instance = this;
    this.plugin = plugin;
    // 기본 설정 파일을 불러옵니다.
    defaultBundle = ResourceBundle.getBundle(LANGUAGE, defaultLocale);
    // 나머지 설정 파일은 refreshLanguageFile() 함수에서 불러옵니다.
    refreshLanguageFile();
  }
  
  /**
   * 언어 설정 파일과 커스텀 설정 파일을 불러오는 함수입니다.
   */
  private void refreshLanguageFile() {
    // 언어 설정 파일도 불러옵니다.
    localeBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale);
    try {
      // URL 객체에 플러그인 디렉토리(/plugins/Planecart/) 객체를 담습니다.
      URL[] urls = { plugin.getDataFolder().toURI().toURL() };
      // 커스텀 로더를 방금 생성한 URL 객체를 넣어 생성합니다.
      customLoader = new URLClassLoader(urls);
      // 커스텀 로더를 사용해 커스텀 설정 파일을 불러옵니다.
      customBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale, customLoader);
    } catch (MalformedURLException e) {
      // 보통 이 상황은 발생하지 않지만, 발생한다면 언어 설정 파일을 대신 사용합니다.
      customBundle = localeBundle;
    } catch (MissingResourceException e) {
      // 커스텀 설정 파일이 없으므로, 언어 설정 파일을 사용합니다.
      customBundle = localeBundle;
    }
  }
  
  /**
   * 현재 사용하고 있는 언어를 출력합니다.
   * @return 현재 사용중인 언어
   */
  public Locale getCurrentLocale() {
    return currentLocale;
  }
  
  /**
   * 언어 파일에서 해당 키의 값을 불러옵니다.
   * @param key 값을 불러올 키
   * @return 값
   */
  public String translate(String key) {
    try {
      try {
        // 커스텀 설정 파일을 먼저 사용하려고 시도합니다.
        return customBundle.getString(key);
      } catch (MissingResourceException e) {
        // 커스텀 설정 파일에 해당 키가 없으므로, 언어 설정 파일을 사용합니다.
        return localeBundle.getString(key);
      }
    } catch (MissingResourceException e) {
      // 언어 설정 파일에는 모든 키가 있어야 하므로 에러를 띄우고 기본 설정 파일을 사용합니다.
      // 단, 여기에서는 I18n을 사용해 에러 메시지를 표시하면 문제가 발생할 수 있으므로 하드코딩 합니다.
      plugin.logger.warning("Could not find key " + key + " in the locale " + currentLocale.getDisplayName() + "!");
      return defaultBundle.getString(key);
    }
  }
  
  /**
   * 언어 파일에서 해당 키의 값을 불러와 매개변수들로 포맷을 시킵니다.
   * @param key 값을 불러올 키
   * @param params 매개변수들
   * @return 포맷된 문자열
   */
  public String format(String key, Object... params) {
    // 포맷을 수행할 원본 문자열입니다.
    String format = translate(key);
    // 일단, 포맷 캐시에서 해당 포맷이 있는지 먼저 불러와 봅니다.
    MessageFormat messageFormat = messageFormatCache.get(format);
    if (messageFormat == null) {
      // 없다면 만듭니다.
      try {
        messageFormat = new MessageFormat(format);
      } catch (IllegalArgumentException e) {
        // 만약 포맷 문자열이 이상하다면, 에러 메시지를 띄우고 placeholder를 리턴합니다.
        plugin.logger.warning("key " + key + " in the locale " + currentLocale.getDisplayName() + " is invaild!");
        return format + ":" + Arrays.toString(params);
      }
      // 포맷 캐시에 포맷을 집어넣습니다.
      messageFormatCache.put(format, messageFormat);
    }
    // 포맷한 문자열을 출력합니다.
    return messageFormat.format(params);
  }
  
  public void setCurrentLocale(String locale) {
    // 언어 코드가 비어있으면 처리하지 않습니다.
    if(locale == null || locale.isEmpty()) return;
    // 언어 코드를 \ 혹은 . 혹은 _ 단위로 쪼갭니다.
    // ko.KR , ko_KR, ko\KR
    String[] parts = locale.split("[\\._]");
    // 언어 코드의 길이대로 객체를 생성합니다.
    if (parts.length == 1)
      currentLocale = new Locale(parts[0]);
    if (parts.length == 2)
      currentLocale = new Locale(parts[0], parts[1]);
    if (parts.length == 3)
      currentLocale = new Locale(parts[0], parts[2]);
    // 캐시들을 전부 지웁니다.
    ResourceBundle.clearCache();
    messageFormatCache.clear();
    // 콘솔에 사용하고 있는 언어를 표시합니다.
    plugin.logger.info("Using language " + locale);
    // 언어를 로드합니다.
    refreshLanguageFile();
  }
  
  /**
   * 전역 함수로서, I18n 객체를 불러와 포맷된 문자열을 리턴합니다.
   * <code>import static kr.kkiro.projects.planecart.I18n._</code> 따위로 임포트하면
   * <code>_("key")</code>를 바로 사용할 수 있어 편리합니다.
   * @param key 키
   * @param params 매개변수들
   * @return 포맷된 문자열
   */
  public static String _(String key, Object... params) {
    if (instance == null) {
      // 아직 객체가 초기화되지 않았으므로, 빈 값을 리턴합니다.
      return "";
    }
    String message = "";
    if (params.length == 0) {
      // 포맷할 필요가 없으므로, 문자열을 그대로 처리합니다.
      message = instance.translate(key);
    } else {
      // 문자열을 포맷합니다.
      message = instance.format(key, params);
    }
    // ChatColor과 \n 처리를 한 후 리턴합니다.
    return ChatColor.translateAlternateColorCodes(COLOR_ESCAPE, message).replace("\\n", "\n");
  }
  
  /**
   * 전역 함수로서, 문자열을 포맷해 <code>CommandSender</code>에게 보냅니다.
   * <code>import static kr.kkiro.projects.planecart.I18n.__</code> 따위로 임포트하면
   * <code>__(sender,"key")</code>를 바로 사용할 수 있어 편리합니다.
   * @param sender 문자열을 보낼 객체
   * @param key 키
   * @param params 매개변수들
   */
  public static void __(CommandSender sender, String key, Object... params) {
    // _ 함수를 사용해 문자열을 포맷합니다.
    String message = _(key, params);
    // \n 단위로 문자열을 쪼갭니다.
    String[] messages = message.split("\n");
    // 쪼갠 문자열 배열대로 보냅니다.
    for(String packet : messages) {
      sender.sendMessage(packet);
    }
  }
}
