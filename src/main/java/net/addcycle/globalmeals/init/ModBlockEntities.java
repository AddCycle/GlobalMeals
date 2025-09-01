package net.addcycle.globalmeals.init;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.blocks.entity.CuttingBoardEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<CuttingBoardEntity> CUTTING_BOARD_ENTITY =
            register("cutting_board_be", CuttingBoardEntity::new, ModBlocks.CUTTING_BOARD);

    public static void registerBlockEntities() {
        GlobalMeals.LOGGER.info("Registering block entities");
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> entityFactory, Block... blocks) {
        Identifier id = Identifier.of(GlobalMeals.MODID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(entityFactory, blocks).build());
    }
}
