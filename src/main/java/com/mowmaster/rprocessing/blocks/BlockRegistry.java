package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.blocks.item.ItemBlockOre;
import com.mowmaster.rprocessing.enums.EnumBlock;
import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class BlockRegistry
{
    public static Block claybloomery;
    public static Block choppingblock;
    public static Block formedunfiredbloomery;




    public static Block claymorterblock;
    public static Block unfiredbloomery;
    public static Block leafpile;


    public static void init() {

        claybloomery = new BlockClayBloomery("claybloomery", "claybloomery");
        choppingblock = new BlockChoppingBlock("choppingblock","choppingblock");
        formedunfiredbloomery = new BlockFormedUnfiredBloomery("formedunfiredbloomery","formedunfiredbloomery");



        claymorterblock = new BlockClayMorter("claymorterblock");
        unfiredbloomery = new BlockUnfiredBloomery("unfiredbloomery");
        leafpile = new BlockLeafPile("leafpile");

    }

    public static void register()
    {

        registerBlock(claybloomery);
        registerBlock(choppingblock);
        registerBlock(formedunfiredbloomery);

        registerBlock(claymorterblock, new ItemBlockOre(claymorterblock));
        registerBlock(unfiredbloomery,new ItemBlockOre(unfiredbloomery));
        registerBlock(leafpile,new ItemBlockOre(leafpile));
    }

    public static void registerRenders()
    {
        registerRender(claybloomery);
        registerRender(choppingblock);
        registerRender(formedunfiredbloomery);

        for (int i = 0; i < EnumBlock.ClayMorterBlock.values().length; i++)
        {
            registerRender(claymorterblock,i,EnumBlock.ClayMorterBlock.values()[i].getName());
        }

        for (int i = 0; i < EnumBlock.UnfiredBloomeryBlock.values().length; i++)
        {
            registerRender(unfiredbloomery,i, EnumBlock.UnfiredBloomeryBlock.values()[i].getName());
        }

        for (int i = 0; i < EnumBlock.LeafBlock.values().length; i++)
        {
            registerRender(leafpile,i, EnumBlock.LeafBlock.values()[i].getName());
        }
    }

    public static void registerBlock(Block block)
    {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    public static void registerBlock(Block block, ItemBlock itemBlock)
    {

        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    //Regular Block regRender
    public static void registerRender(Block block)
    {
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5),"inventory")));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(References.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    //Special Package for Crystal Item Renders
    public static void registerRenderCrystal(Block block)
    {
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5),"inventory")));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(References.MODID,"crystals/" + block.getUnlocalizedName().substring(5)), "inventory"));
    }
    //Special Package for Crystal Item Renders
    public static void registerRenderLog(Block block)
    {
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5),"inventory")));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(References.MODID,"logs/" + block.getUnlocalizedName().substring(5)), "inventory"));
    }
    //Special Package for Ancient Item Renders
    public static void registerRenderAncient(Block block)
    {
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5),"inventory")));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(References.MODID,"ancient/" + block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void registerRender(Block block, int meta, String fileName)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(References.MODID, fileName), "inventory"));
    }

}
