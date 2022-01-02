package eu.spigotui.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	
	Material material;
	Byte damage;
	String name;
	String[] lore;

	HashMap<Enchantment,Integer> enchants = new HashMap<>();
	
	public ItemBuilder(Material material) {
		this.material = material;
	}
	
	public ItemBuilder setDamage(int damage) {
		this.damage = (byte)damage;
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
	
	public ItemStack build() {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		for(Enchantment ench : enchants.keySet()) {
			int level = enchants.get(ench);
			if(level<0) {
				meta.addEnchant(ench, 1, true);
			} else {
				meta.addEnchant(ench, level, true);
			}
		}
		if(name != null)
			meta.setDisplayName(name);
		if(damage != null)
			item.setDurability(damage);
		if(lore != null) {
			ArrayList<String> lore = new ArrayList<String>();
			for(String s : this.lore)
				lore.add(s);
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		return item;
	}

}
