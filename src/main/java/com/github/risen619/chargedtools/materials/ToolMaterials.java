package com.github.risen619.chargedtools.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum ToolMaterials implements IItemTier
{
	chargedTool(10.0f, 9.0f, 1561, 3, 10, Items.DIAMOND);
	
	private int maxUses, harvestLevel, enchantability;
	private float efficiency, attackDamage;
	private Item repairMaterial;
	
	private ToolMaterials(float attackDamage, float efficiency, int maxUses, int harvestLevel, int enchantability, Item repairMaterial)
	{
		this.attackDamage = attackDamage;
		this.efficiency = efficiency;
		this.maxUses = maxUses;
		this.harvestLevel = harvestLevel;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial;
	}
	
	@Override
	public int getMaxUses()
	{
		return maxUses;
	}
	
	@Override
	public float getEfficiency()
	{
		return efficiency;
	}
	
	@Override
	public float getAttackDamage()
	{
		return attackDamage;
	}
	
	@Override
	public int getHarvestLevel()
	{
		return harvestLevel;
	}
	
	@Override
	public int getEnchantability()
	{
		return enchantability;
	}
	
	@Override
	public Ingredient getRepairMaterial()
	{
		return Ingredient.fromItems(repairMaterial);
	}
}
