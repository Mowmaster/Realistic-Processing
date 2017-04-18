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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.lang.ref.Reference;

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
            if (tileEntity instanceof TileClayBloomery) {
                TileClayBloomery clayBloomery = (TileClayBloomery) tileEntity;
                if (playerIn.isSneaking()) {
                    playerIn.sendStatusMessage(new TextComponentTranslation("You are sneaking"), true);
                    playerIn.sendMessage(new TextComponentString("Test Test Test"));
                }
                else
                {
                    if (playerIn.getHeldItem(EnumHand.MAIN_HAND) == new ItemStack(Items.COAL))
                    {
                        clayBloomery.addCarbon();
                        playerIn.sendMessage(new TextComponentString("Added Carbon to Bloomery"));
                    }
                    else
                    {
                        if(playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
                        {
                            clayBloomery.removeCarbon();
                            spawnAsEntity(worldIn,pos,new ItemStack(Items.COAL));
                            playerIn.sendMessage(new TextComponentString("Removed Carbon from Bloomery"));
                        }

                    }
                }
            }
        }
        return true;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClayBloomery();
    }
}
