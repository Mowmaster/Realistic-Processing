package com.mowmaster.rprocessing;


import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.proxies.CommonProxy;
import com.mowmaster.rprocessing.recipes.VanillaCrafting;
import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileRegistry;
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
        BlockRegistry.init();
        BlockRegistry.register();
        proxy.preInit();
        TileRegistry.registerTile();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        VanillaCrafting.ICraftingRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
