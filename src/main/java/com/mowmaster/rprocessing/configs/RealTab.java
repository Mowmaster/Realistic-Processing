package com.mowmaster.rprocessing.configs;

import com.mowmaster.rprocessing.blocks.BlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
