package eu.spigotui.ui;

import java.awt.Dimension;
import java.awt.Point;

import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.utils.ClickAction;
import eu.spigotui.ui.utils.Componentable;
import eu.spigotui.utils.BoundingBox;

public abstract class UIComponent extends BoundingBox {

	private Componentable parent;

	public UIComponent() {
	}

	public UIComponent(Dimension size) {
		setSize(size);
	}

	public UIComponent setPos(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}

	public UIComponent setPos(int x, int y, int z) {
		setX(x);
		setY(y);
		setZ(z);
		return this;
	}

	public UIComponent setLayer(int z) {
		setZ(z);
		return this;
	}

	@Override
	public void setZ(int z) {
		sort();
		super.setZ(z);
	}

	public Componentable getParent() {
		return parent;
	}

	public void setParent(Componentable parent) {
		this.parent = parent;
	}

	private void sort() {
		if (parent != null)
			parent.sortLayers();
	}

	public boolean hit(int absX, int absY) {
		Point location = getPos();
		Dimension size = getSize();
		boolean b1 = absX >= location.x, b2 = absX < location.x + size.width;
		boolean b3 = absY >= location.y, b4 = absY < location.y + size.height;
		return b1 && b2 && b3 && b4;
	}

	public abstract boolean onClick(int relX, int relY, ClickAction type);

	public abstract ItemStack getStack(int relX, int relY);

}
