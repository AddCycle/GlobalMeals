package net.addcycle.globalmeals.blocks.entity;

import net.addcycle.globalmeals.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CuttingBoardEntity extends BlockEntity {

    public CuttingBoardEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUTTING_BOARD_ENTITY, pos, state);
    }
}
