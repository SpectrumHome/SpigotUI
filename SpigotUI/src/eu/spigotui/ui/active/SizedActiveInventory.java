package eu.spigotui.ui.active;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.active.categories.ActiveInventory;

public class SizedActiveInventory extends ActiveInventory {

	int height;

	public SizedActiveInventory(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void initComponents() {
	}

	@Override
	public Inventory openActiveInventory() {
		Inventory inv = Bukkit.createInventory(getPlayer(), height * 9, getName());
		getPlayer().openInventory(inv);
		return inv;
	}

}
