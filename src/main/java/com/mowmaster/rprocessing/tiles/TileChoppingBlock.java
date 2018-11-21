package com.mowmaster.rprocessing.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;


public class TileChoppingBlock extends TileEntity implements ITickable
{
    ItemStackHandler itemOnCB;
    private double chopProgress = 0.0;

    public TileChoppingBlock()
    {
        this.itemOnCB = new ItemStackHandler();
    }

    public ItemStack getBlockOnCB()
    {
        return itemOnCB.getStackInSlot(0);
    }

    public boolean hasBlockOnCB()
    {
        if(itemOnCB.getStackInSlot(0).isEmpty())
        {
            return false;
        }
        else return true;
    }

    private void updateBlock()
    {
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.getRedstonePower(pos, EnumFacing.UP);
        world.notifyBlockUpdate(pos, state, state, 3);
        world.setBlockState(pos,state,3);
    }

    public boolean addItemToCB(ItemStack input)
    {
        if(canBeAdded(input))
        {
            ItemStack itemToAdd = new ItemStack(input.getItem(),1,input.getMetadata());
            chopProgress=0.0;
            itemOnCB.insertItem(0,itemToAdd,false);
            updateBlock();
            return true;
        }

        return false;
    }

    public boolean canBeAdded(ItemStack input)
    {
        IBlockState block = Block.getBlockFromItem(input.getItem()).getDefaultState();
        if(block.getMaterial().equals(Material.WOOD))
        {
            if(hasCraftingOutput(input) || hasCraftingOutput2(input))
            {
                if(Block.getBlockFromItem(input.getItem())!= Blocks.AIR)
                {
                    if(!hasBlockOnCB())
                    {
                        return true;
                    }
                }
            }
        }


        return false;
    }

    int ticker=0;
    @Override
    public void update() {
        ticker++;
        if(ticker>=20)
        {
            updateBlock();
            ticker=0;
        }
    }

    public ItemStack removeBlockFromCB()
    {
        ItemStack returned = ItemStack.EMPTY;
        if(getBlockOnCB().isEmpty())
        {
            chopProgress=0.0;
            returned = ItemStack.EMPTY;
            updateBlock();
        }
        else
        {
            chopProgress=0.0;
            returned = getBlockOnCB().copy();
            itemOnCB.setStackInSlot(0,ItemStack.EMPTY);
            updateBlock();
        }

        return returned;
    }



    public boolean hasCraftingOutput(ItemStack input)
    {
        if(getCraftingOutput(input).equals(ItemStack.EMPTY))
        {
            return false;
        }
        else return true;
    }

    public boolean hasCraftingOutput2(ItemStack input)
    {
        if(getCraftingOutput2(input).equals(ItemStack.EMPTY))
        {
            return false;
        }
        else return true;
    }

    public ItemStack getCraftingOutput(ItemStack input)
    {
        InventoryCrafting craft = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(@Nonnull EntityPlayer player) {
                return false;
            }
        }, 1, 1);

        craft.setInventorySlotContents(0, input);

        for(IRecipe recipe : ForgeRegistries.RECIPES)
        {
            if(recipe.matches(craft, world)) {

                ItemStack crafted = recipe.getCraftingResult(craft);
                crafted.setCount(Math.abs(crafted.getCount())+1);
                return crafted;
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack getCraftingOutput2(ItemStack input)
    {
        InventoryCrafting craft = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(@Nonnull EntityPlayer player) {
                return false;
            }
        }, 2, 2);

        craft.setInventorySlotContents(0, input);
        craft.setInventorySlotContents(2, input);

        for(IRecipe recipe : ForgeRegistries.RECIPES)
        {
            if(recipe.matches(craft, world)) {
                ItemStack crafted = recipe.getCraftingResult(craft);
                crafted.setCount(Math.abs(crafted.getCount()/2)+1);

                return crafted;
            }
        }

        return ItemStack.EMPTY;
    }

    public void addChopProgress(double progress)
    {
        if(chopProgress+progress>=16.0)
        {
            if(!world.isRemote)
            {
                ItemStack itemToSummon = ItemStack.EMPTY;
                if(hasCraftingOutput2(getBlockOnCB()))
                {
                    itemToSummon = getCraftingOutput2(getBlockOnCB()).copy();
                }
                else
                {
                    itemToSummon = getCraftingOutput(getBlockOnCB()).copy();
                }

                EntityItem itemEntity = new EntityItem(world,pos.getX() + 0.5,pos.getY(),pos.getZ() + 0.5,itemToSummon);
                itemEntity.motionX = 0;
                itemEntity.motionY = 0;
                itemEntity.motionZ = 0;
                removeBlockFromCB();
                world.spawnEntity(itemEntity);
            }
            chopProgress=0.0;
            updateBlock();
        }
        else {
            chopProgress+=progress;
            updateBlock();
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        //this.writeUpdateTag(compound);
        //compound.setInteger("needsoxygen", needsoxygen);
        compound.setTag("blockOnCB", this.itemOnCB.serializeNBT());
        compound.setDouble("chopProgress",this.chopProgress);

        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.itemOnCB.deserializeNBT(compound.getCompoundTag("blockOnCB"));
        this.chopProgress=compound.getDouble("chopProgress");

    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }


}