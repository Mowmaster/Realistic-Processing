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


//Maybe try extending Block leaves sometime?
public class BlockLeafPile extends Block implements IMetaBlockName
{

    //the string text is what you use in your json file
    public static final PropertyEnum LEAFPILE = PropertyEnum.create("leafpile",EnumBlock.LeafBlock.class);


    public BlockLeafPile(String unloc)
    {
        super(Material.LEAVES);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(References.MODID, unloc));
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEAFPILE, EnumBlock.LeafBlock.LB1));
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(REAL_TAB);
        this.setTickRandomly(true);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this,new IProperty[]{LEAFPILE});
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        int blockmeta = placer.getHeldItem(EnumHand.MAIN_HAND).getMetadata();
        return getStateFromMeta(blockmeta);
    }



    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumBlock.LeafBlock claytypes = (EnumBlock.LeafBlock) state.getValue(LEAFPILE);
        return claytypes.getID();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LEAFPILE,EnumBlock.LeafBlock.values()[meta]);
    }


    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int i = 0; i < EnumBlock.LeafBlock.values().length; i++)
        {
            list.add(new ItemStack(itemIn,1,i));
        }
    }

    public String getSpecialName(ItemStack stack)
    {
        return EnumBlock.LeafBlock.values()[stack.getItemDamage()].getName();
    }


    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this),1,getMetaFromState(world.getBlockState(pos)));
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */

    public boolean isOpaqueCube(IBlockState state) {return false;}

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Pass true to draw this block using fancy graphics, or false for fast graphics.
     */

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        Item dropped = null;
        if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB1)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB2)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB3)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB4)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB5)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB6)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB7)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB8)))
        {
            dropped =  ItemRegistry.leafpile;
        }
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB9)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB10)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB11)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB12)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB13)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB14)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB15)) ||
                state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB16)))
        {
            dropped =  ItemRegistry.dryleafpile;
        }
        return dropped;

    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int drops = 1;
        if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB1))) {drops =  1;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB2))) {drops = 2;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB3))) {drops = 3;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB4))) {drops = 4;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB5))) {drops = 5;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB6))) {drops = 6;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB7))) {drops = 7;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB8))) {drops = 8;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB9))) {drops = 1;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB10))) {drops = 2;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB11))) {drops = 3;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB12))) {drops = 4;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB13))) {drops = 5;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB14))) {drops = 6;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB15))) {drops = 7;}
        else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB16))) {drops = 8;}
        return drops;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (((EnumBlock.LeafBlock)state.getValue(LEAFPILE)))
        {
            case LB1:
            case LB9:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
            case LB2:
            case LB10:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
            case LB3:
            case LB11:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D);
            case LB4:
            case LB12:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
            case LB5:
            case LB13:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);
            case LB6:
            case LB14:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
            case LB7:
            case LB15:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
            case LB8:
            case LB16:
            default:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
        ItemStack leaf = new ItemStack(ItemRegistry.leafpile);

            if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),leaf))
            {

                if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB1)))
                {
                    if(!playerIn.isCreative()) {playerIn.getHeldItem(hand).shrink(1);}
                    worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB2));
                }
                else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB2)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB3));
                }
                else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB3)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB4));
                }
                else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB4))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE, EnumBlock.LeafBlock.LB5));
                }
                else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB5)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB6));
                }
                else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB6)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB7));
                }
                else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB7)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB8));
                }
            }

        return true;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        int rn = random.nextInt(10);

        if(rn>6)
        {
            if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB1)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB9));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB2)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB10));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB3)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB11));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB4)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB12));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB5)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB13));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB6)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB14));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB7)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB15));
            }
            else if(state.equals(BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB8)))
            {
                worldIn.setBlockState(pos,BlockRegistry.leafpile.getDefaultState().withProperty(BlockLeafPile.LEAFPILE,EnumBlock.LeafBlock.LB16));
            }
        }
    }


}