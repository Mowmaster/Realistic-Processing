package com.mowmaster.rprocessing.configs;

import com.mowmaster.rprocessing.blocks.BlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by KingMowmaster on 4/16/2017.
 */
public class RealTab
{
    public static final CreativeTabs REAL_TAB = new CreativeTabs("realTab")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(BlockRegistry.clayBloomery);
        }
    };
}
