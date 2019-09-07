package com.github.risen619.chargedtools.crafting.recipes;

import com.github.risen619.chargedtools.Main;
import com.github.risen619.chargedtools.items.ChargedTool;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Map;

public class ChargedToolRecharge extends ShapelessRecipe
{
	public static String REGISTRY_NAME = "charged_tool_recharge_shapeless";
	
	private ChargedToolRecharge(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> ingredients)
	{
		super(id, group, output, ingredients);
	}
	
	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack output = super.getCraftingResult(inv);
		
		if(output.isEmpty()) return output;
		
		for(int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack ingredient = inv.getStackInSlot(i);
			if(!ingredient.isEmpty() && ingredient.getItem() instanceof ChargedTool)
			{
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(ingredient);
				
				for(Enchantment e : enchantments.keySet())
				{
					output.addEnchantment(e, enchantments.get(e));
				}
				
				output.setDamage(ingredient.getDamage());
				
				break;
			}
		}
		
		return output;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModRecipes.charged_tool_recharge_shapeless;
	}
	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<ChargedToolRecharge>
	{
		@Override
		public ChargedToolRecharge read(ResourceLocation recipeId, JsonObject json)
		{
			String group = JSONUtils.getString(json, "group", "");
			ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
			
			NonNullList<Ingredient> ingredients = NonNullList.create();
			for(JsonElement e : JSONUtils.getJsonArray(json, "ingredients"))
				ingredients.add(CraftingHelper.getIngredient(e));
			
			if(ingredients.isEmpty())
				throw new JsonParseException("No ingredients for shapeless recipe");
				
			return new ChargedToolRecharge(recipeId, group, output, ingredients);
		}
		
		@Override
		public ChargedToolRecharge read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			String group = buffer.readString(Short.MAX_VALUE);
			int numIngredients = buffer.readVarInt();
			NonNullList<Ingredient> ingredients = NonNullList.withSize(numIngredients, Ingredient.EMPTY);
			
			for (int i = 0; i < ingredients.size(); i++)
				ingredients.set(i, Ingredient.read(buffer));
			
			ItemStack result = buffer.readItemStack();
			
			return new ChargedToolRecharge(recipeId, group, result, ingredients);
		}
		
		@Override
		public void write(PacketBuffer buffer, ChargedToolRecharge recipe)
		{
			buffer.writeString(recipe.getGroup());
			buffer.writeVarInt(recipe.getIngredients().size());
			
			for (Ingredient i : recipe.getIngredients()) {
				i.write(buffer);
			}
			
			buffer.writeItemStack(recipe.getRecipeOutput());
		}
	}
}
