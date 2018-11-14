package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.blocks.item.IMetaBlockName;
import com.mowmaster.rprocessing.enums.EnumBlock;
import com.mowmaster.rprocessing.items.ItemRegistry;
import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;
import static com.mowmaster.rprocessing.enums.EnumBlock.ClayMorterBlock.MIX2;


//Maybe try extending Block leaves sometime?
public class BlockClayMorter extends Block implements IMetaBlockName
{

    //the string text is what you use in your json file
    public static final PropertyEnum CLAYTYPE = PropertyEnum.create("claytype",EnumBlock.ClayMorterBlock.class);


    public BlockClayMorter(String unloc)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(References.MODID, unloc));
        this.setDefaultState(this.blockState.getBaseState().withProperty(CLAYTYPE, MIX2));
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setCreativeTab(REAL_TAB);
        this.setSoundType(SoundType.WOOD);

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add("Mix: Sand, Clay, Bonemeal, Dried Leaves, and Water to make Bloomery Clay Blocks");
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this,new IProperty[]{CLAYTYPE});
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        int blockmeta = placer.getHeldItem(EnumHand.MAIN_HAND).getMetadata();
        return getStateFromMeta(blockmeta);
    }



    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumBlock.ClayMorterBlock claytypes = (EnumBlock.ClayMorterBlock) state.getValue(CLAYTYPE);
        return claytypes.getID();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(CLAYTYPE,EnumBlock.ClayMorterBlock.values()[meta]);
    }


    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int i = 0; i < EnumBlock.ClayMorterBlock.values().length; i++)
        {
            list.add(new ItemStack(itemIn,1,i));
        }
    }

    public String getSpecialName(ItemStack stack)
    {
        return EnumBlock.ClayMorterBlock.values()[stack.getItemDamage()].getName();
    }


    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this),1,getMetaFromState(world.getBlockState(pos)));
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Pass true to draw this block using fancy graphics, or false for fast graphics.
     */

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public int damageDropped(IBlockState state) {
        switch (((EnumBlock.ClayMorterBlock)state.getValue(CLAYTYPE)))
        {
            case MIX2:
            case MIX3:
            case MIX4:
            case MIX5:
            case MIX6:
            case MIX7:
            case MIX8:
            case MIX9:
            case MIX10:
            case MIX11:
                default:
                    return 0;
            case MIX12:
                return 10;

        }
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack bonemeal = new ItemStack(Items.DYE,1,15);

        if(!worldIn.isRemote)
        {
            if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.SAND))) {
                if (state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX2))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX3));
                }
            }
            else if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Items.CLAY_BALL)))
            {
                if(state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX3)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX4));
                }
                else if(state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX4)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX5));
                }
                else if(state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX5)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX6));
                }
            }
            else if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),bonemeal))
            {
                if(state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX6)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX7));
                }
                else if(state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX7)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX8));
                }
                else if(state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX8)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE,EnumBlock.ClayMorterBlock.MIX9));
                }
            }
            else if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(ItemRegistry.dryleafpile)) || ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Items.WHEAT)) || ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.VINE)) || ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.REEDS)))
            {
                if (state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX9))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX10));
                }
            }
            else if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Items.WATER_BUCKET)))
            {
                if (state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX10))) {
                    if (!playerIn.isCreative()) {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    worldIn.setBlockState(pos, BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX11));
                }
            }
            else if (state.equals(BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX11))) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.4, new ItemStack(BlockRegistry.claymorterblock, 1, 10)));
                worldIn.setBlockState(pos, BlockRegistry.claymorterblock.getDefaultState().withProperty(BlockClayMorter.CLAYTYPE, EnumBlock.ClayMorterBlock.MIX2));
            }
        }


        return true;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (((EnumBlock.ClayMorterBlock)state.getValue(CLAYTYPE)))
        {
            case MIX2:
            default:
                return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.5D, 0.9D);
            case MIX3:
            case MIX4:
            case MIX5:
            case MIX6:
            case MIX7:
            case MIX8:
            case MIX9:
            case MIX10:
            case MIX11:
                return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);
            case MIX12:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        }
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return true;
    }

}