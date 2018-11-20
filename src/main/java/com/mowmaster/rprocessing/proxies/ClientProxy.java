package com.mowmaster.rprocessing.proxies;


import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.items.ItemRegistry;
import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileChoppingBlock;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import com.mowmaster.rprocessing.tiles.render.RenderTileChoppingBlock;
import com.mowmaster.rprocessing.tiles.render.RenderTileClayBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
    @Override
    @SideOnly(Side.CLIENT)
    public void preInit()
    {
        BlockRegistry.registerRenders();
        ItemRegistry.registerRenders();

        ClientRegistry.bindTileEntitySpecialRenderer(TileClayBloomery.class,new RenderTileClayBloomery());

        ClientRegistry.bindTileEntitySpecialRenderer(TileChoppingBlock.class,new RenderTileChoppingBlock());

    }


    @Override
    public void registerModelBakeryVarients()
    {
        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlockRegistry.claymorterblock),
                //new ResourceLocation(References.MODID, "mix1"),
                new ResourceLocation(References.MODID, "mix2"),
                new ResourceLocation(References.MODID, "mix3"),
                new ResourceLocation(References.MODID, "mix4"),
                new ResourceLocation(References.MODID, "mix5"),
                new ResourceLocation(References.MODID, "mix6"),
                new ResourceLocation(References.MODID, "mix7"),
                new ResourceLocation(References.MODID, "mix8"),
                new ResourceLocation(References.MODID, "mix9"),
                new ResourceLocation(References.MODID, "mix10"),
                new ResourceLocation(References.MODID, "mix11"),
                new ResourceLocation(References.MODID, "mix12")
        );

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlockRegistry.unfiredbloomery),
                new ResourceLocation(References.MODID, "ubb1"),
                new ResourceLocation(References.MODID, "ubb2"),
                new ResourceLocation(References.MODID, "ubb3"),
                new ResourceLocation(References.MODID, "ubb4"),
                new ResourceLocation(References.MODID, "ubb5"),
                new ResourceLocation(References.MODID, "ubb6"),
                new ResourceLocation(References.MODID, "ubb7"),
                new ResourceLocation(References.MODID, "ubb8"),
                new ResourceLocation(References.MODID, "ubb9"),
                new ResourceLocation(References.MODID, "ubb10"),
                new ResourceLocation(References.MODID, "ubb11"),
                new ResourceLocation(References.MODID, "ubb12"),
                new ResourceLocation(References.MODID, "ubb13"),
                new ResourceLocation(References.MODID, "ubb14"),
                new ResourceLocation(References.MODID, "ubb15"),
                new ResourceLocation(References.MODID, "ubb16")
        );

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlockRegistry.unfiredbloomery),
                new ResourceLocation(References.MODID, "lb1"),
                new ResourceLocation(References.MODID, "lb2"),
                new ResourceLocation(References.MODID, "lb3"),
                new ResourceLocation(References.MODID, "lb4"),
                new ResourceLocation(References.MODID, "lb5"),
                new ResourceLocation(References.MODID, "lb6"),
                new ResourceLocation(References.MODID, "lb7"),
                new ResourceLocation(References.MODID, "lb8"),
                new ResourceLocation(References.MODID, "lb9"),
                new ResourceLocation(References.MODID, "lb10"),
                new ResourceLocation(References.MODID, "lb11"),
                new ResourceLocation(References.MODID, "lb12"),
                new ResourceLocation(References.MODID, "lb13"),
                new ResourceLocation(References.MODID, "lb14"),
                new ResourceLocation(References.MODID, "lb15"),
                new ResourceLocation(References.MODID, "lb16")
        );

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlockRegistry.stonepile),
                new ResourceLocation(References.MODID, "stone1"),
                new ResourceLocation(References.MODID, "stone2"),
                new ResourceLocation(References.MODID, "stone3"),
                new ResourceLocation(References.MODID, "stone4"),
                new ResourceLocation(References.MODID, "stone5"),
                new ResourceLocation(References.MODID, "stone6"),
                new ResourceLocation(References.MODID, "stone7"),
                new ResourceLocation(References.MODID, "stone8")
        );
    }

}
