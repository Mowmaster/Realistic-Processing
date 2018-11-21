package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.blocks.BlockBasic;
import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileChoppingBlock;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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

        //renderChoppingProgress(te);

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


    public void renderChoppingProgress(TileChoppingBlock te)
    {
        Tessellator tessellator = Tessellator.getInstance();

        double progress = te.getChopProgress();



        if (te == null) {
            return;
        }


        if (progress==0.0) {
            return;
        }


        if (progress > 0.0) {
            ResourceLocation block = new ResourceLocation(References.MODID,"textures/blocks/bloomery/progress");
            bindTexture(block);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            BufferBuilder renderer = tessellator.getBuffer();

            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(block.toString());

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            float zero = 0.125f;
            float one = 0.875f;
            float yvalue = 1.0f;
            renderer.pos(zero, yvalue, zero).tex(u1, v1).endVertex();
            renderer.pos(zero, yvalue, one).tex(u1, v2).endVertex();
            renderer.pos(one, yvalue, one).tex(u2, v2).endVertex();
            renderer.pos(one, yvalue, zero).tex(u2, v1).endVertex();




            tessellator.draw();

            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }

    }

}