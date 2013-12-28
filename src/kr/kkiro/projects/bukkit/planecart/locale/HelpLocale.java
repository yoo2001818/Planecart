package kr.kkiro.projects.bukkit.planecart.locale;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

import com.bergerkiller.bukkit.common.localization.LocalizationEnum;

public class HelpLocale extends LocalizationEnum {
	public static final HelpLocale PLANE_CRAFT = new HelpLocale("planeCraftHelp", "Crafts plane at you point. Translation fail. :(");
	
	public HelpLocale(String name, String defValue) {
		super(name, defValue);
	}

	@Override
	public String get(String... args) {
		return Planecart.getInstance().getLocale(this.getName(), args);
	}

}
