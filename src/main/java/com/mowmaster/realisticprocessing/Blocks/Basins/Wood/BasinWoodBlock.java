package com.mowmaster.realisticprocessing.Blocks.Basins.Wood;

import com.mowmaster.realisticprocessing.Blocks.Basins.BaseBasinBlock;
import com.mowmaster.realisticprocessing.Registry.DeferredBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

public class BasinWoodBlock extends BaseBasinBlock {
    public BasinWoodBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        //return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);

        if(!p_60504_.isClientSide())
        {
            BlockEntity blockEntity = p_60504_.getBlockEntity(p_60505_);
            if(blockEntity instanceof BasinWoodBlockEntity basin)
            {
                ItemStack itemInHand = p_60506_.getMainHandItem();
                ItemStack itemInOffHand = p_60506_.getOffhandItem();

                if(!itemInHand.isEmpty())
                {
                    ItemStack itemToInsert = itemInHand;
                    System.out.println("Add Item???");
                    if(basin.addItemToBasin(itemToInsert,true).getCount() != itemToInsert.getCount())
                    {
                        ItemStack leftovers = basin.addItemToBasin(itemToInsert,false);
                        System.out.println("Add Item!!! " + leftovers);
                        itemInHand.shrink(itemToInsert.getCount()-leftovers.getCount());
                    }
                }
                else if(p_60506_.isCrouching() && itemInHand.isEmpty())
                {
                    System.out.println("Remove Item???");
                    if(!basin.removeItemFromBasin(true).isEmpty())
                    {
                        System.out.println("Remove Item!!!");
                        ItemHandlerHelper.giveItemToPlayer(p_60506_,basin.removeItemFromBasin(false));
                    }
                }
            }
        }

        return  InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return DeferredBlockEntityTypes.BASIN_WOOD.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((BasinWoodBlockEntity) blockEntity).tick();
    }
}
