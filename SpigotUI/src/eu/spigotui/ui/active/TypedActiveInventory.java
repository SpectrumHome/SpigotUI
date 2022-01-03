package eu.spigotui.ui.active;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.SpigotUI;
import eu.spigotui.ui.active.categories.ActiveInventory;

public class TypedActiveInventory extends ActiveInventory {

	InventoryType type;
	
	public TypedActiveInventory(SpigotUI ui,InventoryType type) {
		super(ui);
		this.type = type;
	}

	@Override
	public void initComponents() {}

	@Override
	public Inventory openActiveInventory() {
		Inventory inv =  Bukkit.createInventory(getPlayer(), type);
		getPlayer().openInventory(inv);
		return inv;
	}

}
