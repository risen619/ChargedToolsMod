package com.github.risen619.chargedtools.items;

import com.github.risen619.chargedtools.Main;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModItems
{
	public static final Item charged_pickaxe = null;
	public static final Item charged_axe = null;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
			new ChargedPickaxe(), new ChargedAxe()
		);
	}
}
