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


public class TileClayBloomery extends TileEntity implements ITickable
{
    public static int oxygencount=0;
    public static int maxoxygen=1000; //because if 95 it can go up to 100
    public static int needsoxygen = 0;
    public static int maxneedsoxygen = 200;
    public static int carboncount=0;
    public static int maxcarbon=8;
    public static int maxore=8;
    public static int orecount=0;
    public static int oreiron=0;
    public static int oregold=0;
    public static int processtimer = 0;
    public static int maxprocessedtime = 6000;

    public static boolean activated = false;
    public static boolean processed = false;



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
        if (activated == false)
        {
            if(oreiron ==0 && oregold ==0)
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
            if(carboncount>=orecount)
            {
                if(activated == false)
                {
                    activated = true;
                    needsoxygen = 0;
                    markDirty();
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos,state,state,3);
                    return true;
                }
            }

        }
        return false;
    }
    public boolean deactivate()
    {
        if(activated == true)
        {
            activated = false;
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
        return false;
    }

    public boolean addOxygen()
    {
            if(oxygencount<maxoxygen)
            {
                oxygencount += 200;
                markDirty();
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos,state,state,3);
                return true;
            }
        return false;
    }

    public void resetNeedsOxygen()
    {
        needsoxygen = 0;
    }

    public boolean addIron()
    {
    if(oregold == 0)
    {
        if(oreiron<maxore)
        {
            oreiron++;
            orecount++;
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
    }
    return false;
    }
    public boolean addGold()
    {
        if(oreiron == 0)
        {
            if(oregold<maxore)
            {
                oregold++;
                orecount++;
                markDirty();
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos,state,state,3);
                return true;
            }
        }
        return false;
    }

    public boolean removeIron()
    {
        if (activated == false)
        {
            if(oregold == 0)
            {
                if(oreiron>0)
                {
                    world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Blocks.IRON_ORE)));
                    oreiron--;
                    orecount--;
                    markDirty();
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos,state,state,3);
                    return true;
                }
            }
        }

        return false;
    }
    public boolean removeGold()
    {
        if (activated == false)
        {
            if(oreiron == 0)
            {
                if(oregold>0)
                {
                    world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Blocks.GOLD_ORE)));
                    oregold--;
                    orecount--;
                    markDirty();
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos,state,state,3);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void update()
    {
        if (activated == true)
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

            if (activated == true) {

                if(carboncount>0)
                {
                    if(processtimer==1500)
                    {
                        carboncount--;
                    }
                    if(processtimer==3000)
                    {
                        carboncount--;
                    }
                    if(processtimer==4500)
                    {
                        carboncount--;
                    }
                }

                if (oxygencount > 0) {
                    oxygencount--;
                    //System.out.println("Oxygen Count :" + oxygencount);

                }

                if (oxygencount == 0) {
                    if (needsoxygen < maxneedsoxygen) {
                        needsoxygen++;
                        //System.out.println("Oxygen Timer Count :" + needsoxygen);
                    }
                }

                if (needsoxygen == maxneedsoxygen) {
                    activated = false;
                    processtimer = 0;
                    System.out.println("Bloomery Off and Reset");

                }

                if (processtimer < maxprocessedtime) {
                    processtimer++;
                }

                if (processtimer == maxprocessedtime) {
                    processed = true;
                    System.out.println("Bloomery COMPLETED");
                }


                if (processed == true) {
                    if (oreiron > 0) {
                        world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(Items.IRON_INGOT, oreiron * 2)));
                        oreiron = 0;
                        orecount = 0;
                    }
                    if (oregold > 0) {
                        world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(Items.GOLD_INGOT, oregold * 2)));
                        oregold = 0;
                        orecount = 0;
                    }

                    if (oreiron == 0 && oregold == 0) {
                        activated = false;
                        oreiron = 0;
                        oregold = 0;
                        orecount = 0;
                        carboncount = 0;
                        oxygencount = 0;
                        processtimer = 0;
                        needsoxygen = 0;
                        processed = false;
                        System.out.println("Bloomery Reset");
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
        compound.setBoolean("processed",processed);
        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readUpdateTag(compound);
        this.orecount = compound.getInteger("orecount");
        this.needsoxygen = compound.getInteger("needsoxygen");
        this.processed = compound.getBoolean("processed");
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound tagCompound = pkt.getNbtCompound();
        this.readUpdateTag(tagCompound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeUpdateTag(tagCompound);
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tagCompound);

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
        tagCompound.setBoolean("activated",activated);
        tagCompound.setInteger("oxygencount",oxygencount);
        tagCompound.setInteger("iron", oreiron);
        tagCompound.setInteger("gold",oregold);
        tagCompound.setInteger("timer",processtimer);
    }
    public void readUpdateTag(NBTTagCompound tagCompound)
    {
        this.carboncount = tagCompound.getInteger("carboncount");
        this.activated = tagCompound.getBoolean("activated");
        this.oxygencount = tagCompound.getInteger("oxygencount");
        this.oreiron = tagCompound.getInteger("iron");
        this.oregold = tagCompound.getInteger("gold");
        this.processtimer = tagCompound.getInteger("timer");
    }

}
