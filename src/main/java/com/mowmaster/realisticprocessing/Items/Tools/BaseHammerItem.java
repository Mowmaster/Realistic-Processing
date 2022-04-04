package com.mowmaster.realisticprocessing.Items.Tools;

import com.google.common.collect.Sets;
import com.mowmaster.realisticprocessing.ModTab.RPTab;
import com.mowmaster.realisticprocessing.Registry.DeferredRegisterItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;


import javax.annotation.Nonnull;
import java.util.*;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;

public class BaseHammerItem extends DiggerItem {

    @Nonnull
    private static final Set<Block> effectiveBlocksOn = Sets
            .newHashSet(Blocks.GRASS,Blocks.TALL_GRASS,Blocks.WHEAT);
    
    public BaseHammerItem(@Nonnull final Tier tier, final int maxDamage) {
        super(0.5F, 0.5F, tier, BlockTags.create(new ResourceLocation(MODID,"mineable/hammer")),
                new Item.Properties().defaultDurability(maxDamage).tab(RPTab.TAB_ITEMS));
    }

    @Override
    public int getBurnTime(@Nonnull final ItemStack itemStack, @Nullable final RecipeType<?> recipeType) {
        if (itemStack.getItem() == DeferredRegisterItems.HAMMER_WOOD.get()) {
            return 200;
        }
        else return 0;
    }

}
