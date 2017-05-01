package com.mowmaster.rprocessing.configs;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by KingMowmaster on 5/1/2017.
 */
public class RealConfig
{
    public static Configuration realConfig;


    public static int bloomeryProcessingTime;//in seconds Default is 300
    //CR = Cunsumption Rate
    //TR = Tick Rate
    public static int bloomeryCoalCR;//in seconds default is 40
    public static int bloomeryCoalAstheticCR;//in seconds default is 80
    public static int bloomeryOxygenTR;//variable tick rate for oxygen loss default is 1


    public static void InitConfig(File file)
    {
        realConfig = new Configuration(file);
        SyncConfig();
    }

    public static void SyncConfig()
    {
        String category;

        category = "Bloomery";
        realConfig.addCustomCategoryComment(category, "Bloomery Tweaks");

        bloomeryProcessingTime = realConfig.getInt("Bloomery Processing Time",category,300,1,86400,"Time in Seconds for the Bloomery to Finish Processing");
        bloomeryCoalCR = realConfig.getInt("Coal Consumption Rate",category,40,1,86400,"Time in seconds for coal to be consumed by the smelting process");
        bloomeryCoalAstheticCR = realConfig.getInt("Coal CR for Asthetic Burns",category,80,0,604800,"Time in seconds for the consumption of coal for Asthetic burns");
        bloomeryOxygenTR = realConfig.getInt("Oxygen Consumption Per 'X' Ticks",category,1,1,3600,"Every 'X' ticks 1/1000 units of Oxygen will be consumed");


        realConfig.save();
    }
}
