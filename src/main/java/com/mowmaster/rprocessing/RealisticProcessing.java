package com.mowmaster.rprocessing;


import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.configs.RealConfig;
import com.mowmaster.rprocessing.proxies.CommonProxy;
import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION)
public class RealisticProcessing
{
    @Mod.Instance(References.MODID)
    public static RealisticProcessing instance;

    @SidedProxy(serverSide = References.SERVER_SIDE, clientSide = References.CLIENT_SIDE)
    public static CommonProxy proxy;


    public static File configFile;
    public static File getConfigFile(){return configFile;}

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        configFile = new File(event.getModConfigurationDirectory() + "/" + References.MODID);
        configFile.mkdirs();
        RealConfig.InitConfig(new File(configFile.getPath(), References.MODID + ".cfg"));

        BlockRegistry.init();
        BlockRegistry.register();
        proxy.preInit();
        TileRegistry.init();
        TileRegistry.registerTile();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
