package com.github.risen619.chargedtools.items;

public class IronBattery extends BaseBattery
{
	public IronBattery()
	{
		super("iron_battery");
	}
	
	@Override
	public int getCapacity()
	{
		return 100;
	}
}
