package com.mowmaster.rprocessing.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

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
    public int orecount=0;
    public int oreiron=0;
    public int oregold=0;
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


    //public static boolean activated = false;
    //public static boolean processed = false;




    public boolean addCarbon()
    {
        if(carboncount<maxcarbon)
        {
            carboncount++;
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
        return false;
    }
    public boolean removeCarbon()
    {
        if (activated == 0)
        {
            if(oreiron ==0 && oregold ==0)
            {
                if(carboncount>0)
                {
                    worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.COAL)));
                    carboncount--;
                    markDirty();
                    IBlockState state = worldObj.getBlockState(pos);
                    worldObj.notifyBlockUpdate(pos,state,state,3);
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
                IBlockState state = worldObj.getBlockState(pos);
                worldObj.notifyBlockUpdate(pos,state,state,3);
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
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos,state,state,3);
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
                IBlockState state = worldObj.getBlockState(pos);
                worldObj.notifyBlockUpdate(pos,state,state,3);
                return true;
            }
        }

        return false;
    }

    public void resetNeedsOxygen()
    {
        needsoxygen = 0;
    }

    public boolean addIron()
    {
        if(activated == 0)
        {
            if(processtimer == 0)
            {
                if(oregold == 0)
                {
                    if(oreiron<maxore)
                    {
                        oreiron++;
                        orecount++;
                        markDirty();
                        IBlockState state = worldObj.getBlockState(pos);
                        worldObj.notifyBlockUpdate(pos,state,state,3);
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public boolean addGold()
    {
        if(activated == 0)
        {
            if(processtimer == 0)
            {
                if(oreiron == 0)
                {
                    if(oregold<maxore)
                    {
                        oregold++;
                        orecount++;
                        markDirty();
                        IBlockState state = worldObj.getBlockState(pos);
                        worldObj.notifyBlockUpdate(pos,state,state,3);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean removeIron()
    {
        if (activated == 0)
        {
            if(processtimer == 0)
            {
                if(oregold == 0)
                {
                    if(oreiron>0)
                    {
                        worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Blocks.IRON_ORE)));
                        oreiron--;
                        orecount--;
                        markDirty();
                        IBlockState state = worldObj.getBlockState(pos);
                        worldObj.notifyBlockUpdate(pos,state,state,3);
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public boolean removeGold()
    {
        if (activated == 0)
        {
            if(processtimer == 0)
            {
                if(oreiron == 0)
                {
                    if(oregold>0)
                    {
                        worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Blocks.GOLD_ORE)));
                        oregold--;
                        orecount--;
                        markDirty();
                        IBlockState state = worldObj.getBlockState(pos);
                        worldObj.notifyBlockUpdate(pos,state,state,3);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean finishedProcessing()
    {
        deactivate();
        oreiron = 0;
        oregold = 0;
        orecount = 0;
        carboncount = 0;
        oxygencount = 0;
        processtimer = 0;
        needsoxygen = 0;
        processed = 0;
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(pos,state,state,3);
        return true;
    }

    @Override
    public void update()
    {
        //System.out.println("Activated :" + activated);
        if (activated == 1)
        {
            if (oxygencount >= 750) {
                worldObj.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0, 0.0, 0.0, new int[0]);
            }
            if (oxygencount < 750 && oxygencount >= 400) {
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.1, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.1, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);

            }
            if (oxygencount < 400 && oxygencount >= 100) {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            }
            if (oxygencount < 100) {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            }
        }


        if(!worldObj.isRemote) {

            if (activated == 1) {
                //if it runs out of Carbon
                if(carboncount == 0) {deactivate();}

                if(orecount > 0)
                {
                    tickerO2++;
                    if(tickerO2 > bloomeryOxygenTR)
                    {
                        tickerO2 = 0;
                        if (oxygencount > 0) {
                            oxygencount--;
                            IBlockState state = worldObj.getBlockState(pos);
                            worldObj.notifyBlockUpdate(pos,state,state,3);
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
                                if(orecount > 0)
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

                                if(orecount ==0)
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

                if (processed == 1) {
                    if (oreiron > 0) {
                        worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(Items.IRON_INGOT, oreiron * 2)));
                        oreiron = 0;
                        orecount = 0;
                    }
                    if (oregold > 0) {
                        worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(Items.GOLD_INGOT, oregold * 2)));
                        oregold = 0;
                        orecount = 0;
                    }

                    if (oreiron == 0 && oregold == 0) {
                        finishedProcessing();
                        //System.out.println("Bloomery Reset");
                    }
                }
            }

        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.writeUpdateTag(compound);
        compound.setInteger("orecount",orecount);
        compound.setInteger("needsoxygen", needsoxygen);
        compound.setInteger("processed",processed);
        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readUpdateTag(compound);
        this.orecount = compound.getInteger("orecount");
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
        tagCompound.setInteger("iron", oreiron);
        tagCompound.setInteger("gold",oregold);
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
        this.oreiron = tagCompound.getInteger("iron");
        this.oregold = tagCompound.getInteger("gold");
        this.processtimer = tagCompound.getInteger("timer");
        this.ticker20 = tagCompound.getInteger("timer20");
        this.tickerO2 = tagCompound.getInteger("timerO2");
        this.burntimer = tagCompound.getInteger("burntimer");
        this.tickercoal = tagCompound.getInteger("tickercoal");
    }


}