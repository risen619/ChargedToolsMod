package com.github.risen619.chargedtools;

import com.github.risen619.chargedtools.setup.ClientProxy;
import com.github.risen619.chargedtools.setup.IProxy;
import com.github.risen619.chargedtools.setup.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main
{
	public static final String MODID = "chargedtools";
	private static final IProxy proxy = DistExecutor.runForDist(
		() -> () -> new ClientProxy(), () -> () -> new ServerProxy()
	);
	
	public static final Logger LOGGER = LogManager.getLogger();

	public Main()
	{
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		
		MinecraftForge.EVENT_BUS.register(proxy);
	}

//	private void setup(final FMLCommonSetupEvent event)
//	{
//	}
}
