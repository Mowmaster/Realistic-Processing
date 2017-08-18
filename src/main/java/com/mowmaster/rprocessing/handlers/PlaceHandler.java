package com.mowmaster.rprocessing.handlers;

import com.mowmaster.rprocessing.blocks.BlockChoppingBlock;
import com.mowmaster.rprocessing.blocks.BlockClayMorter;
import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.blocks.BlockUnfiredBloomery;
import com.mowmaster.rprocessing.enums.EnumBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;


public class PlaceHandler
{

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onItemRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        World worldIn = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer playerIn = event.getEntityPlayer();
        EnumHand hand = event.getHand();
        IBlockState state = worldIn.getBlockState(event.getPos());
        if((playerIn.getHeldItem(hand) != null))
        {
            if (playerIn.getHeldItem(hand).getItem() instanceof ItemAxe) {
                if (state.getBlock() instanceof BlockPlanks) {
                    if(!worldIn.isRemote)
                    {
                        worldIn.setBlockState(pos.add(0, 0, 0), BlockRegistry.choppingblock.getDefaultState());
                        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.4, new ItemStack(Items.STICK, 2)));
                        playerIn.getHeldItem(hand).damageItem(1, playerIn);
                    }
                }
            }

            if (playerIn.getHeldItem(hand).getItem() instanceof ItemAxe) {
                if (state.getBlock() instanceof BlockChoppingBlock) {
                    if(!worldIn.isRemote)
                    {
                        worldIn.setBlockState(pos.add(0, 0, 0), BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX2));
                        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.4, new ItemStack(Items.STICK, 1)));
                        playerIn.getHeldItem(hand).damageItem(1, playerIn);
                    }
                }
            }

            if((ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.BRICK))))
            {
                if (!(state.getBlock()instanceof BlockUnfiredBloomery))
                {
                    worldIn.setBlockState(pos.add(0,1,0), BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB1));
                    playerIn.getHeldItem(hand).shrink(1);
                }


            }
        }
    }


}
