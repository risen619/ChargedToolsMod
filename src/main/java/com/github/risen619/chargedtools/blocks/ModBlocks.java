package com.github.risen619.chargedtools.blocks;

import com.github.risen619.chargedtools.Main;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModBlocks
{
	public static final Block steel = null;
	
	@SubscribeEvent
	public static void onBlocksRegistry(final RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(new Steel());
	}
	
	@SubscribeEvent
	public static void onItemsRegistry(final RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
			new BlockItem(steel, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(steel.getRegistryName())
		);
	}
	
}
