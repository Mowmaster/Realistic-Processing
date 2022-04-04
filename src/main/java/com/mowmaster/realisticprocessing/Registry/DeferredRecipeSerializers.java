package com.mowmaster.realisticprocessing.Registry;

import com.mowmaster.realisticprocessing.Recipes.InWorldDualHandedCrafting;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;

public final class DeferredRecipeSerializers
{
    @Nonnull
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static final RegistryObject<RecipeSerializer<?>> HAMMERING = RECIPES.register(MODID + "_dualhandedcrafting", () ->
            InWorldDualHandedCrafting.SERIALIZER);

}
