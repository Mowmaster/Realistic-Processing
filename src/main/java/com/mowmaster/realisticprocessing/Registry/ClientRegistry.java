package com.mowmaster.realisticprocessing.Registry;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "realisticprocessing", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry
{

    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {

        /*
        event.getItemColors().register((stack, color) ->
         {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterItems.COLOR_APPLICATOR.get());
        */

        //ItemModelPropertiesDust.dustItemModes(DeferredRegisterItems.PEDESTAL_UPGRADE_IMPORT.get());

        /*
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterTileBlocks.BLOCK_PEDESTAL.get());
        */
    }

    @SubscribeEvent
    public static void registerBlockColor(ColorHandlerEvent.Block event) {
    /*
        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterTileBlocks.BLOCK_PEDESTAL.get());
    */
    }

    public static void registerBlockEntityRenderers()
    {
        //BlockEntityRenderers.register(DeferredBlockEntityTypes.PEDESTAL.get(), BasePedestalBlockEntityRenderer::new);
    }
}
