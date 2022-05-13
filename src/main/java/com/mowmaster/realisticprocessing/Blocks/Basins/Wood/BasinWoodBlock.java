package com.mowmaster.realisticprocessing.Blocks.Basins.Wood;

import com.mowmaster.realisticprocessing.Blocks.Basins.BaseBasinBlock;
import com.mowmaster.realisticprocessing.Registry.DeferredBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BasinWoodBlock extends BaseBasinBlock {
    public BasinWoodBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return DeferredBlockEntityTypes.BASIN_WOOD.get().create(pos, state);
    }


}
