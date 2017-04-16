package com.mowmaster.rprocessing;


import com.mowmaster.rprocessing.proxies.CommonProxy;
import com.mowmaster.rprocessing.reference.References;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION)
public class RealisticProcessing
{
    @Mod.Instance(References.MODID)
    public static RealisticProcessing instance;

    @SidedProxy(serverSide = References.SERVER_SIDE, clientSide = References.CLIENT_SIDE)
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
