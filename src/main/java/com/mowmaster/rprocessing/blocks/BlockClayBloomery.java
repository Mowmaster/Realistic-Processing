package com.mowmaster.rprocessing.blocks;

import com.mowmaster.rprocessing.configs.RealTab;
import com.mowmaster.rprocessing.reference.References;
import com.mowmaster.rprocessing.tiles.TileClayBloomery;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.ref.Reference;
import java.util.Random;

import static com.mowmaster.rprocessing.configs.RealTab.REAL_TAB;

/**
 * Created by KingMowmaster on 4/18/2017.
 */
public class BlockClayBloomery extends Block implements ITileEntityProvider
{
    public BlockClayBloomery(String unlocalizedName, String registryName)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MODID, registryName));
        this.setCreativeTab(REAL_TAB);


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
                            playerIn.sendMessage(new TextComponentString("You are adding Carbon"));
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                    }
                }
                if(playerIn.isSneaking())
                {
                    if(bloom.removeCarbon())
                    {
                        playerIn.sendMessage(new TextComponentString("You are removing Carbon"));
                    }
                }

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Blocks.IRON_ORE))) {
                        if(bloom.addIron())
                        {
                            playerIn.sendMessage(new TextComponentString("You are adding Iron Ore"));
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                    }
                }
                if(playerIn.isSneaking())
                {
                    if(bloom.removeIron())
                    {
                        playerIn.sendMessage(new TextComponentString("You are removing Iron Ore"));
                    }
                }

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Blocks.GOLD_ORE))) {
                        if(bloom.addGold())
                        {
                            playerIn.sendMessage(new TextComponentString("You are adding Gold Ore"));
                            playerIn.getHeldItem(hand).shrink(1);
                        }
                    }
                }
                if(playerIn.isSneaking())
                {
                    if(bloom.removeGold())
                    {
                        playerIn.sendMessage(new TextComponentString("You are removing Gold Ore"));
                    }
                }

                if((playerIn.getHeldItem(hand) != null))
                {
                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.FLINT_AND_STEEL)))
                    {
                        if(bloom.activate())
                        {
                            playerIn.sendMessage(new TextComponentString("You have Lit the Bloomery"));
                        }
                    }

                    if(ItemStack.areItemsEqual(playerIn.getHeldItem(hand), new ItemStack(Items.WATER_BUCKET)))
                    {
                        if(bloom.deactivate())
                        {
                            playerIn.sendMessage(new TextComponentString("You have put out the Bloomery"));
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
                            playerIn.sendMessage(new TextComponentString("You have added Oxygen to the Bloomery"));
                        }
                    }
                }






            }
        }
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClayBloomery();
    }


}
