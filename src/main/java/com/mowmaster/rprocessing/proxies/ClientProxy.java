package com.mowmaster.rprocessing.proxies;


import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import com.mowmaster.rprocessing.tiles.render.RenderTileClayBloomery;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
    @Override
    @SideOnly(Side.CLIENT)
    public void preInit()
    {
        BlockRegistry.registerRenders();
        ClientRegistry.bindTileEntitySpecialRenderer(TileClayBloomery.class,new RenderTileClayBloomery());
    }

}
