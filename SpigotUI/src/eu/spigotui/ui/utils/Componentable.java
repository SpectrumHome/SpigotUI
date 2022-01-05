package eu.spigotui.ui.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.UIComponent;

public abstract class Componentable {

//	TreeMap<UIComponent, Point> components = new TreeMap<UIComponent, Point>();
//	List<Entry<UIComponent, Point>> components = new ArrayList<>();
	private ArrayList<UIComponent> components = new ArrayList<>();

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
	
	public void reset() {
		this.components = new ArrayList<UIComponent>();
	}

	public abstract Inventory getInventory();

	public List<UIComponent> getComponents() {
		return components;
	}

	public void repaint() {
		getInventory().clear();
		setSectionItems(getInventory(), this.repaintOffset);
		getPlayer().updateInventory();
	}

	public void setSectionItems(Inventory sectionInv, int offset) {
		components.forEach((c) -> {

			for (int x = 0; x < c.getSize().width; x++) {
				for (int y = 0; y < c.getSize().height; y++) {
					int absX = c.getX() + x;
					int absY = c.getY() + y;

					int slot = absY * 9 + absX;
					if (offset > 0) {
						slot = (slot + 9) % 36;
					}

					sectionInv.setItem(slot, c.getStack(x, y));
				}
			}
		});
	}
	
	public void sortLayers() {
		components.sort((e1, e2) -> e1.getZ() - e2.getZ());
	}

	public UIComponent getComponentAt(Point position) {
		for (UIComponent comp : components) {
			if (comp.getPos().equals(position))
				return comp;
		}
		return null;
	}

	public void addComponent(UIComponent comp) {
		this.components.add(comp);
		comp.setParent(this);
		sortLayers();
	}

//	@Deprecated
//	public void insertComponent(int listPos, Point pos, UIComponent comp) {
//		this.components.set(listPos, new AbstractMap.SimpleEntry<UIComponent, Point>(comp, pos));
//	}

	public boolean removeComponent(UIComponent comp) {
		comp.setParent(null);
		return components.remove(comp);
	}

}
