package kr.kkiro.projects.bukkit.planecart.utils;

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

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

import org.bukkit.ChatColor;

/* Thanks to Essentials */
/* https://github.com/essentials/Essentials/blob/2.x/Essentials/src/com/earth2me/essentials/I18n.java */

public class I18n {

  private static I18n instance;

  private static final String LANGUAGE = "language";
  private static final char COLOR_ESCAPE = '&';
  private Planecart plugin;
  private URLClassLoader customLoader;
  private final Locale defaultLocale = Locale.getDefault();
  private Locale currentLocale = defaultLocale;
  private ResourceBundle customBundle;
  private ResourceBundle localeBundle;
  private final ResourceBundle defaultBundle;
  private final Map<String, MessageFormat> messageFormatCache = new HashMap<String, MessageFormat>();

  public I18n(Planecart plugin) {
    instance = this;
    this.plugin = plugin;
    defaultBundle = ResourceBundle.getBundle(LANGUAGE, Locale.ENGLISH);
    localeBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale);
    try {
      URL[] urls = { plugin.getDataFolder().toURI().toURL() };
      customLoader = new URLClassLoader(urls);
      customBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale,
          customLoader);
    } catch (MalformedURLException e) {
      //Fine.. let's just use default bundle, shall we.
      customBundle = localeBundle;
    } catch (MissingResourceException e) {
      customBundle = localeBundle;
    }
  }

  public Locale getCurrentLocale() {
    return currentLocale;
  }

  public String translate(final String key) {
    try {
      try {
        return customBundle.getString(key);
      } catch (MissingResourceException e) {
        return localeBundle.getString(key);
      }
    } catch (MissingResourceException e) {
      plugin.logger.warning("Could not find key " + key + " in the locale "
          + currentLocale.getDisplayName() + "!");
      return defaultBundle.getString(key);
    }
  }

  public static String _(final String key, final Object... params) {
    if (instance == null) {
      return "";
    }
    if (params.length == 0) {
      return ChatColor.translateAlternateColorCodes(COLOR_ESCAPE,
          instance.translate(key)).replace("\\n", "\n");
    } else {
      return ChatColor.translateAlternateColorCodes(COLOR_ESCAPE,
          instance.format(key, params)).replace("\\n", "\n");
    }
  }

  public String format(final String key, final Object... params) {
    String format = translate(key);
    MessageFormat messageFormat = messageFormatCache.get(format);
    if (messageFormat == null) {
      try {
        messageFormat = new MessageFormat(format);
      } catch (IllegalArgumentException e) {
        plugin.logger.warning("key " + key + " in the locale "
            + currentLocale.getDisplayName() + " is invaild!");
        return format + " " + Arrays.toString(params);
      }
      messageFormatCache.put(format, messageFormat);
    }
    return messageFormat.format(params);
  }

  public void setCurrentLocale(String locale) {
    if (locale == null || locale.isEmpty())
      return;
    String[] parts = locale.split("[\\._]");
    if (parts.length == 1) {
      currentLocale = new Locale(parts[0]);
    }
    if (parts.length == 2) {
      currentLocale = new Locale(parts[0], parts[1]);
    }
    if (parts.length == 3) {
      currentLocale = new Locale(parts[0], parts[1], parts[2]);
    }
    ResourceBundle.clearCache();
    plugin.logger.info("Using language " + locale);
    localeBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale);
    try {
      customBundle = ResourceBundle.getBundle(LANGUAGE, currentLocale,
          customLoader);
    } catch (MissingResourceException e) {
      customBundle = localeBundle;
    }
  }
}
