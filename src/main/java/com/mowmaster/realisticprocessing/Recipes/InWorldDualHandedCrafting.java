package com.mowmaster.realisticprocessing.Recipes;

import com.google.gson.JsonObject;
import com.mowmaster.realisticprocessing.Registry.DeferredRegisterItems;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;


public class InWorldDualHandedCrafting implements Recipe<Container>
{
    @ObjectHolder(MODID + ":dualhandedcrafting")
    public static RecipeSerializer<?> SERIALIZER = new Serializer();

    public static RecipeType<InWorldDualHandedCrafting> INWORLD_HAMMERING = RecipeType.register(MODID + ":dualhandedcrafting");

    private final String group;
    private final ResourceLocation id;
    @Nullable
    private final Ingredient blockTarget;
    private final Ingredient offhandTool;
    private final Ingredient mainhandTool;
    private final ItemStack resultBlock;
    //private final ItemStack offhandTool;

    /*public InWorldDualHandedCrafting(ResourceLocation id, String group, @Nullable Ingredient blockTarget, ItemStack resultBlock, ItemStack offhandTool)
    {
        this.group = group;
        this.id = id;
        this.blockTarget = blockTarget;
        this.resultBlock = resultBlock;
        this.offhandTool = offhandTool;
    }*/

    public InWorldDualHandedCrafting(ResourceLocation id, String group, @Nullable Ingredient blockTarget, @Nullable Ingredient mainhandTool, @Nullable Ingredient offhandTool, ItemStack resultBlock)
    {
        this.group = group;
        this.id = id;
        this.blockTarget = blockTarget;
        this.mainhandTool = mainhandTool;
        this.offhandTool = offhandTool;
        this.resultBlock = resultBlock;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static Collection<InWorldDualHandedCrafting> getAllRecipes(Level world)
    {
        return world.getRecipeManager().getAllRecipesFor(INWORLD_HAMMERING);
    }

    @Override
    public String getGroup()
    {
        return group;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> allIngredients = NonNullList.create();
        allIngredients.add(blockTarget != null ? blockTarget : Ingredient.EMPTY);
        allIngredients.add(mainhandTool != null ? mainhandTool : Ingredient.EMPTY);
        allIngredients.add(offhandTool != null ? offhandTool : Ingredient.EMPTY);
        return allIngredients;
    }

    @Override
    public boolean matches(Container inv, Level worldIn)
    {
        //TargetBlock
        //MainHand
        //OffHand
        if(inv.getContainerSize()<3)return false;
        return offhandTool.test(inv.getItem(2)) && mainhandTool.test(inv.getItem(1)) && blockTarget.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv)
    {
        return getResultItem().copy();
    }

    @Override
    public ItemStack getResultItem()
    {
        return resultBlock;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType()
    {
        return INWORLD_HAMMERING;
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(DeferredRegisterItems.HAMMER_WOOD.get());
    }

    public Ingredient getPattern()
    {
        return blockTarget != null ? blockTarget : Ingredient.EMPTY;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<InWorldDualHandedCrafting>
    {
        protected InWorldDualHandedCrafting createRecipe(ResourceLocation recipeId, String group, Ingredient blockTarget , Ingredient mainhandTool , Ingredient offhandTool, ItemStack result)
        {
            return new InWorldDualHandedCrafting(recipeId, group, blockTarget, mainhandTool, offhandTool, result);
        }

        @Override
        public InWorldDualHandedCrafting fromJson(ResourceLocation recipeId, JsonObject json)
        {
            String group = GsonHelper.getAsString(json, "group", "");
            Ingredient blockTarget = json.has("blockTarget") ? CraftingHelper.getIngredient(json.get("blockTarget")) : null;
            Ingredient mainhandTool = json.has("mainhandTool") ? CraftingHelper.getIngredient(json.get("mainhandTool")) : null;
            Ingredient offhandTool = json.has("offhandTool") ? CraftingHelper.getIngredient(json.get("offhandTool")) : null;
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
            return createRecipe(recipeId, group, blockTarget, mainhandTool, offhandTool, result);
        }

        @Override
        public InWorldDualHandedCrafting fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            String group = buffer.readUtf(32767);
            boolean hasInput = buffer.readBoolean();
            Ingredient blockTarget = hasInput ? Ingredient.fromNetwork(buffer) : null;
            boolean hasMainHandTool = buffer.readBoolean();
            Ingredient mainhandTool = hasMainHandTool ? Ingredient.fromNetwork(buffer) : null;
            boolean hasOffHandTool = buffer.readBoolean();
            Ingredient offhandTool = hasOffHandTool ? Ingredient.fromNetwork(buffer) : null;
            ItemStack result = buffer.readItem();
            return createRecipe(recipeId, group,  blockTarget, mainhandTool, offhandTool, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, InWorldDualHandedCrafting recipe)
        {
            buffer.writeUtf(recipe.group);
            boolean hasInput = recipe.blockTarget != null;
            buffer.writeBoolean(hasInput);
            if (hasInput) recipe.blockTarget.toNetwork(buffer);
            boolean hasMainHandTool = recipe.mainhandTool != null;
            buffer.writeBoolean(hasMainHandTool);
            if (hasMainHandTool) recipe.mainhandTool.toNetwork(buffer);
            boolean hasOffHandTool = recipe.offhandTool != null;
            buffer.writeBoolean(hasOffHandTool);
            if (hasOffHandTool) recipe.offhandTool.toNetwork(buffer);
            buffer.writeItem(recipe.resultBlock);
        }
    }
}
