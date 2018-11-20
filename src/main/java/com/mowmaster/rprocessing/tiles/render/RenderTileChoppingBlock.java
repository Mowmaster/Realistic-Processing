package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileChoppingBlock;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
        ItemStack itemstack = te.getBlockOnCB();
        BlockPos pos = te.getPos();
        Tessellator tessellator = Tessellator.getInstance();
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        float value = 0.5f;


        if(te.hasBlockOnCB())
        {
            renderBlockOnCB(itemRenderer,new ItemStack(itemstack.getItem(),1,itemstack.getMetadata()),value,0.75f,value);
        }

        GlStateManager.popMatrix();
    }


    public static void renderBlockOnCB(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.75f,0.75f,0.75f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}