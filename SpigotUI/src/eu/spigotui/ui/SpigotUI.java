package eu.spigotui.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import eu.spigotui.listener.UIListener;
import eu.spigotui.ui.components.UIButton;
import eu.spigotui.utils.UISection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

public class SpigotUI {

	HashMap<UIComponent, Point> topComponents = new HashMap<>();
	HashMap<UIComponent, Point> bottomComponents = new HashMap<>();

	Runnable onClose;

	InventoryType type;
	int height = 3;
	Player p;
	String name;

	public SpigotUI(Player p, InventoryType type) {
		this.p = p;
		this.type = type;
	}

	public SpigotUI(Player p, int height) {
		this.height = height;
		this.p = p;
	}

	public SpigotUI(Player p) {
		this.p = p;
	}

	public SpigotUI(Player p, InventoryType type, String name) {
		this.p = p;
		this.type = type;
		this.name = name;
	}

	public SpigotUI(Player p, int height, String name) {
		this.height = height;
		this.p = p;
		this.name = name;
	}

	public SpigotUI(Player p, String name) {
		this.p = p;
		this.name = name;
	}

	public Player getPlayer() {
		return p;
	}

	public void addComponent(UISection section, int x, int y, UIComponent comp) {
		addComponent(section, new Point(x, y), comp);
	}

	public void addComponent(UISection section, Point pos, UIComponent comp) {
		if (section == UISection.TOP) {
			this.topComponents.put(comp, pos);
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

		System.out.println(section.toString());

		HashMap<UIComponent, Point> map = section == UISection.BOTTOM ? bottomComponents : topComponents;

		List<UIComponent> keys = new ArrayList<>(map.keySet());
		Collections.reverse(keys);

		for (UIComponent key : keys) {
			UIButton comp = (UIButton) key;
			System.out.println(comp.getName());
			Point location = map.get(key);
			boolean hit = location.x >= absX && location.x < absX + key.size.width && location.y >= absY
					&& location.y < absY + key.size.height;
			if (hit) {
				key.onClick(absX - location.x, absY - location.y, action);
				break;
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
		openTopInventory();
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

	public Inventory getTopInv() {
		return topInv;
	}

	Inventory topInv;

	public void openTopInventory() {
		if (topInv != null)
			return;
		boolean packet = false;

		if (type != null) {
			switch (type) {
			case CRAFTING:
			case CREATIVE:
			case ENCHANTING:
			case MERCHANT:
				this.type = null;
				openTopInventory();
				return;
			case DROPPER:
				this.type = InventoryType.DISPENSER;
				openTopInventory();
				return;
			case ANVIL: {
				packet = true;
				EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();
				FakeAnvil fakeAnvil = new FakeAnvil(entityPlayer);
				int containerId = entityPlayer.nextContainerCounter();

				((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId,
						"minecraft:anvil", new ChatMessage("", new Object[] {}), 0));

				entityPlayer.activeContainer = fakeAnvil;
				entityPlayer.activeContainer.windowId = containerId;
				entityPlayer.activeContainer.addSlotListener(entityPlayer);
				entityPlayer.activeContainer = fakeAnvil;
				entityPlayer.activeContainer.windowId = containerId;
				topInv = fakeAnvil.getBukkitView().getTopInventory();
				break;
			}
			default:
				if (this.name == null)
					topInv = Bukkit.createInventory(p, type);
				else
					topInv = Bukkit.createInventory(p, type, this.name);
				break;
			}
		} else {
			if (this.name == null)
				topInv = Bukkit.createInventory(p, height * 9);
			else
				topInv = Bukkit.createInventory(p, height * 9, this.name);
		}

		setSectionItems(topComponents, topInv, -1);
		if (!packet)
			p.openInventory(topInv);
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

	public InventoryType getType() {
		return type;
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
