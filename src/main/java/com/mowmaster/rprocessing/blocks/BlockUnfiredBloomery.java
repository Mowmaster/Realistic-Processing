package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.blocks.item.IMetaBlockName;
import com.mowmaster.rprocessing.enums.EnumBlock;
import com.mowmaster.rprocessing.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
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
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;


//Maybe try extending Block leaves sometime?
public class BlockUnfiredBloomery extends Block implements IMetaBlockName
{

    //the string text is what you use in your json file
    public static final PropertyEnum STAGE = PropertyEnum.create("stage",EnumBlock.UnfiredBloomeryBlock.class);


    public BlockUnfiredBloomery(String unloc)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(References.MODID, unloc));
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, EnumBlock.UnfiredBloomeryBlock.UBB1));
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.SAND);
        this.setBlockUnbreakable();//maybe it has to be dug up with materials lost?
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this,new IProperty[]{STAGE});
    }


    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        int blockmeta = placer.getHeldItem(EnumHand.MAIN_HAND).getMetadata();
        return getStateFromMeta(blockmeta);
    }



    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumBlock.UnfiredBloomeryBlock claytypes = (EnumBlock.UnfiredBloomeryBlock) state.getValue(STAGE);
        return claytypes.getID();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STAGE,EnumBlock.UnfiredBloomeryBlock.values()[meta]);
    }


    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int i = 0; i < EnumBlock.UnfiredBloomeryBlock.values().length; i++)
        {
            list.add(new ItemStack(itemIn,1,i));
        }
    }

    public String getSpecialName(ItemStack stack)
    {
        return EnumBlock.UnfiredBloomeryBlock.values()[stack.getItemDamage()].getName();
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
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    /*
    If player uses wrong structure material it deconstructs it by one (giving the item back as well)
     */

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (((EnumBlock.UnfiredBloomeryBlock)state.getValue(STAGE)))
        {
            case UBB1:
            case UBB2:
            case UBB3:
            case UBB4:
            case UBB5:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.2D, 1.0D);
            case UBB6:
            case UBB7:
            case UBB8:
            case UBB9:
            case UBB10:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4D, 1.0D);
            case UBB11:
            case UBB12:
            case UBB13:
            case UBB14:
            case UBB15:
            case UBB16:
            default:
                return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
        ItemStack brick = new ItemStack(Items.BRICK);
        ItemStack morter = new ItemStack(BlockRegistry.claymorterblock,1,10);

            if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),brick))
            {
                if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB1)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB2));
                }
                else if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB2)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB3));
                }
                else if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB3)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB4));
                }
                else if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB5))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB6));
                }
                else if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB6)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB7));
                }
                else if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB7)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB8));
                }
                else if(state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB8)))
                {
                    if(!playerIn.isCreative())
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos,BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE,EnumBlock.UnfiredBloomeryBlock.UBB9));
                }

            }



            else if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),morter))
                {
                    if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB4))) {
                        if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                        }
                        worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB5));
                    }
                    else if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB9))) {
                        if (!playerIn.isCreative()) {
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                        worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB10));
                    }
                    else if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB14))) {
                        if (!playerIn.isCreative()) {
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                        worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB15));
                    }
                    else if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB15))) {
                        if (!playerIn.isCreative()) {
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                        worldIn.setBlockState(pos, BlockRegistry.claybloomery.getDefaultState());
                    }
                }


            else if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.ACACIA_FENCE)) ||
                    ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.BIRCH_FENCE)) ||
                    ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.DARK_OAK_FENCE)) ||
                    ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.JUNGLE_FENCE)) ||
                    ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.OAK_FENCE)) ||
                    ItemStack.areItemsEqual(playerIn.getHeldItem(hand),new ItemStack(Blocks.SPRUCE_FENCE)))
            {
                if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB10))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB11));
                }
                else if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB11))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB12));
                }
                else if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB12))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB13));
                }
                else if (state.equals(BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB13))) {
                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, BlockRegistry.unfiredbloomery.getDefaultState().withProperty(BlockUnfiredBloomery.STAGE, EnumBlock.UnfiredBloomeryBlock.UBB14));
                }
            }





        return true;
    }
}