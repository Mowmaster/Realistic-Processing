package com.mowmaster.realisticprocessing.Capabilities;

public interface IAirStorage
{
    /**
     * Adds rp_air to the storage. Returns quantity of rp_air that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of rp_air to be inserted.
     * @param simulate
     *            If TRUE, the insertion will only be simulated.
     * @return Amount of rp_air that was (or would have been, if simulated) accepted by the storage.
     */
    int receiveRP_Air(int maxReceive, boolean simulate);

    /**
     * Removes rp_air from the storage. Returns quantity of rp_air that was removed.
     *
     * @param maxExtract
     *            Maximum amount of rp_air to be extracted.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of rp_air that was (or would have been, if simulated) extracted from the storage.
     */
    int extractRP_Air(int maxExtract, boolean simulate);

    /**
     * Returns the amount of rp_air currently stored.
     */
    int getRP_AirStored();

    /**
     * Returns the maximum amount of rp_air that can be stored.
     */
    int getMaxRP_AirStored();

    /**
     * Returns if this storage can have rp_air extracted.
     * If this is false, then any calls to extractRP_Air will return 0.
     */
    boolean canExtract();

    /**
     * Used to determine if this storage can receive rp_air.
     * If this is false, then any calls to receiveRP_Air will return 0.
     */
    boolean canReceive();
}
