package eu.spigotui.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.active.SizedActiveInventory;
import eu.spigotui.ui.active.categories.ActiveInventory;
import eu.spigotui.ui.components.UIDisplayComponent;
import eu.spigotui.ui.utils.ClickAction;
import eu.spigotui.ui.utils.Componentable;
import eu.spigotui.ui.utils.ItemClickAction;
import eu.spigotui.utils.ItemBuilder;
import eu.spigotui.utils.UISection;

public abstract class SpigotUI extends Componentable {

	public static HashMap<ItemStack, ItemClickAction> clickEvents = new HashMap<>();

	public static final int size = 3;

	ActiveInventory activeInventory;

	Runnable onClose;

	Player p;
	String name;

	public SpigotUI(Player p) {
		super(p, 9);
		this.p = p;
		setActiveInventory(new SizedActiveInventory(size));
	}

	public SpigotUI(Player p, String name) {
		super(p, 9);
		this.p = p;
		this.name = name;
		setActiveInventory(new SizedActiveInventory(size));
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

	public SpigotUI setActiveInventory(ActiveInventory activeInventory) {
		activeInventory.setUi(this);
		this.activeInventory = activeInventory;
		return this;
		}

	public Player getPlayer() {
		return p;
	}

	public SpigotUI addComponent(UISection section, UIComponent comp) {
		if (section == UISection.TOP) {
			this.activeInventory.addComponent(comp);
		} else {
			this.addComponent(comp);
		}
		return this;
	}

	public SpigotUI paintBackground(UISection section) {
		UIComponent background = new UIDisplayComponent(ItemBuilder.paneFiller(7, "�8-"), 100, 100).setPos(0, 0, -100);
		this.addComponent(section, background);
		return this;
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
		map = new ArrayList<UIComponent>(map);
		Collections.reverse(map);

		for (UIComponent key : map) {
			Point location = key.getPos();
			Dimension size = key.getSize();
			boolean hit = location.x >= absX && location.x < absX + size.width && location.y >= absY
					&& location.y < absY + size.height;
			if (hit) {
				System.out.println(key);
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

	public SpigotUI setActionOnClose(Runnable run) {
		this.onClose = run;
		return this;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/*
	 * STATIC
	 */

	public static void addClickListener(ItemStack stack, ItemClickAction action) {
		clickEvents.put(stack, action);
	}
}
