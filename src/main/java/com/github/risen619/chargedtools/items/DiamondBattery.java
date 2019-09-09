package com.github.risen619.chargedtools.items;

public class DiamondBattery extends BaseBattery
{
	public DiamondBattery()
	{
		super("diamond_battery");
	}
	
	@Override
	public int getCapacity()
	{
		return 1000;
	}
}
