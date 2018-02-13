package com.mowmaster.rprocessing.recipes.fuels;

import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelTypes implements IFuelHandler
{
    @Override
    public int getBurnTime(ItemStack fuel){
        if(fuel.getItem() == ItemRegistry.dryleafpile){
            return 25;
            //8 Leaves per item smelted
        }
        else if(fuel.getItem() == ItemRegistry.twig)
        {
            return 50;
        }
        else if(fuel.getItem() == ItemRegistry.branch)
        {
            return 200;
        }
        else if(fuel.getItem() == ItemRegistry.charcoalChunk)
        {
            return 200;
        }
        return 0;
    }
}
