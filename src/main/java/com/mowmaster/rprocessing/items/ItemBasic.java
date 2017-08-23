package com.mowmaster.rprocessing.items;


import com.mowmaster.rprocessing.reference.References;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;


public class ItemBasic extends Item
{
        public ItemBasic(String unlocName, String registryName) {
            this.setUnlocalizedName(unlocName);
            this.setRegistryName(new ResourceLocation(References.MODID, registryName));
            this.setCreativeTab(REAL_TAB);
        }

}
