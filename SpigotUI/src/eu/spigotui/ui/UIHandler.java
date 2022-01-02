package eu.spigotui.ui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import eu.spigotui.listener.UIListener;

public class UIHandler {
	
	static UIListener listener;
	
	public static void init(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(listener = new UIListener(), plugin);
	}

}
