package eu.spigotui.ui;

import java.awt.Dimension;

import org.bukkit.inventory.ItemStack;

public abstract class UIComponent {
	
	public SpigotUI ui;
	
	Dimension size = new Dimension(1, 1);
	
	public UIComponent() {}
	
	public UIComponent(Dimension size) {
		this.size = size;
	}
	
	public void setUI(SpigotUI ui) {
		this.ui = ui;
	}
	
	public abstract boolean onClick(int relX, int relY, ClickAction type);
	public abstract ItemStack getStack(int relX, int relY);
	
}
