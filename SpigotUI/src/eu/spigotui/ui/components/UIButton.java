package eu.spigotui.ui.components;

import java.awt.Dimension;

import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.ClickAction;
import eu.spigotui.ui.UIComponent;
import eu.spigotui.utils.ValueRunnable;

public class UIButton extends UIComponent {

	ItemStack stack;

	ValueRunnable<ClickAction> onClick;

	String name;
	
	public UIButton(ItemStack stack,String name) {
		this.stack = stack;
		this.name = name;
	}

	public UIButton(ItemStack stack, int width, int height, String name) {
		super(new Dimension(width, height));
		this.stack = stack;
		this.name = name;
	}
	
	public UIButton(ItemStack stack,String name,ValueRunnable<ClickAction> onClick) {
		this.stack = stack;
		this.name = name;
		this.onClick = onClick;
	}

	public UIButton(ItemStack stack, int width, int height, String name,ValueRunnable<ClickAction> onClick) {
		super(new Dimension(width, height));
		this.stack = stack;
		this.name = name;
		this.onClick = onClick;
	}

	@Override
	public boolean onClick(int x, int y, ClickAction type) {
		if (this.onClick != null)
			onClick.run(type);
		return true;
		
	}

	public String getName() {
		return name;
	}
	
	@Override
	public ItemStack getStack(int relX, int relY) {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public UIButton setOnClick(ValueRunnable<ClickAction> onClick) {
		this.onClick = onClick;
		return this;
	}

}
