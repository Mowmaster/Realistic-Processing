package com.mowmaster.rprocessing.proxies;


import com.mowmaster.rprocessing.handlers.PlaceHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;


public class CommonProxy {
    public void init()
    {
        PlaceHandler handler = new PlaceHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
    }

    public void preInit()
    {

    }

    public void registerModelBakeryVarients()
    {

    }
}
