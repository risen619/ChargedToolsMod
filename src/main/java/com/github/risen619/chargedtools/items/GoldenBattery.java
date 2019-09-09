package com.github.risen619.chargedtools.items;

public class GoldenBattery extends BaseBattery
{
	public GoldenBattery()
	{
		super("golden_battery");
	}
	
	@Override
	public int getCapacity()
	{
		return 500;
	}
}
