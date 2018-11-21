package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.tiles.TileChoppingBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileChoppingBlock extends TileEntitySpecialRenderer<TileChoppingBlock>
{

    public void render(TileChoppingBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        ItemStack itemStack = te.getBlockOnCB();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        float value = 0.5f;

        if(te.getBlockOnCB().getCount()>0)
        {
            renderOreItem(itemRenderer,new ItemStack(te.getBlockOnCB().getItem(),1,te.getBlockOnCB().getMetadata()),value,0.81f,value);
        }

        GlStateManager.popMatrix();
    }

    public static void renderOreItem(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(1.25f,1.25f,1.25f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}