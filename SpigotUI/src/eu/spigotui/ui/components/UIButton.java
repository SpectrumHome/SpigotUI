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

	public String getName() {
		return name;
	}
	
	public UIButton(ItemStack stack, int width, int height, String name) {
		super(new Dimension(width, height));
		this.stack = stack;
		this.name = name;
	}

	@Override
	public boolean onClick(int x, int y, ClickAction type) {
		if (this.onClick != null)
			onClick.run(type);
		ui.getPlayer().sendMessage("kekkkkkk");
		return true;
		
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
