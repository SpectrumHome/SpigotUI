package eu.spigotui.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.active.SizedActiveInventory;
import eu.spigotui.ui.active.categories.ActiveInventory;
import eu.spigotui.ui.utils.ClickAction;
import eu.spigotui.ui.utils.Componentable;
import eu.spigotui.utils.UISection;

public class SpigotUI extends Componentable {

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

	public void addComponent(UISection section, int x, int y, UIComponent comp) {
		addComponent(section, new Point(x, y), comp);
	}

	public void addComponent(UISection section, Point pos, UIComponent comp) {
		if (section == UISection.TOP) {
			this.activeInventory.addComponent(pos, comp);
		} else {
			this.addComponent(pos, comp);
		}
		comp.setUI(this);
	}
	
	public void insertComponent(int listPos, UISection section, int x, int y, UIComponent comp) {
		insertComponent(listPos, section, new Point(x, y), comp);
	}

	public void insertComponent(int listPos, UISection section, Point pos, UIComponent comp) {
		if (section == UISection.TOP) {
			this.activeInventory.insertComponent(listPos, pos, comp);
		} else {
			this.insertComponent(listPos, pos, comp);
		}
		comp.setUI(this);
	}

	public int removeComponent(UIComponent comp) {
		int pos = -1;
		if((pos = activeInventory.removeComponent(comp)) > -1)
			return pos;
		return super.removeComponent(comp);
	}

	//TODO: replaceComponent
	
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

		List<Entry<UIComponent, Point>> map = new ArrayList<>(section == UISection.BOTTOM ? getComponents()
				: activeInventory.getComponents());
		
		//List<UIComponent> keys = new ArrayList<>(map.keySet());
		
		Collections.reverse(map);

		for (Entry<UIComponent, Point> e : map) {
			UIComponent key = e.getKey();
			Point location = e.getValue();
			boolean hit = location.x >= absX && location.x < absX + key.size.width && location.y >= absY
					&& location.y < absY + key.size.height;
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

}
