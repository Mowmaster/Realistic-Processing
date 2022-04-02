package com.mowmaster.realisticprocessing.ModTab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RPTab extends CreativeModeTab
{
    public RPTab() {
        super("tab_realisticprocessing");
    }

    public static final RPTab TAB_ITEMS = new RPTab() {};

    @Override
    public ItemStack makeIcon() {
        //return new ItemStack(DeferredRegisterItems.COLOR_APPLICATOR.get());
        return new ItemStack(Items.STONE_PICKAXE);
    }
}
