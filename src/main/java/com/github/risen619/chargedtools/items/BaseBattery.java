package com.github.risen619.chargedtools.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public abstract class BaseBattery extends Item
{
	public BaseBattery(String registryName)
	{
		super(new Properties().group(ItemGroup.TOOLS));
		
		setRegistryName(registryName);
	}
	
	public abstract int getCapacity();
}
