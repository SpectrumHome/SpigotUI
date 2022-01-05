package eu.spigotui.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.active.SizedActiveInventory;
import eu.spigotui.ui.active.categories.ActiveInventory;
import eu.spigotui.ui.utils.ClickAction;
import eu.spigotui.ui.utils.Componentable;
import eu.spigotui.utils.UISection;

public abstract class SpigotUI extends Componentable {

	public static final int size = 3;

	ActiveInventory activeInventory;

	Runnable onClose;

	Player p;
	String name;

	public SpigotUI(Player p) {
		super(p, 9);
		this.p = p;
		this.activeInventory = new SizedActiveInventory(this, size);
	}

	public SpigotUI(Player p, String name) {
		super(p, 9);
		this.p = p;
		this.name = name;
		this.activeInventory = new SizedActiveInventory(this, size);
	}

	public SpigotUI(Player p, ActiveInventory acInv) {
		super(p, 9);
		this.p = p;
		setActiveInventory(acInv);
	}

	public SpigotUI(Player p, ActiveInventory acInv, String name) {
		super(p, 9);
		this.p = p;
		this.name = name;
		setActiveInventory(acInv);
	}

	public void setActiveInventory(ActiveInventory activeInventory) {
		activeInventory.setUi(this);
		this.activeInventory = activeInventory;
	}

	public Player getPlayer() {
		return p;
	}

	public void addComponent(UISection section, UIComponent comp) {
		if (section == UISection.TOP) {
			this.activeInventory.addComponent(comp);
		} else {
			this.addComponent(comp);
		}
	}

	public boolean removeComponent(UIComponent comp) {
		return activeInventory.removeComponent(comp) ? true : super.removeComponent(comp);
	}

	// TODO: replaceComponent

	public boolean onClicked(int rawX, int rawY, UISection section, ClickAction action) {

		int absX = rawX;
		int absY = rawY;
		int slot = rawX + rawY * 9;

		if (section == UISection.BOTTOM) {
			slot -= 9;
			slot += 36;
			slot %= 36;

			absX = slot % 9;
			absY = (int) Math.floor(slot / 9);
		}

		List<UIComponent> map = section == UISection.BOTTOM ? getComponents() : activeInventory.getComponents();

		// List<UIComponent> keys = new ArrayList<>(map.keySet());

		for (UIComponent key : map) {
			Point location = key.getPos();
			Dimension size = key.getSize();
			boolean hit = location.x >= absX && location.x < absX + size.width && location.y >= absY
					&& location.y < absY + size.height;
			if (hit) {
				return key.onClick(absX - location.x, absY - location.y, action);
			}
		}

		return true;
	}

	/* Only bottom inventory */
	@Override
	public Inventory getInventory() {
		if (p.getOpenInventory() != null)
			return p.getOpenInventory().getBottomInventory();
		return null;
	}

	public void close() {
		p.closeInventory();
	}

	public void onClose() {
		UIListener.currentUIs.remove(this);
		if (onClose != null)
			onClose.run();
		UIHandler.loadItemCache(p);
	}

	public void setActionOnClose(Runnable run) {
		this.onClose = run;
	}

	public void openInventory() {
		this.reset();
		initComponents();
		UIHandler.saveItemCache(p);
		activeInventory.openInventory();
		super.repaint();
		UIListener.currentUIs.add(this);
	}

	public void repaint() {
		activeInventory.repaint();
		super.repaint();
	}

	public void repaintTop() {
		activeInventory.repaint();
	}

	public void repaintBottom() {
		super.repaint();
	}
	
	public abstract void initComponents();

}
