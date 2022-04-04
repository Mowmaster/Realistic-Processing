package com.mowmaster.realisticprocessing;

import com.mojang.logging.LogUtils;
import com.mowmaster.realisticprocessing.Capabilities.AirCapability;
import com.mowmaster.realisticprocessing.Registry.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.mowmaster.realisticprocessing.Utilities.References.MODNAME;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("realisticprocessing")
public class realisticprocessing
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public realisticprocessing()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.register(new ClientRegistry()));
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PedestalConfig.commonSpec);
        //eventBus.register(PedestalConfig.class);

        DeferredRegisterItems.ITEMS.register(eventBus);
        DeferredRegisterBlocks.BLOCKS.register(eventBus);
        DeferredRegisterTileBlocks.BLOCKS.register(eventBus);
        DeferredBlockEntityTypes.BLOCK_ENTITIES.register(eventBus);
        DeferredRecipeSerializers.RECIPES.register(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        //LOGGER.info("HELLO FROM PREINIT");
        //DustPacketHandler.registerMessages();
    }

    private void setupClient(final FMLClientSetupEvent event)
    {
        LOGGER.info("Initialize "+MODNAME+" Block Entity Renders");
        ClientRegistry.registerBlockEntityRenderers();

        //LOGGER.info("Initialize "+MODNAME+" Tooltip Renders");
        //MinecraftForgeClient.registerTooltipComponentFactory(ItemTooltipComponent.class, ClientItemTooltipComponent::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        //InterModComms.sendTo("realisticprocessing", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        /*LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));*/
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void attachCapabilities(final RegisterCapabilitiesEvent event) {
            AirCapability.register(event);
        }
    }
}
