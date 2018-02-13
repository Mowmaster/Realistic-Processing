package com.mowmaster.rprocessing.recipes.oredict;

import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraftforge.oredict.OreDictionary;


public class OreDict
{
    public static void addEntries()
    {
        OreDictionary.registerOre("twigWood", ItemRegistry.twig);
        OreDictionary.registerOre("branchWood", ItemRegistry.branch);
        OreDictionary.registerOre("nuggetCharcoal", ItemRegistry.charcoalChunk);
    }
}
