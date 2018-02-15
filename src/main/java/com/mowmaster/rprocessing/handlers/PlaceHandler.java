package com.mowmaster.rprocessing.handlers;

import com.mowmaster.rprocessing.blocks.*;
import com.mowmaster.rprocessing.enums.EnumBlock;
import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;
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
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
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

            if((ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(ItemRegistry.leafpile))))
            {
                if (!(state.getBlock()instanceof BlockLeafPile))
                {
                    worldIn.setBlockState(pos.add(0,1,0), BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE, EnumBlock.LeafBlock.LB1));
                    playerIn.getHeldItem(hand).shrink(1);
                }


            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRightClickHand(PlayerInteractEvent.RightClickItem event)
    {
        World worldIn = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer playerIn = event.getEntityPlayer();
        EnumHand hand = event.getHand();
        IBlockState state = worldIn.getBlockState(event.getPos());

        Random rn = new Random();
        int chance = rn.nextInt(10);
        if((ItemStack.areItemsEqual(playerIn.getHeldItemMainhand(), new ItemStack(Items.STICK))) && (ItemStack.areItemsEqual(playerIn.getHeldItemOffhand(), new ItemStack(Items.STICK))))
        {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if(chance>5) {
                    //drops embers entitys which can start fires
                    worldIn.setBlockState(pos,Blocks.FIRE.getDefaultState());
                    playerIn.getHeldItemOffhand().shrink(1);
                }
        }

    }

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
                else if(drop>80)
                {
                    getdrops.setDropChance(0.125F);
                    getdrops.getDrops().add(new ItemStack(ItemRegistry.branch));
                }

            }

    }





}
