package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTileClayBloomery extends TileEntitySpecialRenderer<TileClayBloomery>
{
    public void render(TileClayBloomery te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Item item = te.getOreItemInBlock().getItem();
        BlockPos pos = te.getPos();
        Tessellator tessellator = Tessellator.getInstance();
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        ItemStack itemStack = te.getOreItemInBlock();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        float value = 0.35f;
        float orevalue1 = 0.65f;
        if(te.getCharcoalCount()>=1600)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.20f,value);}
        if(te.getCharcoalCount()>=3200)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.25f,value);}
        if(te.getCharcoalCount()>=4800)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.30f,value);}
        if(te.getCharcoalCount()>=6400)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.35f,value);}
        if(te.getCharcoalCount()>=8000)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.40f,value);}
        if(te.getCharcoalCount()>=9600)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.45f,value);}
        if(te.getCharcoalCount()>=11200)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.50f,value);}
        if(te.getCharcoalCount()>=12800)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.55f,value);}
        if(te.getCharcoalCount()>=14400)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.60f,value);}
        if(te.getCharcoalCount()>=16000)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.65f,value);}
        if(te.getCharcoalCount()>=17600)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.70f,value);}
        if(te.getCharcoalCount()>=19200)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.75f,value);}
        if(te.getCharcoalCount()>=208000)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.80f,value);}
        if(te.getCharcoalCount()>=22400)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.8f,value);}
        if(te.getCharcoalCount()>=24000)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.85f,value);}
        if(te.getCharcoalCount()>=25600)
        {renderItem(itemRenderer,new ItemStack(Items.COAL,1,1),value,0.90f,value);}

        if(te.getOreCount()>=1)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.15f,orevalue1);}
        if(te.getOreCount()>=2)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.25f,orevalue1);}
        if(te.getOreCount()>=3)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.35f,orevalue1);}
        if(te.getOreCount()>=4)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.45f,orevalue1);}
        if(te.getOreCount()>=5)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.55f,orevalue1);}
        if(te.getOreCount()>=6)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.65f,orevalue1);}
        if(te.getOreCount()>=7)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.75f,orevalue1);}
        if(te.getOreCount()>=8)
        {renderOreItem(itemRenderer,new ItemStack(te.getOreItemInBlock().getItem(),1,te.getOreItemInBlock().getMetadata()),orevalue1,0.85f,orevalue1);}


        renderSmeltingProgress(tessellator,te,pos);
        GlStateManager.popMatrix();
    }

    private void renderSmeltingProgress(Tessellator tessellator, TileClayBloomery bloom, BlockPos pos) {
        if (bloom == null) {
            return;
        }

        int renderProgress = bloom.getProgress();
        if (renderProgress==0) {
            return;
        }


        float scale = (bloom.getProgress() * 0.0025f);

        if (scale > 0) {
            ResourceLocation block = new ResourceLocation(References.MODID,"blocks/bloomery/progress");
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

            // Top
            float colorg = 1f;
            float colorr = 1f;
            float colorb = 1f;
            float alpha = 1f;

            //float zero = 0.1875f;
            //float one = 0.8125f;
            float zero = 0.125f;
            float one = 0.875f;
            float yvalue = 0.175f;
            renderer.pos(zero, scale+yvalue, zero).tex(u1, v1).color(255,0,0,250).endVertex();
            renderer.pos(zero, scale+yvalue, one).tex(u1, v2).color(255,0,0,250).endVertex();
            renderer.pos(one, scale+yvalue, one).tex(u2, v2).color(255,0,0,250).endVertex();
            renderer.pos(one, scale+yvalue, zero).tex(u2, v1).color(255,0,0,250).endVertex();



            tessellator.draw();

            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }
    }


    public static void renderItem(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(90f,1,0,0);
        GlStateManager.scale(0.5f,0.5f,0.5f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void renderOreItem(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.5f,0.2f,0.5f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}


/*
         if(te.itemStackInput.getCount()<16 && te.itemStackInput.getCount()>0)
        {renderItemInput(itemRenderer,itemStackInput,0.28125f,1f,0.28125f,0f,0f,0f,0f);}
        else if(te.itemStackInput.getCount()<32 && te.itemStackInput.getCount()>=16)
        {renderItemInput(itemRenderer,itemStackInput,0.2f,1f,0.28125f,0f,0f,0f,0f);renderItemInput(itemRenderer,itemStackInput,0.3625f,1f,0.28125f,0f,0f,0f,0f);}
        else if(te.itemStackInput.getCount()<48 && te.itemStackInput.getCount()>=32)
        {renderItemInput(itemRenderer,itemStackInput,0.2f,1f,0.25f,0f,0f,0f,0f);renderItemInput(itemRenderer,itemStackInput,0.3625f,1f,0.25f,0f,0f,0f,0f);renderItemInput(itemRenderer,itemStackInput,0.28125f,1f,0.375f,0f,0f,0f,0f);}
        else if(te.itemStackInput.getCount()>=48)
        {renderItemInput(itemRenderer,itemStackInput,0.215f,1f,0.25f,0f,0f,0f,0f);renderItemInput(itemRenderer,itemStackInput,0.345f,1f,0.25f,0f,0f,0f,0f);renderItemInput(itemRenderer,itemStackInput,0.28125f,1f,0.375f,0f,0f,0f,0f);renderItemInput(itemRenderer,itemStackInput,0.28125f,1.125f,0.28125f,0f,0f,0f,0f);}

 */