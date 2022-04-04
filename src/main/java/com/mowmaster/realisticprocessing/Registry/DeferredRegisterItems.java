package com.mowmaster.realisticprocessing.Registry;

import com.mowmaster.realisticprocessing.Items.GameItems.*;
import com.mowmaster.realisticprocessing.Items.Tools.BaseHammerItem;
import com.mowmaster.realisticprocessing.ModTab.RPTab;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.C;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;


public class DeferredRegisterItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);




    public static final RegistryObject<Item> HAMMER_WOOD = ITEMS.register("hammer_wood",
            () -> new BaseHammerItem(Tiers.WOOD,100));

    public static final RegistryObject<Item> ITEM_STRAW = ITEMS.register("straw",
            () -> new StrawItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static final RegistryObject<Item> ITEM_AIR_PIPE = ITEMS.register("air_pipe",
            () -> new AirPipeItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static final RegistryObject<Item> ITEM_CLAY = ITEMS.register("bloomery_clay",
            () -> new ClayItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static final RegistryObject<Item> ITEM_CLAYDUST = ITEMS.register("bloomeryclay_dust",
            () -> new DustItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static final RegistryObject<Item> ITEM_BLOOMERYBRICK = ITEMS.register("bloomery_brick",
            () -> new BrickItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static final RegistryObject<Item> ITEM_BLOOM = ITEMS.register("metal_bloom",
            () -> new BloomItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static final RegistryObject<Item> ITEM_SLAG = ITEMS.register("bloomery_slag",
            () -> new SlagItem(new Item.Properties().tab(RPTab.TAB_ITEMS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
