package eu.spigotui.main;

import org.bukkit.plugin.java.JavaPlugin;

import eu.spigotui.ui.UIHandler;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		super.onEnable();
		UIHandler.init(this);
	}
}
