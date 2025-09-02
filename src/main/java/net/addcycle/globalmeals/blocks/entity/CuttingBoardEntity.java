package net.addcycle.globalmeals.blocks.entity;

import net.addcycle.globalmeals.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardEntity extends BlockEntity implements Inventory {
    private final SimpleInventory inventory = new SimpleInventory(1);

    public CuttingBoardEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUTTING_BOARD_ENTITY, pos, state);
    }

    @Override
    public int size() {
        return 1; // only one item cut at a time
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        if (!isEmpty()) {
            return inventory.getStack(0);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (inventory.isEmpty()) return ItemStack.EMPTY;
        return inventory.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return inventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.setStack(slot, stack);
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    // --------- syncing to clients & renderers client-side only -------------

    private DefaultedList<ItemStack> getStacks() {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.size(); i++) {
            list.set(i, inventory.getStack(i));
        }
        return list;
    }

    private void setStacks(DefaultedList<ItemStack> list) {
        for (int i = 0; i < inventory.size(); i++) {
            inventory.setStack(i, list.get(i));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, getStacks());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, list);
        setStacks(list);
    }

    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
