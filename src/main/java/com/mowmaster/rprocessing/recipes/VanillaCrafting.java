package com.mowmaster.rprocessing.recipes;

import com.mowmaster.rprocessing.blocks.BlockRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by KingMowmaster on 4/16/2017.
 */
public class VanillaCrafting {

    public static void ICraftingRecipes()
    {
        GameRegistry.addShapedRecipe(new ItemStack(BlockRegistry.clayBloomery, 1),new Object[]{"X X","X X", "XXX", 'X', new ItemStack(Blocks.CLAY)});
    }
}
