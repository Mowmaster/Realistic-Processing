package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;



public class RenderTileClayBloomery extends TileEntitySpecialRenderer<TileClayBloomery>
{
    private static final EntityItem IRON = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Blocks.IRON_ORE));
    private static final EntityItem GOLD = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Blocks.GOLD_ORE));

    @Override
    public void renderTileEntityAt(TileClayBloomery te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(90f,1,0,0);
            GlStateManager.translate(0.3,0.5,-0.2);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0;j<te.carboncount;j++)
            {
                Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.COAL), ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.translate(0,0,-0.075);
            }
        }
        GlStateManager.popMatrix();
        {}


        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0,0,1.0);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0; j<te.orelist.size() && j < 4;j++)
            {
                //Minecraft.getMinecraft().getRenderManager().doRenderEntity(IRON,0,0,0,0f,0f,false);
                Minecraft.getMinecraft().getRenderItem().renderItem(te.orelist.get(j).copy(), ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.translate(0,0.2,0);
                GlStateManager.rotate(45f + j*10,0,1,0);

            }
        }
        GlStateManager.popMatrix();
        {}
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0.25,0,0.75);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=4;j<te.orelist.size();j++)
            {
                //Minecraft.getMinecraft().getRenderManager().doRenderEntity(IRON,0,0,0,0f,0f,false);
                Minecraft.getMinecraft().getRenderItem().renderItem(te.orelist.get(j).copy(), ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.translate(0,0.2,0);
                GlStateManager.rotate(30f + j*50,0,1,0);
            }
        }
        GlStateManager.popMatrix();
        {}


        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            //GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0.5,0,0.5);
            GlStateManager.translate(0,0,0);
            GlStateManager.scale(3.75,0.25,3.75);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0;j<te.processtimer;j++)
            {
                Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Blocks.MAGMA), ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.translate(0,0.0113,0);
            }
        }
        GlStateManager.popMatrix();

    }
}