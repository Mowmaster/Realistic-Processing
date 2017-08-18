package com.mowmaster.rprocessing.tiles;

import com.mowmaster.rprocessing.blocks.BlockChoppingBlock;
import com.mowmaster.rprocessing.blocks.BlockClayBloomery;
import com.mowmaster.rprocessing.blocks.BlockFormedUnfiredBloomery;
import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class TileRegistry
{
    public static Block clayBloomery;
    public static Block formedbloomery;
    public static Block choppingblock;

    public static void init()
    {
        clayBloomery = new BlockClayBloomery("clayBloomery","clayBloomery");
        formedbloomery = new BlockFormedUnfiredBloomery("formedunfiredbloomery","formedunfiredbloomery");
        choppingblock = new BlockChoppingBlock("choppingblock","choppingblock");
    }

    public static void registerTile()
    {
        GameRegistry.registerTileEntity(TileClayBloomery.class, References.MODID + "TileClayBloomery");
        GameRegistry.registerTileEntity(TileUnfiredBloomery.class, References.MODID + "TileFormedUnfiredBloomery");
        GameRegistry.registerTileEntity(TileChoppingBlock.class, References.MODID + "TileChoppingBlock");
    }
}
