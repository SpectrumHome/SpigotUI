package eu.spigotui.ui.top;

import java.awt.Point;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.components.UIDisplayComponent;
import eu.spigotui.ui.top.categories.AnvilInventory;
import eu.spigotui.utils.ItemBuilder;

public class TextFieldInventory extends AnvilInventory {

	public String defValue;
	public String lastValue;
	
	public TextFieldInventory(String defValue) {
		this.defValue = defValue;
	}

	@Override
	public void initComponents() {
		addComponent(new Point(0,0), new UIDisplayComponent(new ItemBuilder(Material.WOOL).setName(defValue).build()));
	}
	
	public String getValue() {
		ItemStack valueComp = getInv().getItem(9);
		if(valueComp!=null) {
			lastValue = valueComp.getItemMeta().getDisplayName().trim();
			return lastValue;
		}
		return defValue;
	}
	
	public String getLastValue() {
		return lastValue;
	}
	
	public void displayError(String message) {
		getInv().setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§c"+message).setDamage(14).build());
		getPlayer().updateInventory();
	}

}
