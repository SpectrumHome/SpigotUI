package eu.spigotui.ui;

import java.awt.Dimension;

import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.utils.ClickAction;

public abstract class UIComponent {
	
	@Deprecated
	public SpigotUI ui;
	
	Dimension size = new Dimension(1, 1);
	
	public UIComponent() {}
	
	public UIComponent(Dimension size) {
		this.size = size;
	}
	
	public void setUI(SpigotUI ui) {
		this.ui = ui;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public abstract boolean onClick(int relX, int relY, ClickAction type);
	public abstract ItemStack getStack(int relX, int relY);
	
}
