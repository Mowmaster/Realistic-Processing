package com.mowmaster.rprocessing.recipes.BloomeryRecipes;

import net.minecraft.item.ItemStack;

public class BloomeryHashMap
{
    public ItemStack inputOre;
    public Boolean isAllowed;

    public BloomeryHashMap(ItemStack getOre, Boolean canAllow)
    {
        this.inputOre=getOre;
        this.isAllowed=canAllow;
    }

    public ItemStack getInput()
    {
        return inputOre;
    }

    public Boolean getOutput()
    {
        return isAllowed;
    }

    @Override
    public String toString() {
        return "EffectHashMap [input=" + inputOre + ", output=" + isAllowed + "]";
    }
}
