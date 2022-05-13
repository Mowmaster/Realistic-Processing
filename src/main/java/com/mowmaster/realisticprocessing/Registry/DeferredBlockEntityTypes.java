package com.mowmaster.realisticprocessing.Registry;

import com.mowmaster.realisticprocessing.Blocks.Basins.BaseBasinBlockEntity;
import com.mowmaster.realisticprocessing.Blocks.Basins.Wood.BasinWoodBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;


public class DeferredBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, MODID);

        public static final RegistryObject<BlockEntityType<BasinWoodBlockEntity>> BASIN_WOOD = BLOCK_ENTITIES.register(
                "block_entity_basin_wood",
                () -> BlockEntityType.Builder.of(BasinWoodBlockEntity::new, DeferredRegisterTileBlocks.BLOCK_BASIN_WOOD.get()).build(null));

    private DeferredBlockEntityTypes() {
    }
}
