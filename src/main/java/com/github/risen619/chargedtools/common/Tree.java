package com.github.risen619.chargedtools.common;

import com.github.risen619.chargedtools.Main;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class Tree
{
	private static final Set<Block> LOGS = Sets.newHashSet(Blocks.ACACIA_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.JUNGLE_LOG, Blocks.OAK_LOG, Blocks.SPRUCE_LOG);
	private static final Set<Block> GROUND = Sets.newHashSet(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK);
	private static final Set<Block> LEAVES = Sets.newHashSet(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES);
	
	private World world;
	
	private Set<BlockPos> logs = new HashSet<>();
	private boolean hasLeaves = false;
	private boolean isOnGround = false;
	
	private Tree() { }
	
	public static Tree create(BlockState blockState, BlockPos blockPos, World world)
	{
		if(!isLog(blockState)) return null;
		
		Tree tree = new Tree();
		tree.world = world;
		
		tree.analyze(blockPos);
		
		if(!tree.hasLeaves || !tree.isOnGround || tree.logs.size() == 0) return null;
		
		return tree;
	}
	
	private static boolean isLog(BlockState blockState)
	{
		return LOGS.contains(blockState.getBlock());
	}
	
	private void checkBlockAround(BlockPos blockPos)
	{
		if(!LOGS.contains(world.getBlockState(blockPos).getBlock())) return;
		
		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				for(int z = -1; z <= 1; z++)
				{
					if(x == 0 && y == 0 && z == 0) continue;
					
					BlockPos pos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(pos);
					
					if(LEAVES.contains(state.getBlock())) hasLeaves = true;
					
					if(isLog(state) && !logs.contains(pos))
					{
						if(GROUND.contains(world.getBlockState(pos.add(0, -1, 0)).getBlock())) isOnGround = true;
						
						logs.add(pos);
						checkBlockAround(pos);
					}
				}
			}
		}
	}
	
	private void analyze(BlockPos blockPos)
	{
		checkBlockAround(blockPos);
	}
	
	private void addLog(BlockState logState, BlockPos logPos)
	{
		if(!LOGS.contains(logState.getBlock())) return;
		
		logs.add(logPos);
	}
	
	public void destroy()
	{
		for(BlockPos blockPos : logs)
			world.destroyBlock(blockPos, true);
	}
	
	@Override
	public String toString()
	{
		return "{ hasLeaves: " + hasLeaves + "; logsSize: " + logs.size() + "; }";
	}
}
