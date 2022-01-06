package eu.spigotui.ui;

import java.util.ArrayList;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
			UISection section = e.getClickedInventory() == ui.getInventory() ? UISection.BOTTOM : UISection.TOP;
			int slot = e.getSlot();
			e.setCancelled(ui.onClicked(slot % 9, (int) Math.floor(slot / 9), section,
					ClickAction.getFromStates(e.isLeftClick(), e.isRightClick(), e.isShiftClick())));
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		boolean left = false, right = false;
		if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)
			left = true;
		else if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)
			right = true;
		if ((left || right) && e.getItem() != null && SpigotUI.clickEvents.containsKey(e.getItem())) {
			SpigotUI.clickEvents.get(e.getItem()).onClick(ClickAction.getFromStates(left, right, p.isSneaking()), p);
			;
		}

	}

	public static SpigotUI getUIByPlayer(HumanEntity p) {
		for (SpigotUI ui : currentUIs)
			if (ui.getPlayer() == p)
				return ui;
		return null;
	}
}
