package eu.spigotui.ui.active.categories;

import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.SpigotUI;
import eu.spigotui.ui.utils.Componentable;

public abstract class ActiveInventory extends Componentable {

	SpigotUI ui;
	Inventory inv;
	
	public ActiveInventory() {}

	public ActiveInventory(SpigotUI ui) {
		super(ui.getPlayer());
		this.ui = ui;
	}
	
	public SpigotUI getUi() {
		return ui;
	}

	public void setUi(SpigotUI ui) {
		this.ui = ui;
		this.setPlayer(ui.getPlayer());
	}

	public abstract void initComponents();

	public void openInventory() {
		if (inv != null)
			return;

		initComponents();

		inv = openActiveInventory();
		repaint();
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}

	/* also open inventory NOT ONLY RETURN */
	public abstract Inventory openActiveInventory();
	
	public String getName() {
		if(ui.getName()==null) return "GUI";
		else return ui.getName();
	}


}
