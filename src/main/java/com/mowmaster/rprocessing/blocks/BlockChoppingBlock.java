package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileChoppingBlock;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentSelector;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;


public class BlockChoppingBlock extends Block implements ITileEntityProvider
{
    public BlockChoppingBlock(String unlocalizedName, String registryName)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MODID, registryName));
        this.setCreativeTab(REAL_TAB);
        this.setHardness(3.0f);
        this.setResistance(5);
    }


    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        //tooltip.add("Smelts Iron or Gold, fuel with Coal");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        if(!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileChoppingBlock) {
                TileChoppingBlock tileChoppingBlock = (TileChoppingBlock) tileEntity;

                if (playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
                {
                    if(tileChoppingBlock.hasBlockOnCB())
                    {
                        ItemStack itemToSummon = tileChoppingBlock.removeBlockFromCB().copy();
                        EntityItem itemEntity = new EntityItem(worldIn,pos.getX() + 0.5,pos.getY()+ 0.5,pos.getZ() + 0.5,itemToSummon);
                        itemEntity.motionX = 0;
                        itemEntity.motionY = 0;
                        itemEntity.motionZ = 0;
                        worldIn.spawnEntity(itemEntity);
                        return true;
                    }
                    else return false;
                }
                else
                {
                    if(tileChoppingBlock.canBeAdded(playerIn.getHeldItem(EnumHand.MAIN_HAND)))
                    {
                        tileChoppingBlock.addItemToCB(playerIn.getHeldItem(EnumHand.MAIN_HAND));
                        playerIn.getHeldItem(EnumHand.MAIN_HAND).shrink(1);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {

            if(!worldIn.isRemote) {
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if (tileEntity instanceof TileChoppingBlock) {
                    TileChoppingBlock tileChoppingBlock = (TileChoppingBlock) tileEntity;

                    if(tileChoppingBlock.hasBlockOnCB())
                    {
                        if(playerIn.getHeldItemMainhand().isEmpty())
                        {
                            tileChoppingBlock.addChopProgress(1.0);
                            playerIn.addExhaustion(0.1f);
                        }
                        if(playerIn.getHeldItemMainhand().getItem() instanceof ItemAxe)
                        {
                            if(playerIn.getHeldItemMainhand().getItem().equals(Items.WOODEN_AXE))
                            {
                                tileChoppingBlock.addChopProgress(2.0);
                                playerIn.addExhaustion(0.1f);
                                playerIn.getHeldItemMainhand().damageItem(1,playerIn);
                            }
                            else if(playerIn.getHeldItemMainhand().getItem().equals(Items.STONE_AXE))
                            {
                                tileChoppingBlock.addChopProgress(3.0);
                                playerIn.addExhaustion(0.1f);
                                playerIn.getHeldItemMainhand().damageItem(1,playerIn);
                            }
                            else if(playerIn.getHeldItemMainhand().getItem().equals(Items.IRON_AXE))
                            {
                                tileChoppingBlock.addChopProgress(4.0);
                                playerIn.addExhaustion(0.1f);
                                playerIn.getHeldItemMainhand().damageItem(1,playerIn);
                            }
                            else if(playerIn.getHeldItemMainhand().getItem().equals(Items.GOLDEN_AXE))
                            {
                                tileChoppingBlock.addChopProgress(4.0);
                                playerIn.addExhaustion(0.1f);
                                playerIn.getHeldItemMainhand().damageItem(1,playerIn);
                            }
                            else if(playerIn.getHeldItemMainhand().getItem().equals(Items.DIAMOND_AXE))
                            {
                                tileChoppingBlock.addChopProgress(6.0);
                                playerIn.addExhaustion(0.1f);
                                playerIn.getHeldItemMainhand().damageItem(1,playerIn);
                            }
                        }
                    }
                }
            }


    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.5D, 0.9D);
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
        return new TileChoppingBlock();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileChoppingBlock();
    }
}