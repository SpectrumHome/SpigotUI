package eu.spigotui.ui.active.categories;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;
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
