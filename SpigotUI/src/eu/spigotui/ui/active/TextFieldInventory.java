package eu.spigotui.ui.active;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.spigotui.ui.active.categories.AnvilInventory;
import eu.spigotui.ui.components.UIDisplayComponent;
import eu.spigotui.utils.ItemBuilder;

public class TextFieldInventory extends AnvilInventory {

	String defValue;
	String lastValue;

	public TextFieldInventory(String defValue) {
		this.defValue = defValue;
	}
	
	public String getDefValue() {
		return defValue;
	}
	
	public String getLastValue() {
		return lastValue;
	}
	

	@Override
	public void initComponents() {
		addComponent( new UIDisplayComponent(new ItemBuilder(Material.PAPER).setName(defValue).build()));
	}

	public String getValue() {
		ItemStack valueComp = getInventory().getItem(9);
		if (valueComp != null && valueComp.getType()==Material.PAPER) {
			lastValue = valueComp.getItemMeta().getDisplayName().trim();
			return lastValue;
		}
		return defValue;
	}
	
	public boolean valueChanged() {
		if(lastValue==null) return false;
		return !defValue.trim().equals(lastValue.trim());
	}

	public void displayError(String message) {
		getInventory().setItem(9,
				new ItemBuilder(Material.STAINED_GLASS_PANE).setName("�c" + message).setDamage(14).build());
		getPlayer().updateInventory();
	}

}
