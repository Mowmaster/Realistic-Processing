package com.mowmaster.rprocessing.items;

import com.mowmaster.rprocessing.reference.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class ItemRegistry
{


    public static Item leafpile;
    public static Item dryleafpile;
    public static Item twig;
    public static Item branch;
    public static Item hammer;
    public static Item charcoalChunk;
    public static Item debugItem;
    public static Item tempChecker;
    public static Item bloomeryClay;
    public static Item bloomeryBrick;





    public static void init()
    {
        leafpile = new ItemBasic("leaf","leaf",64);
        dryleafpile = new ItemBasic("dryleaf","dryleaf",64);
        twig = new ItemBasic("twig","twig",64);
        branch = new ItemBasic("branch","branch",64);
        hammer = new ItemBasic("tool_hammer","tool_hammer",1);
        charcoalChunk = new ItemBasic("charcoalchunk","charcoalchunk",64);
        debugItem = new ItemBasic("debug","debug",1);
        tempChecker = new ItemBasic("temp","temp",1);
        bloomeryBrick= new ItemBasic("bloomerybrick","bloomerybrick",64);
        bloomeryClay = new ItemBasic("bloomeryclay","bloomeryclay",64);

    }

    public static void register()
    {
        registerItem(leafpile);
        registerItem(dryleafpile);
        registerItem(twig);
        registerItem(branch);
        registerItem(hammer);
        registerItem(charcoalChunk);
        registerItem(debugItem);
        registerItem(tempChecker);
        registerItem(bloomeryBrick);
        registerItem(bloomeryClay);
    }

    public static void registerRenders()
    {
        registerRender(leafpile);
        registerRender(dryleafpile);
        registerRender(twig);
        registerRender(branch);
        registerRender(hammer);
        registerRender(charcoalChunk);
        registerRender(debugItem);
        registerRender(tempChecker);
        registerRender(bloomeryBrick);
        registerRender(bloomeryClay);
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