package com.github.risen619.chargedtools.items;

import com.github.risen619.chargedtools.Main;
import com.github.risen619.chargedtools.materials.ToolMaterials;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChargedPickaxe extends ChargedTool
{
	private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
	
	public static final String REGISTRY_NAME = "charged_pickaxe";
	
	public static Set<Block> nonHarvestable = new HashSet<Block>(Arrays.asList(
		Blocks.BEDROCK, Blocks.OBSIDIAN, Blocks.LAVA, Blocks.WATER
	));
	
	public ChargedPickaxe()
	{
		super(0, 0, ToolMaterials.chargedTool, EFFECTIVE_ON,
			new Properties().addToolType(ToolType.PICKAXE, ToolMaterials.chargedTool.getHarvestLevel()).group(ItemGroup.TOOLS)
		);
		
		setRegistryName(REGISTRY_NAME);
		
		addPropertyOverride(new ResourceLocation(POWERED_NBT), new IItemPropertyGetter()
		{
			@OnlyIn(Dist.CLIENT)
			@Override
			public float call(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity)
			{
				return (
					itemStack.hasTag() && itemStack.getTag().contains(POWERED_NBT) &&
					itemStack.getTag().getBoolean(POWERED_NBT) ? 1 : 0
				);
			}
		});
	}
	
	public static String getFullRegistryName()
	{
		return Main.MODID + ":" + REGISTRY_NAME;
	}
	
//	@Override
//	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
//	{
//		super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
//	}
	
	public static void onBlockBreak(BlockEvent.BreakEvent event)
	{
		IWorld world = event.getWorld();
		ItemStack item = event.getPlayer().getHeldItem(Hand.MAIN_HAND);
		CompoundNBT nbt = getNBT(item);

		boolean poweredOn = nbt.getBoolean(POWERED_NBT);
		int charge = nbt.getInt(CHARGE_NBT);

		if(!poweredOn || charge <= 0)
		{
			event.setResult(Event.Result.DEFAULT);
			return;
		}

		for(int i = -1; i <= 1; i++)
		{
			if(i == 0) continue;

			BlockPos blockPos = event.getPos().add(0, i, 0);
			BlockState blockState = world.getBlockState(blockPos);

			if(!nonHarvestable.contains(blockState.getBlock()))
				world.destroyBlock(blockPos, true);
		}

		charge--;
		nbt.putInt(CHARGE_NBT, charge);
		item.setTag(nbt);

		if(charge <= 0)
			item = powerOff(item, event.getPlayer());
		
		event.setResult(Event.Result.DEFAULT);
	}
	
	@Override
	public boolean canHarvestBlock(BlockState blockIn)
	{
		int i = this.getTier().getHarvestLevel();
		
		if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE)
			return i >= blockIn.getHarvestLevel();
		
		Material material = blockIn.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		Material material = state.getMaterial();
		return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
	}
}
