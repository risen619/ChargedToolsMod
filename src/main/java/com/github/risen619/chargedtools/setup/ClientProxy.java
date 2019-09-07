package com.github.risen619.chargedtools.setup;

import com.github.risen619.chargedtools.Main;
import com.github.risen619.chargedtools.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientProxy implements IProxy
{
	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}
	
	@SubscribeEvent
	public void onBlockDestroyed(BlockEvent.BreakEvent event)
	{
		ModItems.onBlockBreak(event);
	};
}
