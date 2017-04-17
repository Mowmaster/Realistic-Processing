package com.mowmaster.rprocessing.configs;

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
    public static final CreativeTabs REAL_TAB = new CreativeTabs(0, "realTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.IRON_INGOT);
        }
    };
}
