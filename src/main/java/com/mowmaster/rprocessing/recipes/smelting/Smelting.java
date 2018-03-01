package com.mowmaster.rprocessing.recipes.smelting;

import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;


public class Smelting
{
    public static void init()
    {
        for(ItemStack stack : OreDictionary.getOres("twigWood")){ GameRegistry.addSmelting(stack, new ItemStack(ItemRegistry.charcoalChunk,1), 0.05f); }
        for(ItemStack stack : OreDictionary.getOres("stickWood")){ GameRegistry.addSmelting(stack, new ItemStack(ItemRegistry.charcoalChunk,2), 0.05f); }
        for(ItemStack stack : OreDictionary.getOres("branchWood")){ GameRegistry.addSmelting(stack, new ItemStack(ItemRegistry.charcoalChunk,4), 0.05f); }
        GameRegistry.addSmelting(ItemRegistry.bloomeryClay,new ItemStack(ItemRegistry.bloomeryBrick,1), 0.05f);
    }
}
