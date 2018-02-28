package com.mowmaster.rprocessing.tiles.render;

import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileClayBloomery extends TileEntitySpecialRenderer<TileClayBloomery>
{
    public void render(TileClayBloomery te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Item item = te.getOreItemInBlock().getItem();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);



        GlStateManager.popMatrix();
    }
}