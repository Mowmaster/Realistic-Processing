package com.mowmaster.realisticprocessing.Blocks.WoodBasin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseBasinBlock extends Block {

    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    protected static final VoxelShape SHAPE = Shapes.join(
            Shapes.block(), Shapes.or(
            box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D),
            box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D),
            box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
            box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            INSIDE), BooleanOp.ONLY_FIRST);

    public BaseBasinBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return this.SHAPE;
    }
}
