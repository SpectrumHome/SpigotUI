package eu.spigotui.ui.utils;

import java.awt.Point;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.UIComponent;

public abstract class Componentable {

//	TreeMap<UIComponent, Point> components = new TreeMap<UIComponent, Point>();
	List<Entry<UIComponent, Point>> components = new ArrayList<>();

	Player p;
	int repaintOffset = -1;

	public Componentable() {
	}

	public Componentable(Player p) {
		this.p = p;
	}

	public Componentable(int repaintOffset) {
		this.repaintOffset = repaintOffset;
	}

	public Componentable(Player p, int repaintOffset) {
		this.p = p;
		this.repaintOffset = repaintOffset;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	public void setRepaintOffset(int repaintOffset) {
		this.repaintOffset = repaintOffset;
	}

	public Player getPlayer() {
		return p;
	}

	public abstract Inventory getInventory();

	public List<Entry<UIComponent, Point>> getComponents() {
		return components;
	}

	public void repaint() {
		getInventory().clear();
		setSectionItems(getComponents(), getInventory(), this.repaintOffset);
		getPlayer().updateInventory();
	}

	public void setSectionItems(List<Entry<UIComponent, Point>> components, Inventory sectionInv, int offset) {
		components.forEach((e) -> {
			UIComponent c = e.getKey();
			Point pos = e.getValue();

			for (int x = 0; x < c.getSize().width; x++) {
				for (int y = 0; y < c.getSize().height; y++) {
					int absX = pos.x + x;
					int absY = pos.y + y;

					int slot = absY * 9 + absX;
					if (offset > 0) {
						slot = (slot + 9) % 36;
					}

					sectionInv.setItem(slot, c.getStack(x, y));
				}
			}
		});
	}

	public void setComponents(List<Entry<UIComponent, Point>> comps) {
		this.components = comps;
	}

	public UIComponent getComponentAt(Point position) {
		for (Entry<UIComponent, Point> e : components) {
			UIComponent comp = e.getKey();
			if (e.getValue().equals(position))
				return comp;
		}
		return null;
	}

	public void addComponent(Point pos, UIComponent comp) {
		this.components.add(new AbstractMap.SimpleEntry<UIComponent, Point>(comp, pos));
	}

	public void insertComponent(int listPos, Point pos, UIComponent comp) {
		this.components.set(listPos, new AbstractMap.SimpleEntry<UIComponent, Point>(comp, pos));
	}

	public int removeComponent(UIComponent comp) {
		int pos = -1;

		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getKey().equals(comp)) {
				pos = i;
				break;
			}
		}

		components.removeIf(e -> e.getKey().equals(comp));
		return pos;
	}

}
