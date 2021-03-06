package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;

/**
 * Created by KingMowmaster on 4/16/2017.
 */
public class BlockBasic extends Block{

    public BlockBasic(String unloc, String registryName)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(References.MODID, registryName));
        this.setHardness(20);
        this.setResistance(20);
        this.setLightOpacity(10);
        this.setCreativeTab(REAL_TAB);
    }
}
