package eu.spigotui.ui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class UIHandler {
	
	static UIListener listener;
	public static HashMap<Player,Map<Integer, ItemStack>> itemCache = new HashMap<>();
	
	public static void saveItemCache(Player p) {
		HashMap<Integer, ItemStack> cache = new HashMap<>();
		Inventory playerInv = p.getInventory();
		for (int i = 0; i < playerInv.getSize(); i++) {
			ItemStack stack = playerInv.getItem(i);
			cache.put(i, stack);
		}
		itemCache.put(p, cache);
		playerInv.clear();
	}
	
	public static void loadItemCache(Player p) {
		p.getInventory().clear();
		if (itemCache.containsKey(p)) {
			for (int i : itemCache.get(p).keySet()) {
				p.getInventory().setItem(i, itemCache.get(p).get(i));
			}
			itemCache.remove(p);
			p.updateInventory();
		}
	}
	
	public static void init(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(listener = new UIListener(), plugin);
	}

}
