package com.mowmaster.realisticprocessing.Blocks.Basins.Wood;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mowmaster.realisticprocessing.Blocks.Basins.BaseBasinBlockRender;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BasinWoodBlockRenderer extends BaseBasinBlockRender implements BlockEntityRenderer<BasinWoodBlockEntity> {
    @Override
    public void render(BasinWoodBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {
        if (!p_112307_.isRemoved())
        {
            Level level = p_112307_.getLevel();
            List<ItemStack> stacksList = p_112307_.getStoredItems();

            if(stacksList.size()>0)
            {
                int angle = 360 / stacksList.size();
                for(int i=0;i<stacksList.size();i++)
                {
                    renderItemStacked(level,p_112309_,p_112310_,stacksList.get(i),p_112311_,p_112312_,0.25f,0.5f,0.25f,0.5f,0.5f,0.5f,angle);
                }
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(BasinWoodBlockEntity p_112306_) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(p_112306_);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(BasinWoodBlockEntity p_173568_, Vec3 p_173569_) {
        return BlockEntityRenderer.super.shouldRender(p_173568_, p_173569_);
    }
}
