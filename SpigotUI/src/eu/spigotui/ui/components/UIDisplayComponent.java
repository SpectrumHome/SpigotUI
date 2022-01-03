package eu.spigotui.ui.components;

import java.awt.Dimension;

import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.UIComponent;
import eu.spigotui.ui.utils.ClickAction;

public class UIDisplayComponent extends UIComponent {

	ItemStack stack;
	
	public UIDisplayComponent(ItemStack stack) {
		this.stack = stack;
	}
	
	public UIDisplayComponent(ItemStack stack, int width, int height) {
		super(new Dimension(width,height));
		this.stack = stack;
	}
	
	@Override
	public boolean onClick(int relX, int relY, ClickAction type) {
		return true;
	}

	@Override
	public ItemStack getStack(int relX, int relY) {
		return stack;
	}

}
