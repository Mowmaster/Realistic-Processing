package com.mowmaster.rprocessing.items;

import com.mowmaster.rprocessing.reference.References;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class ItemRegistry
{


    public static Item leafpile;
    public static Item twig;
    public static Item branch;
    public static Item limb;





    public static void init()
    {
        leafpile = new ItemBasic("leafpile","leafpile");
        twig = new ItemBasic("twig","twig");
        branch = new ItemBasic("branch","branch");
        limb = new ItemBasic("limb","limb");

    }

    public static void register()
    {
        registerItem(leafpile);
        registerItem(twig);
        registerItem(branch);
        registerItem(limb);
    }

    public static void registerRenders()
    {
        registerRender(leafpile);
        registerRender(twig);
        registerRender(branch);
        registerRender(limb);
    }

    public static void registerItem(Item item)
    {

        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerRender(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(References.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void registerRender(Item item, int meta, String fileName)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(References.MODID, fileName), "inventory"));
    }




}