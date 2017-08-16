package com.mowmaster.rprocessing.tiles;

import com.mowmaster.rprocessing.blocks.BlockClayBloomery;
import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class TileRegistry
{
    public static Block clayBloomery;

    public static void init()
    {
        clayBloomery = new BlockClayBloomery("clayBloomery","clayBloomery");
    }

    public static void registerTile()
    {
        GameRegistry.registerTileEntity(TileClayBloomery.class, References.MODID + "TileClayBloomery");
    }
}
