package com.mowmaster.rprocessing.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by KingMowmaster on 4/18/2017.
 */
public class TileClayBloomery extends TileEntity implements ITickable
{
    public static int oxygencount=0;
    public static int maxoxygen=250; //because if 95 it can go up to 100
    public static int needsoxygen = 0;
    public static int maxneedsoxygen = 100;
    public static int carboncount=0;
    public static int maxcarbon=8;
    public static int maxore=8;
    public static int orecount=0;
    public static int oreiron=0;
    public static int oregold=0;
    public static int processtimer = 0;
    public static int maxprocessedtime = 200;

    public static boolean activated = false;
    public static boolean processed = false;



    public boolean addCarbon()
    {
        if(carboncount<maxcarbon)
        {
            carboncount++;
            System.out.println("You are adding Carbon");
            System.out.println("Carbon Count :" + carboncount);
            return true;
        }
        return false;
    }
    public boolean removeCarbon()
    {
        if(carboncount>0)
        {
            world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.COAL)));
            carboncount--;
            System.out.println("You are removing Carbon");
            System.out.println("Carbon Count :" + carboncount);
            return true;
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
                    System.out.println("Activation :" + activated);
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
            System.out.println("Activation :" + activated);
            return true;
        }
        return false;
    }

    public boolean addOxygen()
    {
            if(oxygencount<maxoxygen)
            {
                oxygencount += 50;
                System.out.println("You are adding Oxygen");
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
            System.out.println("Iron Count :" + oreiron);
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
                System.out.println("Gold Count :" + oregold);
                return true;
            }
        }
        return false;
    }

    public boolean removeIron()
    {
        if(oregold == 0)
        {
            if(oreiron>0)
            {
                world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Blocks.IRON_ORE)));
                oreiron--;
                orecount--;
                System.out.println("Iron Count :" + oreiron);
                return true;
            }
        }
        return false;
    }
    public boolean removeGold()
    {
        if(oreiron == 0)
        {
            if(oregold>0)
            {
                world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Blocks.GOLD_ORE)));
                oregold--;
                orecount--;
                System.out.println("Gold Count :" + oregold);
                return true;
            }
        }
        return false;
    }

    @Override
    public void update()
    {

        if(activated == true)
        {

            if(oxygencount>0)
            {
                oxygencount--;
                System.out.println("Oxygen Count :" + oxygencount);

            }

            if(oxygencount == 0)
            {
                if(needsoxygen<maxneedsoxygen)
                {
                    needsoxygen++;
                    System.out.println("Oxygen Timer Count :" + needsoxygen);
                }
            }

            if(oxygencount > 100)
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 1, 0.5,0.5,0.5,new int[0]);
            }

            if(oxygencount < 100)
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 1, 0.5,0.5,0.5,new int[0]);
            }

            if(oxygencount < 50)
            {
                world.spawnParticle(EnumParticleTypes.LAVA,pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 1, 0.5,0.5,0.5,new int[0]);
            }

            if(needsoxygen == maxneedsoxygen)
            {
                activated = false;
                processtimer = 0;
                System.out.println("Bloomery Off and Reset");

            }

            if(processtimer<maxprocessedtime)
            {
                processtimer++;
            }

            if(processtimer == maxprocessedtime)
            {
                processed = true;
                System.out.println("Bloomery COMPLETED");
            }

            if(processed == true)
            {
                if(oreiron >0)
                {
                    world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.IRON_INGOT,oreiron)));
                    oreiron = 0;
                    orecount = 0;
                }
                if(oregold >0)
                {
                    world.spawnEntity(new EntityItem(this.world, pos.getX() + 0.5,pos.getY() + 1.0,pos.getZ() + 0.5,new ItemStack(Items.GOLD_INGOT,oregold)));
                    oregold = 0;
                    orecount = 0;
                }

                if(oreiron == 0 && oregold == 0)
                {
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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("carboncount",carboncount);
        compound.setBoolean("activated",activated);
        compound.setInteger("oxygencount",oxygencount);
        compound.setInteger("needsoxygen", needsoxygen);
        compound.setInteger("orecount",orecount);
        compound.setInteger("iron", oreiron);
        compound.setInteger("gold",oregold);
        compound.setInteger("timer",processtimer);
        compound.setBoolean("processed",processed);


        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.getInteger("carboncount");
        compound.getBoolean("activated");
        compound.getInteger("oxygencount");
        compound.getInteger("needsoxygen");
        compound.getInteger("orecount");
        compound.getInteger("iron");
        compound.getInteger("gold");
        compound.getInteger("timer");
        compound.getBoolean("processed");


    }
}
