package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.items.ItemRegistry;
import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
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


                if(playerIn.isSneaking())
                {

                    if (!playerIn.getHeldItem(hand).isEmpty())
                    {
                        playerIn.inventory.addItemStackToInventory(bloom.removeOre(playerIn));
                        return true;
                    }
                    else if (!playerIn.inventory.addItemStackToInventory(bloom.removeOre(playerIn)))
                    {
                        playerIn.dropItem(bloom.removeOre(playerIn), false);
                        return true;
                    }
                }
                else
                {
                    if(playerIn.getHeldItem(hand).getItem() instanceof ItemSpade)
                    {
                        if (!playerIn.getHeldItem(hand).isEmpty())
                        {
                            playerIn.inventory.addItemStackToInventory(bloom.removeCharcoal());
                            return true;
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(bloom.removeCharcoal()))
                        {
                            playerIn.dropItem(bloom.removeOre(playerIn), false);
                            return true;
                        }
                    }
                    else
                    {

                        if(bloom.addOre(playerIn.getHeldItem(hand)))
                        {
                            return true;
                        }

                        if(bloom.addCharcoal(playerIn.getHeldItem(hand)))
                        {
                            //playerIn.sendStatusMessage(new TextComponentString(TextFormatting.WHITE +"You are adding Carbon"),true);
                            return true;
                        }

                        if (playerIn.getHeldItem(hand).getItem().equals(Items.REEDS))
                        {
                            bloom.addAir();
                            return true;
                        }

                        if (playerIn.getHeldItem(hand).getItem().equals(Items.FLINT_AND_STEEL))
                        {
                            if(bloom.startProgress())
                            {
                                worldIn.playSound(playerIn,pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 0.8F);
                            }
                            return true;
                        }

                        if (playerIn.getHeldItem(hand).getItem().equals(ItemRegistry.debugItem))
                        {
                            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.WHITE +"Bloomery Temp: " + bloom.getHeat()),true);
                            System.out.println("Ore In Bloomery: " + bloom.getItemInBlock());
                            System.out.println("Ore Count In Bloomery: " + bloom.getOreCount());
                            System.out.println("Air In Bloomery: " + bloom.getAirCount());
                            System.out.println("Fuel In Bloomery: " + bloom.getCharcoalCount());
                            //System.out.println("Bloomery Temp: " + bloom.getHeat());
                            System.out.println("Bloomery On: " + bloom.getRunning());
                            System.out.println("Bloomery Ticks without fuel/air: " + bloom.getCold());
                            System.out.println("Smelting Progress: " + bloom.getProgress());
                            System.out.println("Ore Name: " + bloom.getOreName());
                            return true;
                        }
                        return false;
                    }

                }

            }
        }
        return true;
    }

    private int getRedstoneLevel(World worldIn, BlockPos pos)
    {
        int heatToRedstone=0;
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileClayBloomery) {
            TileClayBloomery bloom = (TileClayBloomery) tileEntity;
             heatToRedstone =  Math.round(bloom.getHeat()/125);
        }
        return heatToRedstone;
    }




    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }


    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return getRedstoneLevel(worldIn,pos);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        //super.onBlockHarvested(worldIn, pos, state, player);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileClayBloomery)
        {
            if(!worldIn.isRemote)
            {
                TileClayBloomery bloom = (TileClayBloomery) tileEntity;
                if(bloom.getProgress()<=0)
                {
                    bloom.getOutputAmount();
                    //worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(BlockRegistry.claybloomery,1)));
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(bloom.getOreItemInBlock().getItem(),bloom.getOreCount(),bloom.getOreItemInBlock().getMetadata())));
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.COAL,bloom.getCharcoalReturned(),1)));
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(ItemRegistry.charcoalChunk,bloom.getCharcoalBitsReturned())));
                    bloom.resetTile();
                }
                else if (bloom.getProgress()>0)
                {
                    bloom.getOutputAmount();
                    if(bloom.getOreReturned()>0)
                    {
                        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(bloom.getOreItemInBlock().getItem(),bloom.getOreReturned(),bloom.getOreItemInBlock().getMetadata())));
                    }
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(bloom.getSmeltingOutput(bloom.getOreItemInBlock()).getItem(),bloom.getSmeltedOut(),bloom.getSmeltingOutput(bloom.getOreItemInBlock()).getMetadata())));
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.COAL,bloom.getCharcoalReturned(),1)));
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(ItemRegistry.charcoalChunk,bloom.getCharcoalBitsReturned())));
                    bloom.resetTile();
                }
            }

        }


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