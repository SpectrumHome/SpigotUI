package eu.spigotui.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import eu.spigotui.listener.UIListener;
import eu.spigotui.ui.top.SizedActiveInventory;
import eu.spigotui.ui.top.categories.ActiveInventory;
import eu.spigotui.utils.UISection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;

public class SpigotUI {

	public static final int size = 3;
	
	ActiveInventory activeInventory;
	HashMap<UIComponent, Point> bottomComponents = new HashMap<>();

	Runnable onClose;

	Player p;
	String name;

	public SpigotUI(Player p) {
		this.p = p;
		this.activeInventory = new SizedActiveInventory(this,size);
	}
	
	public SpigotUI(Player p, String name) {
		this.p = p;
		this.name = name;
		this.activeInventory = new SizedActiveInventory(this,size);
	}
	
	public SpigotUI(Player p,ActiveInventory acInv) {
		this.p = p;
		setActiveInventory(acInv);
	}

	public SpigotUI(Player p, ActiveInventory acInv, String name) {
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
			this.bottomComponents.put(comp, pos);
		}
		comp.setUI(this);
	}

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

		HashMap<UIComponent, Point> map = section == UISection.BOTTOM ? bottomComponents
				: activeInventory.getComponents();

		List<UIComponent> keys = new ArrayList<>(map.keySet());
		Collections.reverse(keys);

		for (UIComponent key : keys) {
			Point location = map.get(key);
			boolean hit = location.x >= absX && location.x < absX + key.size.width && location.y >= absY
					&& location.y < absY + key.size.height;
			if (hit) {
				return key.onClick(absX - location.x, absY - location.y, action);
			}
		}

		return true;
	}

	public void close() {
		UIListener.currentUIs.remove(this);
		onClose();
	}

	public void onClose() {
		if (onClose != null)
			onClose.run();
	}

	public void setActionOnClose(Runnable run) {
		this.onClose = run;
	}

	public void openInventory() {
		activeInventory.openInventory();
		openBottomInventory();
		UIListener.currentUIs.add(this);
	}

	public void openBottomInventory() {
		Inventory inv = p.getInventory();
		setSectionItems(bottomComponents, inv, 9);
		p.updateInventory();

	}

	public Inventory getBottomInv() {
		if (p.getOpenInventory() != null)
			return p.getOpenInventory().getBottomInventory();
		return null;
	}

	public void setSectionItems(HashMap<UIComponent, Point> components, Inventory sectionInv, int offset) {
		components.forEach((c, pos) -> {
			for (int x = 0; x < c.size.width; x++) {
				for (int y = 0; y < c.size.width; y++) {
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

	public static final class FakeAnvil extends ContainerAnvil {

		public FakeAnvil(EntityHuman entityHuman) {
			super(entityHuman.inventory, entityHuman.world, new BlockPosition(0, 0, 0), entityHuman);
		}

		@Override
		public boolean a(EntityHuman entityHuman) {
			return true;
		}
	}

}
