package net.addcycle.globalmeals.blocks.custom;

import net.addcycle.globalmeals.blocks.entity.CuttingBoardEntity;
import net.addcycle.globalmeals.items.ItemKnife;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CuttingBoardBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = createCuboidShape(3, 0, 1, 13, 1, 15);

    public CuttingBoardBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CuttingBoardEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof CuttingBoardEntity) {
                // FIXME
                // ItemScatterer.spawn(world, pos, (CuttingBoardEntity)entity);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof CuttingBoardEntity cuttingBoard) {
                ItemStack held = player.getStackInHand(hand);

                // Place item
                if (!held.isEmpty() && cuttingBoard.getStack(0).isEmpty()) {
                    if (!held.isFood() && !(held.getItem() instanceof ItemKnife)) {
                        return ActionResult.FAIL;
                    }
                    ItemStack one = held.copy();
                    one.setCount(1);
                    held.decrement(1);

                    cuttingBoard.setStack(0, one); // calls markDirty() + sync
                    return ActionResult.SUCCESS;
                }

                // Take item
                ItemStack inside = cuttingBoard.getStack(0);
                if (!inside.isEmpty()) {
                    player.getInventory().offerOrDrop(inside.copy());
                    cuttingBoard.setStack(0, ItemStack.EMPTY); // calls markDirty() + sync
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null; // FIXME
    }
}
