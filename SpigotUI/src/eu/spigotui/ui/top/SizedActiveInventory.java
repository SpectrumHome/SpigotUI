package eu.spigotui.ui.top;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.SpigotUI;
import eu.spigotui.ui.top.categories.ActiveInventory;

public class SizedActiveInventory extends ActiveInventory {

	int height;
	
	public SizedActiveInventory(SpigotUI ui,int height) {
		super(ui);
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public void initComponents() {}

	@Override
	public Inventory openActiveInventory() {
		Inventory inv = Bukkit.createInventory(getPlayer(), height*9);
		return inv;
	}

}
