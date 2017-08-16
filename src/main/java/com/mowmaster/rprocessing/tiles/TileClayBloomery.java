package com.mowmaster.rprocessing.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;

import java.util.ArrayList;
import java.util.List;

import static com.mowmaster.rprocessing.configs.RealConfig.*;


public class TileClayBloomery extends TileEntity implements ITickable
{
    public int oxygencount=0;
    public int maxoxygen=1000; //because if 95 it can go up to 100
    public int needsoxygen = 0;
    public int maxneedsoxygen = 200;
    public int carboncount=0;
    public int maxcarbon=8;

    public int maxore=8;

    public int processtimer = 0;
    public int maxprocessedtime = 300;//seconds
    public int burntimer = 0;
    public int tickercoal = 0;
    public int ticker20 = 0;
    public int tickerO2 = 0;


    public int activated = 0;
    public int maxactivated = 1;
    public int processed = 0;
    public int maxprocessed = 1;

    public List<ItemStack> orelist = new ArrayList<>();


    //public static boolean activated = false;
    //public static boolean processed = false;




    public boolean addCarbon()
    {
        if(carboncount<maxcarbon)
        {
            carboncount++;
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
        return false;
    }
    public boolean removeCarbon()
    {
        if (activated == 0)
        {
            if(orelist.isEmpty())
            {
                if(carboncount>0)
                {
                    world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.COAL)));
                    carboncount--;
                    markDirty();
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos,state,state,3);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean activate()
    {
        if(carboncount>0)
        {
            if(activated == 0)
            {
                activated = 1;
                needsoxygen = 0;
                markDirty();
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos,state,state,3);
                return true;
            }

        }
        return false;
    }
    public boolean deactivate()
    {
        if(activated == 1)
        {
            activated = 0;
            oxygencount = 0;
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
        return false;
    }

    public boolean addOxygen()
    {
        if(activated == 1)
        {
            if(oxygencount<maxoxygen)
            {
                oxygencount += 200;
                markDirty();
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos,state,state,3);
                return true;
            }
        }

        return false;
    }

    public void resetNeedsOxygen()
    {
        needsoxygen = 0;
    }

    public boolean addOre(ItemStack ore)
    {
        if(activated != 0 || processtimer !=0)
        {
            return false;
        }
        if (orelist.size() < maxore && ore.getCount() == 1)
        {
            orelist.add(ore);
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
        return false;
    }

    public boolean removeOre()
    {
        if (activated != 0 || processtimer != 0)
        {
            return false;
        }
        if (!orelist.isEmpty())
        {
            ItemStack ore = orelist.remove(orelist.size() - 1);
            world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, ore));
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        return false;
    }

    public boolean finishedProcessing()
    {
        deactivate();
        carboncount = 0;
        oxygencount = 0;
        processtimer = 0;
        needsoxygen = 0;
        processed = 0;
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos,state,state,3);
        return true;
    }

    @Override
    public void update()
    {
        //System.out.println("Activated :" + activated);
        if (activated == 1)
        {
            if (oxygencount >= 750) {
                world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0, 0.0, 0.0, new int[0]);
            }
            if (oxygencount < 750 && oxygencount >= 400) {
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.1, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.1, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);

            }
            if (oxygencount < 400 && oxygencount >= 100) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            }
            if (oxygencount < 100) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            }
        }


        if(!world.isRemote) {

            if (activated == 1) {
                //if it runs out of Carbon
                if(carboncount == 0) {deactivate();}

                if(!orelist.isEmpty())
                {
                    tickerO2++;
                    if(tickerO2 > bloomeryOxygenTR)
                    {
                        tickerO2 = 0;
                        if (oxygencount > 0) {
                            oxygencount--;
                            IBlockState state = world.getBlockState(pos);
                            world.notifyBlockUpdate(pos,state,state,3);
                            //System.out.println("Oxygen Count :" + oxygencount);
                        }
                    }


                    if (oxygencount == 0) {
                        if (needsoxygen < maxneedsoxygen) {
                            needsoxygen++;
                            //System.out.println("Oxygen Timer Count :" + needsoxygen);
                        }
                    }
                    // If it runs out of Oxygen
                    if (needsoxygen == maxneedsoxygen) {deactivate();}
                    //processing
                    if (processtimer < maxprocessedtime)
                    {
                        ticker20++;
                        if(ticker20 > 20)
                        {
                            ticker20 = 0;
                            processtimer++;

                            //uses carbon up as it works
                            if(carboncount>0)
                            {
                                tickercoal++;
                                if(!orelist.isEmpty())
                                {
                                    if(bloomeryCoalCR > maxprocessedtime)
                                        throw new RuntimeException(this.getClass() + "Coal Consumption Rate Cannot be more then Processing Time");
                                    else
                                    {
                                        if(tickercoal>bloomeryCoalCR)
                                        {
                                            tickercoal = 0;
                                            carboncount--;
                                        }
                                    }
                                }

                                if(!orelist.isEmpty())
                                {
                                    if(tickercoal>bloomeryCoalAstheticCR)
                                    {
                                        tickercoal = 0;
                                        carboncount--;
                                    }
                                }
                            }
                            //System.out.println(processtimer);
                        }
                    }
                    //finished processing
                    if (processtimer == maxprocessedtime) {processed = 1;}
                }

                if (processed == 1)
                {
                    for (ItemStack ore : orelist)
                    {
                        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(ore).copy();
                        result.setCount(2);

                        world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, result));
                    }
                    orelist.clear();
                    finishedProcessing();
                }
            }

        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.writeUpdateTag(compound);
        compound.setInteger("needsoxygen", needsoxygen);
        compound.setInteger("processed",processed);
        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readUpdateTag(compound);
        this.needsoxygen = compound.getInteger("needsoxygen");
        this.processed = compound.getInteger("processed");
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeUpdateTag(tagCompound);
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tagCompound);

    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound tagCompound = pkt.getNbtCompound();
        this.readUpdateTag(tagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tagCompound = super.getUpdateTag();
        writeUpdateTag(tagCompound);
        return tagCompound;
    }

    public void writeUpdateTag(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("carboncount",carboncount);
        tagCompound.setInteger("activated",activated);
        tagCompound.setInteger("oxygencount",oxygencount);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0;i<orelist.size();i++)
        {
            {
                ItemStack itemstack = orelist.get(i);
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                itemstack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        tagCompound.setInteger("timer",processtimer);
        tagCompound.setInteger("timer20",ticker20);
        tagCompound.setInteger("timerO2",tickerO2);
        tagCompound.setInteger("burntimer",burntimer);
        tagCompound.setInteger("tickercoal",tickercoal);


    }

    public void readUpdateTag(NBTTagCompound tagCompound)
    {
        this.carboncount = tagCompound.getInteger("carboncount");
        this.activated = tagCompound.getInteger("activated");
        this.oxygencount = tagCompound.getInteger("oxygencount");

        this.orelist.clear();
        NBTTagList taglist = tagCompound.getTagList("orelist", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < taglist.tagCount(); i++)
        {
            NBTTagCompound tag = (NBTTagCompound)taglist.get(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < maxore)
            {
                this.orelist.add(slot, new ItemStack(tag));
            }
        }


        this.processtimer = tagCompound.getInteger("timer");
        this.ticker20 = tagCompound.getInteger("timer20");
        this.tickerO2 = tagCompound.getInteger("timerO2");
        this.burntimer = tagCompound.getInteger("burntimer");
        this.tickercoal = tagCompound.getInteger("tickercoal");
    }


}