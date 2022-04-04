package com.mowmaster.realisticprocessing.Loot;

import com.google.gson.JsonObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class StrawLootModifier extends LootModifier
{
    private final Item addedItem;

    /**
     * This loot modifier adds an item to the loot table, given the conditions specified.
     */
    protected StrawLootModifier(LootItemCondition[] conditionsIn, Item addedItemIn) {
        super(conditionsIn);
        this.addedItem = addedItemIn;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(addedItem));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<StrawLootModifier>
    {
        @Override
        public StrawLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
            return new StrawLootModifier(ailootcondition, addedItem);
        }

        @Override
        public JsonObject write(StrawLootModifier instance) {
            return new JsonObject();
        }
    }
}
