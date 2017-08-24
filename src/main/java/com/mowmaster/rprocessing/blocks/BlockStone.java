package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.blocks.item.IMetaBlockName;
import com.mowmaster.rprocessing.enums.EnumBlock;
import com.mowmaster.rprocessing.items.ItemRegistry;
import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;
import static com.mowmaster.rprocessing.enums.EnumBlock.StoneBlock.STONE1;


//Maybe try extending Block leaves sometime?
public class BlockStone extends Block implements IMetaBlockName
{

    //the string text is what you use in your json file
    public static final PropertyEnum STONETYPE = PropertyEnum.create("stonetype",EnumBlock.StoneBlock.class);


    public BlockStone(String unloc)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(References.MODID, unloc));
        this.setDefaultState(this.blockState.getBaseState().withProperty(STONETYPE, STONE1));
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(REAL_TAB);

    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this,new IProperty[]{STONETYPE});
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        int blockmeta = placer.getHeldItem(EnumHand.MAIN_HAND).getMetadata();
        return getStateFromMeta(blockmeta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumBlock.StoneBlock stonetypes = (EnumBlock.StoneBlock) state.getValue(STONETYPE);
        return stonetypes.getID();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STONETYPE,EnumBlock.StoneBlock.values()[meta]);
    }


    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int i = 0; i < EnumBlock.StoneBlock.values().length; i++)
        {
            list.add(new ItemStack(itemIn,1,i));
        }
    }

    public String getSpecialName(ItemStack stack)
    {
        return EnumBlock.StoneBlock.values()[stack.getItemDamage()].getName();
    }


    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this),1,getMetaFromState(world.getBlockState(pos)));
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockRegistry.stonepile);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int drops = 1;
        if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE1))) {drops =  1;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE2))) {drops = 2;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE3))) {drops = 3;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE4))) {drops = 4;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE5))) {drops = 5;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE6))) {drops = 6;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE7))) {drops = 7;}
        else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE8))) {drops = 8;}
        return drops;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack stone = new ItemStack(BlockRegistry.stonepile);
        ItemStack hammer = new ItemStack(ItemRegistry.hammer);

        if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),stone))
        {
            if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE5)))
            {
                if(!playerIn.isCreative()) {playerIn.getHeldItem(hand).shrink(1);}
                worldIn.setBlockState(pos,BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE6));
            }
            else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE6)))
            {
                if(!playerIn.isCreative()) {playerIn.getHeldItem(hand).shrink(1);}
                worldIn.setBlockState(pos,BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE7));
            }
            else if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE7)))
            {
                if(!playerIn.isCreative()) {playerIn.getHeldItem(hand).shrink(1);}
                worldIn.setBlockState(pos,BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE8));
            }
        }
        if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),hammer))
        {
            if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE8)))
            {
                if(!playerIn.isCreative()) {playerIn.getHeldItem(hand).shrink(1);}
                worldIn.setBlockState(pos,BlockRegistry.stonepileanvil.getDefaultState());
            }
        }

        return true;
    }

    /*
    Break each stone off individually

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if(state.equals(BlockRegistry.stonepile.getDefaultState().withProperty(BlockStone.STONETYPE,EnumBlock.StoneBlock.STONE8)))
        {

        }
    }
     */

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (((EnumBlock.StoneBlock)state.getValue(STONETYPE)))
        {
            case STONE1:
            default:
                return new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.1875D, 0.7D);
            case STONE2:
                return new AxisAlignedBB(0.25, 0.0D, 0.25D, 0.75D, 0.3125D, 0.75D);
            case STONE3:
                return new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.5625D, 0.8D);
            case STONE4:
                return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.5625D, 0.9D);
            case STONE5:
                return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.6875D, 0.9D);
            case STONE6:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
            case STONE7:
            case STONE8:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return true;
    }

}