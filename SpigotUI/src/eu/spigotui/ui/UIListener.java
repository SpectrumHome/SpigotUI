package eu.spigotui.ui;

import java.util.ArrayList;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import eu.spigotui.ui.utils.ClickAction;
import eu.spigotui.utils.UISection;

public class UIListener implements Listener {
	public static ArrayList<SpigotUI> currentUIs = new ArrayList<>();

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity p = e.getPlayer();
		SpigotUI ui;
		if ((ui = getUIByPlayer(p)) != null)
			ui.onClose();

	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		HumanEntity p = e.getWhoClicked();
		SpigotUI ui;
		if ((ui = getUIByPlayer(p)) != null) {
			UISection section = e.getClickedInventory()==ui.getInventory() ? UISection.BOTTOM : UISection.TOP;
			int slot = e.getSlot();
			e.setCancelled(ui.onClicked(slot % 9, (int) Math.floor(slot / 9), section,
					ClickAction.getFromStates(e.isLeftClick(), e.isRightClick(), e.isShiftClick())));
		}
	}

	public static SpigotUI getUIByPlayer(HumanEntity p) {
		for (SpigotUI ui : currentUIs)
			if (ui.getPlayer() == p)
				return ui;
		return null;
	}
}
