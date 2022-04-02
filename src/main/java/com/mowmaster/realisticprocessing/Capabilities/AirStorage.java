package com.mowmaster.realisticprocessing.Capabilities;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class AirStorage implements IAirStorage, INBTSerializable<Tag>
{
    protected int rp_air;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public AirStorage(int capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public AirStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public AirStorage(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public AirStorage(int capacity, int maxReceive, int maxExtract, int air)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.rp_air = Math.max(0 , Math.min(capacity, air));
    }

    @Override
    public int receiveRP_Air(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int rp_airReceived = Math.min(capacity - rp_air, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            rp_air += rp_airReceived;
        return rp_airReceived;
    }

    @Override
    public int extractRP_Air(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int rp_airExtracted = Math.min(rp_air, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            rp_air -= rp_airExtracted;
        return rp_airExtracted;
    }

    @Override
    public int getRP_AirStored() {
        return rp_air;
    }

    @Override
    public int getMaxRP_AirStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public Tag serializeNBT() {
        return IntTag.valueOf(this.getRP_AirStored());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.rp_air = intNbt.getAsInt();
    }
}
