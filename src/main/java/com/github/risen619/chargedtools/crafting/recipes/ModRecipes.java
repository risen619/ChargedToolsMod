package com.github.risen619.chargedtools.crafting.recipes;

import com.github.risen619.chargedtools.Main;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MODID)
public class ModRecipes
{
	public static final IRecipeSerializer<ChargedToolRecharge> charged_tool_recharge_shapeless = null;
	
	@SubscribeEvent
	public static void register(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().registerAll(
			new ChargedToolRecharge.Serializer().setRegistryName(Main.MODID, ChargedToolRecharge.REGISTRY_NAME)
		);
	}
}
