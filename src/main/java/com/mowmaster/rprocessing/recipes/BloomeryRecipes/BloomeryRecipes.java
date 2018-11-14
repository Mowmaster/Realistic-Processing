package com.mowmaster.rprocessing.recipes.BloomeryRecipes;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class BloomeryRecipes
{
    private static final BloomeryRecipes ALLOWED_BASE = new BloomeryRecipes();
    private final Map<ItemStack, BloomeryHashMap> allowedList = Maps.<ItemStack, BloomeryHashMap>newHashMap();
    public static BloomeryRecipes instance()
    {
        return ALLOWED_BASE;
    }


    private BloomeryRecipes()
    {
        //if(Loader.isModLoaded("immersiveengineering")) {immersiveE = true;}
        //this.addSpellEffect(2050025025, new PotionEffect(PotionRegistry.POTION_PETRIFIED));
    }


    public void addOreToAllowed(ItemStack ore, Boolean isAllowed)
    {
        this.allowedList.put(ore,new BloomeryHashMap(ore,isAllowed));
    }

    public boolean hasOre(ItemStack getOre)
    {
        for (Map.Entry<ItemStack, BloomeryHashMap> entry : this.allowedList.entrySet())
        {
            if (getOre == entry.getKey())
            {
                return true;
            }
        }

        return false;
    }

    public Boolean getAllowed(ItemStack getOre)
    {
        for (Map.Entry<ItemStack, BloomeryHashMap> entry : this.allowedList.entrySet())
        {
            if (getOre == entry.getKey())
            {
                return entry.getValue().getOutput();
            }
        }

        return false;
    }
}
