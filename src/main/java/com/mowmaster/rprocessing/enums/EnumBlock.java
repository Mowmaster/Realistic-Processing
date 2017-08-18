package com.mowmaster.rprocessing.enums;


import net.minecraft.util.IStringSerializable;

public class EnumBlock
{
    public static enum ClayMorterBlock implements IStringSerializable
    {
        //MIX1("mix1",0),//woodplank-->woodbase
        MIX2("mix2",0),//woodbase-->woodmixingbowl
        MIX3("mix3",1),//mixingbown+sand
        MIX4("mix4",2),//mix+clay1
        MIX5("mix5",3),//mix+clay2
        MIX6("mix6",4),//mix+clay3
        MIX7("mix7",5),//claymix+bonem1
        MIX8("mix8",6),//claymix+bonem2
        MIX9("mix9",7),//claymix+bonem3
        MIX10("mix10",8),//claybonemix+wheat
        MIX11("mix11",9),//mixture+water
        MIX12("mix12",10);//Final Block Itsself
        //reverts back to MIX2 or meta 1 on right click of MIX11 or meta 10

        private int ID;
        private String name;
        private ClayMorterBlock(String name, int ID)
        {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        public int getID()
        {
            return ID;
        }

        @Override
        public String toString()
        {
            return getName();
        }

    }

    public static enum UnfiredBloomeryBlock implements IStringSerializable
    {
        UBB1("ubb1",0),//brick1
        UBB2("ubb2",1),//brick2
        UBB3("ubb3",2),//brick3
        UBB4("ubb4",3),//brick4
        UBB5("ubb5",4),//player uses clay to make layer 1
        UBB6("ubb6",5),//brick1
        UBB7("ubb7",6),//brick2
        UBB8("ubb8",7),//brick3
        UBB9("ubb9",8),//brick4
        UBB10("ubb10",9),//player uses clay to make layer 2
        UBB11("ubb11",10),//brick block + one iron bars
        UBB12("ubb12",11),//brick block + two iron bars
        UBB13("ubb13",12),//brick block + three iron bars
        UBB14("ubb14",13),//brick block + four iron bars
        UBB15("ubb15",14),//framed UFB + claymorter1
        UBB16("ubb16",15);//framed UFB + claymorter2


        private int ID;
        private String name;
        private UnfiredBloomeryBlock(String name, int ID)
        {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        public int getID()
        {
            return ID;
        }

        @Override
        public String toString()
        {
            return getName();
        }

    }
}
