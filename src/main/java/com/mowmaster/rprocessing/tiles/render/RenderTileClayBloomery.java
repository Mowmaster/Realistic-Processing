package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;


public class RenderTileClayBloomery extends TileEntitySpecialRenderer<TileClayBloomery>
{
    private static final EntityItem IRON = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Blocks.IRON_ORE));
    private static final EntityItem GOLD = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Blocks.GOLD_ORE));
    private static final EntityItem COAL = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Items.COAL));
    private static final EntityItem LAVA = new EntityItem(Minecraft.getMinecraft().world,0,0,0,new ItemStack(Blocks.MAGMA));

    @Override
    public void renderTileEntityAt(TileClayBloomery te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(90f,1,0,0);
            GlStateManager.translate(0.3,-0.1,-0.2);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0;j<te.carboncount;j++)
            {
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(COAL,0,0,0,0f,0f,false);
                GlStateManager.translate(0,0,-0.1);
            }
        }
        GlStateManager.popMatrix();



        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0,0,0.90);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0;j<te.oreiron && j<4;j++)
            {
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(IRON,0,0,0,0f,0f,false);
                GlStateManager.translate(0,0.1,0);
            }
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0.25,0,0.65);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=4;j<te.oreiron && j>=4;j++)
            {
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(IRON,0,0,0,0f,0f,false);
                GlStateManager.translate(0,0.1,0);
            }
        }
        GlStateManager.popMatrix();



        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0,0,0.90);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0;j<te.oregold && j<4;j++)
            {
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(GOLD,0,0,0,0f,0f,false);
                GlStateManager.translate(0,0.1,0);
            }
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(45f,0,1,0);
            GlStateManager.translate(0.25,0,0.65);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=4;j<te.oregold && j>=4;j++)
            {
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(GOLD,0,0,0,0f,0f,false);
                GlStateManager.translate(0,0.1,0);
            }
        }
        GlStateManager.popMatrix();



        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x,y,z);
            GlStateManager.translate(0,0,0);
            GlStateManager.rotate(22.5f,0,1,0);
            GlStateManager.translate(0,0,0);
            GlStateManager.translate(0,0,0);
            GlStateManager.scale(3.75,0.75,3.75);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            for(int j=0;j<te.processtimer;j++)
            {
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(LAVA,0,0,0,0f,0f,false);
                GlStateManager.translate(0,0.00050,0);
            }
        }
        GlStateManager.popMatrix();

    }
}
