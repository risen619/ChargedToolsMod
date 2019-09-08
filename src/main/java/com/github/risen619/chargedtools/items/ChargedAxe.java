package com.github.risen619.chargedtools.items;

import com.github.risen619.chargedtools.Main;
import com.github.risen619.chargedtools.common.Tree;
import com.github.risen619.chargedtools.materials.ToolMaterials;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Set;

public class ChargedAxe extends ChargedTool
{
	public static final String REGISTRY_NAME = "charged_axe";
	
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE);
	
	protected ChargedAxe()
	{
		super(0, 0, ToolMaterials.chargedTool, EFFECTIVE_ON,
			new Properties().addToolType(ToolType.AXE, ToolMaterials.chargedTool.getHarvestLevel()).group(ItemGroup.TOOLS)
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
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		return Items.DIAMOND_AXE.getDestroySpeed(stack, state);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		return Items.DIAMOND_AXE.onItemUse(context);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		Tree tree = Tree.create(state, pos, worldIn);
		
		Main.LOGGER.info("Is Tree null: {}", tree == null);
		
		if(tree != null)
			tree.destroy();
		
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
}
