package com.mowmaster.rprocessing.handlers;

import com.mowmaster.rprocessing.blocks.BlockChoppingBlock;
import com.mowmaster.rprocessing.blocks.BlockClayMorter;
import com.mowmaster.rprocessing.blocks.BlockRegistry;
import com.mowmaster.rprocessing.blocks.BlockUnfiredBloomery;
import com.mowmaster.rprocessing.enums.EnumBlock;
import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraft.block.*;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.Sys;

import java.util.Iterator;
import java.util.Random;


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
    @SubscribeEvent
    public void onBlockHarvestedBy(PlayerInteractEvent.HarvestCheck harvestCheck)
    {
        EntityPlayer playerIn = harvestCheck.getEntityPlayer();
        IBlockState state = harvestCheck.getTargetBlock();
        Iterable holding = harvestCheck.getEntityPlayer().getHeldEquipment();

        if(state.getBlock() instanceof BlockLog)
        {
            System.out.println(playerIn);
            System.out.println(state);
            System.out.println(holding);
        }

    }
/*
    @SubscribeEvent
    public void onBlockHarvested(BlockEvent.BreakEvent breakEvent)
    {
        World worldIn = breakEvent.getWorld();
        BlockPos pos = breakEvent.getPos();
        IBlockState state = worldIn.getBlockState(breakEvent.getPos());


        //leaves 0-25
        //twigs 26-51
        //sticks 52-80
        //limbs 81-94
        //logs 95+
        if(state.getBlock() instanceof BlockLeaves)
        {

        }

    }
*/
    @SubscribeEvent
    public void onBlockHarvestedDrops(BlockEvent.HarvestDropsEvent getdrops) {
        World worldIn = getdrops.getWorld();
        BlockPos pos = getdrops.getPos();
        IBlockState state = getdrops.getState();
        EntityPlayer player = getdrops.getHarvester();
        Random rn = new Random();
        int drop = rn.nextInt(100);
            if(state.getBlock() instanceof BlockLeaves)
            {
                if(drop<=25)
                {
                    getdrops.setDropChance(0.5F);
                    getdrops.getDrops().add(new ItemStack(ItemRegistry.leafpile));
                }
                else if(drop>25 && drop<=51)
                {
                    getdrops.setDropChance(0.5F);
                    getdrops.getDrops().add(new ItemStack(ItemRegistry.twig));
                }
                else if(drop>51 && drop<=80)
                {
                    getdrops.setDropChance(0.25F);
                    getdrops.getDrops().add(new ItemStack(Items.STICK));
                }
                else if(drop>80 && drop<=94)
                {
                    getdrops.setDropChance(0.125F);
                    getdrops.getDrops().add(new ItemStack(ItemRegistry.branch));
                }
                else if(drop>95)
                {
                    getdrops.setDropChance(0.065F);
                    getdrops.getDrops().add(new ItemStack(ItemRegistry.limb));
                }
            }

    }


}
