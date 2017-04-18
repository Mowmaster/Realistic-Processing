package com.mowmaster.rprocessing.proxies;


import com.mowmaster.rprocessing.blocks.BlockRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
    @Override
    @SideOnly(Side.CLIENT)
    public void preInit()
    {
        BlockRegistry.registerRenders();
    }

}
