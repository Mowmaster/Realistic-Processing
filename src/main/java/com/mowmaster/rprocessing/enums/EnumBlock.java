package com.mowmaster.rprocessing.enums;


import net.minecraft.util.IStringSerializable;

public class EnumBlock
{
    public static enum ClayMorterBlock implements IStringSerializable
    {
        MIX1("mix1",0),//sand + clay block
        MIX2("mix2",1),//mix1 + bonemeal1
        MIX3("mix3",2),//mix2 + bonemeal2
        MIX4("mix4",3),//mix3 + bonemeal3
        MIX5("mix5",4),//mix4 + wheat, vines, sugarcane
        MIX6("mix6",5);//mix5 + water

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
