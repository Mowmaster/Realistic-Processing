package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;

/**
 * Created by KingMowmaster on 8/24/2017.
 */
public class BlockStonePileAnvil extends Block// implements ITileEntityProvider
{
    public BlockStonePileAnvil(String unloc, String registryName)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(References.MODID, registryName));
        this.setHardness(20);
        this.setResistance(20);
        this.setLightOpacity(10);
        this.setCreativeTab(REAL_TAB);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
