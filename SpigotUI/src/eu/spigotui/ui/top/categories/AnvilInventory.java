package eu.spigotui.ui.top.categories;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;

import eu.spigotui.ui.SpigotUI.FakeAnvil;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

public abstract class AnvilInventory extends ActiveInventory {

	@Override
	public Inventory openActiveInventory() {
		EntityPlayer entityPlayer = ((CraftPlayer) getPlayer()).getHandle();
		FakeAnvil fakeAnvil = new FakeAnvil(entityPlayer);
		int containerId = entityPlayer.nextContainerCounter();

		((CraftPlayer) getPlayer()).getHandle().playerConnection.sendPacket(
				new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("", new Object[] {}), 0));

		entityPlayer.activeContainer = fakeAnvil;
		entityPlayer.activeContainer.windowId = containerId;
		entityPlayer.activeContainer.addSlotListener(entityPlayer);
		entityPlayer.activeContainer = fakeAnvil;
		entityPlayer.activeContainer.windowId = containerId;
		return fakeAnvil.getBukkitView().getTopInventory();
	}

}
