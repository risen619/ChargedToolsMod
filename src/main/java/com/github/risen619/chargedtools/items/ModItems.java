package com.github.risen619.chargedtools.items;

import com.github.risen619.chargedtools.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Hand;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.*;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModItems
{
	public static final Item charged_pickaxe = null;
	
	private static Map<String, Consumer<BlockEvent.BreakEvent>> onBlockBreakHandlers = new HashMap<>();
	
	static
	{
		onBlockBreakHandlers.put(ChargedPickaxe.getFullRegistryName(), ChargedPickaxe::onBlockBreak);
	}
	
	public static void onBlockBreak(BlockEvent.BreakEvent event)
	{
		PlayerEntity player = event.getPlayer();
		Item item = player.getHeldItem(Hand.MAIN_HAND).getItem();
		
		if(onBlockBreakHandlers.containsKey(item.getRegistryName().toString()))
		{
			onBlockBreakHandlers.get(item.getRegistryName().toString()).accept(event);
		}
		
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(new ChargedPickaxe());
	}
}
