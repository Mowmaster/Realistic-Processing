package com.mowmaster.realisticprocessing.Events;

import com.mowmaster.realisticprocessing.Recipes.InWorldDualHandedCrafting;
import com.mowmaster.realisticprocessing.Utilities.ContainerUtils;
import com.mowmaster.realisticprocessing.Utilities.RPItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber
public class DualHandedCraftingHandler
{
    @SubscribeEvent()
    public static void hammerCrafting(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getPlayer();

        if(!level.isClientSide)
        {
            if(player.getMainHandItem() != null && player.getOffhandItem() != null)
            {
                ItemStack blockTarget = new ItemStack(state.getBlock().asItem());
                if(blockTarget != null)
                {
                    InWorldDualHandedCrafting getRecipe = getRecipe(level,blockTarget,player.getMainHandItem(),player.getOffhandItem());
                    if(getRecipe != null)
                    {
                        ItemStack getResultItem = getBlockItemResult(getRecipe).stream().findFirst().get();
                        if(getResultItem != null)
                        {
                            if(Block.byItem(getResultItem.getItem()) != null && Block.byItem(getResultItem.getItem()) != Blocks.AIR)
                            {
                                level.setBlockAndUpdate(pos,Block.byItem(getResultItem.getItem()).defaultBlockState());
                                //level.setBlockAndUpdate(pos,Blocks.BEDROCK.defaultBlockState());
                            }
                            else
                            {
                                level.setBlockAndUpdate(pos,Blocks.AIR.defaultBlockState());
                                RPItemUtils.spawnItemStack(level, pos.getX(), pos.getY(), pos.getZ(), getResultItem);
                            }
                        }
                    }
                }
                /*if(player.getMainHandItem().getItem() instanceof BaseHammerItem)
                {

                }*/
            }
        }
    }



    @Nullable
    protected static InWorldDualHandedCrafting getRecipe(Level level, ItemStack targetBlockItem, ItemStack mainHandItem, ItemStack offHandItem) {
        Container cont = ContainerUtils.getContainer(3);
        cont.setItem(-1,targetBlockItem);
        cont.setItem(-1,mainHandItem);
        cont.setItem(-1,offHandItem);
        List<InWorldDualHandedCrafting> recipes = level.getRecipeManager().getRecipesFor(InWorldDualHandedCrafting.INWORLD_HAMMERING,cont,level);
        return recipes.size() > 0 ? level.getRecipeManager().getRecipesFor(InWorldDualHandedCrafting.INWORLD_HAMMERING,cont,level).get(0) : null;
    }

    protected static Collection<ItemStack> getBlockItemResult(InWorldDualHandedCrafting recipe) {
        return (recipe == null)?(Arrays.asList(ItemStack.EMPTY)):(Collections.singleton(recipe.getResultItem()));
    }

    /*
@Mod.EventBusSubscriber
public class DualHandedCraftingHandler
{
    @SubscribeEvent()
    public static void hammerCrafting(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getPlayer();

        if(!level.isClientSide)
        {
            if(player.getMainHandItem() != null)
            {
                if(player.getMainHandItem().getItem() instanceof BaseHammerItem)
                {
                    ItemStack blockTarget = new ItemStack(state.getBlock().asItem());
                    if(blockTarget != null)
                    {
                        TagKey<Item> LOGS = ItemTags.LOGS;
                        if(blockTarget.is(LOGS) && player.getOffhandItem().getItem() instanceof AxeItem)
                        {
                            level.setBlock(pos, DeferredRegisterBlocks.BASIN_WOOD.get().defaultBlockState(),2);
                        }
                    }
                }
            }
        }
    }
}
     */
}
