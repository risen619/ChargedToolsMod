package com.github.risen619.chargedtools.blocks;

import com.github.risen619.chargedtools.Main;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class Steel extends Block
{
	private static final String REGISTRY_NAME = "steel";
	
	public Steel()
	{
		super(
			Properties.create(Material.IRON)
			.hardnessAndResistance(10.0f)
			.sound(SoundType.ANVIL)
			.lightValue(15)
		);
		
		this.setRegistryName(Main.MODID, REGISTRY_NAME);
	}
}
