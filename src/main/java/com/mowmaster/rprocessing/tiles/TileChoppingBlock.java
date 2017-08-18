package com.mowmaster.rprocessing.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;


public class TileChoppingBlock extends TileEntity implements ITickable
{
    // Player should have access to coal, charcoal, sticks and planks
    private int fuelcount = 0;//needs 1000 at least
    private int processtimermin=0;
    private int getProcesstimermax = 1000;


    public boolean start()
    {
        return true;
    }

    @Override
    public void update()
    {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.writeUpdateTag(compound);
        //compound.setInteger("needsoxygen", needsoxygen);

        return compound;

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readUpdateTag(compound);
        //this.needsoxygen = compound.getInteger("needsoxygen");

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
        //tagCompound.setInteger("carboncount",carboncount);
    }

    public void readUpdateTag(NBTTagCompound tagCompound)
    {
        //this.carboncount = tagCompound.getInteger("carboncount");
    }


}