package com.mowmaster.realisticprocessing.Registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.realisticprocessing.Utilities.References.MODID;


public class DeferredBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    /*
        public static final RegistryObject<BlockEntityType<BasePedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register(
                "block_entity_pedestal",
                () -> BlockEntityType.Builder.of(BasePedestalBlockEntity::new, DeferredRegisterTileBlocks.BLOCK_PEDESTAL.get()).build(null));
    */
    private DeferredBlockEntityTypes() {
    }
}
