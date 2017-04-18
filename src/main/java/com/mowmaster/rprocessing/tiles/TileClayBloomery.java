package com.mowmaster.rprocessing.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by KingMowmaster on 4/18/2017.
 */
public class TileClayBloomery extends TileEntity
{
    public static int oxygencount = 0;
    public static int maxoxygen = 95; //because if 95 it can go up to 100
    public static int carboncount = 0;
    public static int maxcarbon = 8;
    public static int maxore = 8;
    public static int oreiron = 0;
    public static int oregold = 0;



    public void addCarbon()
    {
        if(carboncount < maxcarbon)
        {
            carboncount ++;
        }
    }
    public void removeCarbon()
    {
        if(carboncount > 0)
        {
            carboncount--;
        }
    }



    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("carboncount",carboncount);
        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.getInteger("carboncount");


    }
}
