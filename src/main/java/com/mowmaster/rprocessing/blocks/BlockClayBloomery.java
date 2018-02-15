package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentSelector;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;


public class BlockClayBloomery extends Block implements ITileEntityProvider
{
    public BlockClayBloomery(String unlocalizedName, String registryName)
    {
        super(Material.CLAY);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MODID, registryName));
        this.setCreativeTab(REAL_TAB);
        this.setHardness(0.5f);
        this.setResistance(5);



    }


    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Smelts Iron or Gold, fuel with Coal");
        tooltip.add("Ignite with Flint and Steel");
        tooltip.add("Blow air into it with a sugarcane after ignition");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileClayBloomery) {
                TileClayBloomery bloom = (TileClayBloomery) tileEntity;

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.COAL))) {
                        if(bloom.addCarbon())
                        {
                            //playerIn.sendMessage(new TextComponentString("You are adding Carbon"));
                            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.WHITE +"You are adding Carbon"),true);
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                    }
                }
                if(playerIn.isSneaking())
                {
                    if(bloom.removeCarbon())
                    {
                        //playerIn.sendMessage(new TextComponentString("You are removing Carbon"));
                        playerIn.sendStatusMessage(new TextComponentString(TextFormatting.WHITE +"You are removing Carbon"),true);
                    }
                }

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Blocks.IRON_ORE))) {
                        if(bloom.addIron(playerIn.getHeldItem(hand)))
                        {
                            //playerIn.sendMessage(new TextComponentString("You are adding Iron Ore"));
                            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GRAY +"You are adding Iron Ore"),true);
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                    }
                }
                if(playerIn.isSneaking())
                {
                    if(bloom.removeIron())
                    {
                        //playerIn.sendMessage(new TextComponentString("You are removing Iron Ore"));
                        playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GRAY +"You are removing Iron Ore"),true);
                    }
                }

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.FLINT_AND_STEEL)))
                    {
                        //ItemStack itemstack = playerIn.getHeldItem(hand);
                        if(bloom.activate())
                        {
                            //playerIn.sendMessage(new TextComponentString("You have Lit the Bloomery"));
                            //itemstack.damageItem(1, playerIn);
                            playerIn.sendStatusMessage(new TextComponentSelector(TextFormatting.YELLOW + "You have Lit the Bloomery"),true);
                            worldIn.playSound(playerIn,pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 0.8F);
                        }
                    }

                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.WATER_BUCKET)))
                    {
                        if(bloom.deactivate())
                        {
                            //playerIn.sendMessage(new TextComponentString("You have put out the Bloomery"));
                            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.BLUE + "You have put out the Bloomery"),true);
                        }
                    }
                }

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.REEDS)))
                    {
                        if(bloom.addOxygen())
                        {
                            bloom.resetNeedsOxygen();
                            //playerIn.sendMessage(new TextComponentString("You have added Oxygen to the Bloomery"));
                            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.AQUA + "You have added Oxygen to the Bloomery"),true);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClayBloomery();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileClayBloomery();
    }
}