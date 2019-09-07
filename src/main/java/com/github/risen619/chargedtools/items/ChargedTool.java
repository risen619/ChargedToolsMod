package com.github.risen619.chargedtools.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class ChargedTool extends ToolItem
{
	public static final String POWERED_NBT = "powered";
	public static final String CHARGE_NBT = "charge";
	
	protected ChargedTool(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveOn, Properties properties)
	{
		super(attackDamageIn, attackSpeedIn, tier, effectiveOn, properties);
	}
	
	protected static CompoundNBT getNBT(ItemStack itemStack)
	{
		CompoundNBT nbt;
		
		if(itemStack.hasTag())  nbt = itemStack.getTag();
		else nbt = new CompoundNBT();
		
		if(!nbt.contains(POWERED_NBT)) nbt.putBoolean(POWERED_NBT, false);
		if(!nbt.contains(CHARGE_NBT)) nbt.putInt(CHARGE_NBT, 100);
		
		return nbt;
	}
	
	protected static ItemStack powerOn(ItemStack itemStack, PlayerEntity player)
	{
		CompoundNBT nbt = getNBT(itemStack);
		
		if(nbt.getInt(CHARGE_NBT) <= 0)
		{
			player.sendStatusMessage(new StringTextComponent("Your tool is discharged!"), true);
			return itemStack;
		}
		
		nbt.putBoolean(POWERED_NBT, true);
		itemStack.setTag(nbt);
		
		player.sendStatusMessage(new StringTextComponent("Powered on!"), true);
		
		return itemStack;
	}
	
	protected static ItemStack powerOff(ItemStack itemStack, PlayerEntity player)
	{
		CompoundNBT nbt = getNBT(itemStack);
		
		nbt.putBoolean(POWERED_NBT, false);
		itemStack.setTag(nbt);
		
		player.sendStatusMessage(new StringTextComponent("Powered off!"), true);
		
		return itemStack;
	}
	
	public static ItemStack recharge(ItemStack itemStack)
	{
		CompoundNBT nbt = getNBT(itemStack);
		nbt.putInt(CHARGE_NBT, 100);
		itemStack.setTag(nbt);
		
		return itemStack;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack itemStack = player.getHeldItem(hand);
		
		if(world.isRemote) return ActionResult.newResult(ActionResultType.PASS, itemStack);
		
		CompoundNBT nbt = getNBT(itemStack);
		
		Boolean poweredOn = nbt.getBoolean(POWERED_NBT);
		if(poweredOn) itemStack = powerOff(itemStack, player);
		else itemStack = powerOn(itemStack, player);
		
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemStack);
	}
	
	@Override
	public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> lines, ITooltipFlag flag)
	{
		super.addInformation(itemStack, world, lines, flag);
		lines.add(new StringTextComponent("Charge: " + getNBT(itemStack).getInt(CHARGE_NBT) + "/100"));
	}
}
