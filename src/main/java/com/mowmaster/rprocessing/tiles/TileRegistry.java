package com.mowmaster.rprocessing.tiles;

import com.mowmaster.rprocessing.reference.References;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by KingMowmaster on 4/18/2017.
 */
public class TileRegistry
{
    public static void registerTile()
    {
        GameRegistry.registerTileEntity(TileClayBloomery.class, References.MODID + "TileClayBloomery");
    }
}
