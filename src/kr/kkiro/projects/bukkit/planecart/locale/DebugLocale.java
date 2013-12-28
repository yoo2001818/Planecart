package kr.kkiro.projects.bukkit.planecart.locale;

import kr.kkiro.projects.bukkit.planecart.bukkit.Planecart;

import com.bergerkiller.bukkit.common.localization.LocalizationEnum;

public class DebugLocale extends LocalizationEnum {
	public static final DebugLocale INSTALL_DDL = new DebugLocale("installDDL", "Installing database due to first usage.");
	
	public DebugLocale(String name, String defValue) {
		super(name, defValue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String get(String... args) {
		return Planecart.getInstance().getLocale(this.getName(), args);
	}

}
