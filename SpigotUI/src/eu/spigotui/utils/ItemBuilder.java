package eu.spigotui.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	Material material;
	Integer damage;
	String name;
	String[] lore;
	String owner;
	int itemCount = 1;

	HashMap<Enchantment, Integer> enchants = new HashMap<>();
	private ItemBuilder() { }

	public ItemBuilder(Material material) {
		this.material = material;
	}


	public ItemBuilder setItemCount(int count) {
		this.itemCount = count;
		return this;
	}

	public ItemBuilder setOwner(String owner) {
		this.owner = owner;
		this.material = Material.SKULL_ITEM;
		this.damage = SkullType.PLAYER.ordinal();
		return this;
	}

	public ItemBuilder setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	public ItemBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		this.lore = lore;
		return this;
	}

	public ItemBuilder enchant(Enchantment enchant, int level) {
		enchants.put(enchant, level);
		return this;
	}

	public ItemBuilder enchantEffect() {
		enchants.put(Enchantment.DAMAGE_ALL, -1);
		return this;
	}

	public static ItemBuilder skull(String owner) {
		return new ItemBuilder().setOwner(owner);
	}

	public ItemStack build() {
		ItemStack item = new ItemStack(material, itemCount);
		ItemMeta meta = item.getItemMeta();
		for (Enchantment ench : enchants.keySet()) {
			int level = enchants.get(ench);
			if (level < 0) {
				meta.addEnchant(ench, 1, true);
			} else {
				meta.addEnchant(ench, level, true);
			}
		}
		if (name != null)
			meta.setDisplayName(name);
		if (damage != null)
			item.setDurability(damage.byteValue());
		if (lore != null) {
			ArrayList<String> lore = new ArrayList<String>();
			for (String s : this.lore)
				lore.add(s);
			meta.setLore(lore);
		}
		if (owner != null && meta instanceof SkullMeta) {
			((SkullMeta) meta).setOwner(owner);
		}
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack paneFiller(int color, String name) {
		ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color);
		ItemMeta meta = pane.getItemMeta();
		meta.setDisplayName(name);
		pane.setItemMeta(meta);
		return pane;
	}

}
