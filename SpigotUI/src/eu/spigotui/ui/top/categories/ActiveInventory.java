package eu.spigotui.ui.top.categories;

import java.awt.Point;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.SpigotUI;
import eu.spigotui.ui.UIComponent;

public abstract class ActiveInventory {

	HashMap<UIComponent, Point> components = new HashMap<>();
	SpigotUI ui;

	Inventory inv;

	public ActiveInventory() {
	}
	
	public ActiveInventory(SpigotUI ui) {
		this.ui = ui;
	}
	
	public void setUi(SpigotUI ui) {
		this.ui = ui;
	}
	
	public Player getPlayer() {
		return this.ui.getPlayer();
	}

	public abstract void initComponents();
	
	public void setComponents(HashMap<UIComponent,Point> comps) {
		this.components = comps;
	}

	public UIComponent getComponentAt(Point position) {
		for (UIComponent comp : components.keySet()) {
			if (components.get(comp).equals(position))
				return comp;
		}
		return null;
	}

	public HashMap<UIComponent, Point> getComponents() {
		return components;
	}

	public void addComponent(Point pos, UIComponent comp) {
		this.components.put(comp, pos);
	}

	public void openInventory() {
		if (inv != null)
			return;
		
		initComponents();

		inv = openActiveInventory();

		ui.setSectionItems(getComponents(), inv, -1);
		getPlayer().updateInventory();
	}

	/* also open inventory NOT ONLY RETURN */
	public abstract Inventory openActiveInventory();

	public Inventory getInv() {
		return inv;
	}

}
