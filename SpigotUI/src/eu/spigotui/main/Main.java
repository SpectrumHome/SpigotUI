package eu.spigotui.main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import eu.spigotui.ui.SpigotUI;
import eu.spigotui.ui.UIHandler;
import eu.spigotui.ui.components.UIButton;
import eu.spigotui.utils.ItemBuilder;
import eu.spigotui.utils.UISection;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		super.onEnable();
		
		this.getCommand("uitest").setExecutor(new TestCommand());
		
		UIHandler.init(this);
		
	}
	
	public class TestCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				SpigotUI ui = new SpigotUI(p,InventoryType.valueOf(args[0]),"Test");
				ui.setActionOnClose(()->System.out.println("closed"));
				ui.addComponent(UISection.TOP,0, 0, new UIButton(new ItemBuilder(Material.DIAMOND_SWORD).setLore("lenni", "du", "kek").enchantEffect().build(),"NUDEL"));
				ui.addComponent(UISection.TOP,1, 0, new UIButton(new ItemStack(Material.WOOL),"wuhl"));
				ui.addComponent(UISection.BOTTOM,0, 0, new UIButton(new ItemStack(Material.APPLE),"eppel"));
				ui.openInventory();
			}
			return false;
		}
		
	}
}
