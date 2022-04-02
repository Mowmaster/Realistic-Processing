package com.mowmaster.realisticprocessing.Registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;

public final class DeferredRecipeSerializers
{

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
/*
    public static final RegistryObject<RecipeSerializer<?>> COBBLEGEN = RECIPES.register(MODID + "_cobblegen", () ->
            CobbleGenRecipe.SERIALIZER);
    */

}
