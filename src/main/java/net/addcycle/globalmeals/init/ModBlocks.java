package net.addcycle.globalmeals.init;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.blocks.custom.CuttingBoardBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block APPLE_BALE = registerBlock(new Block(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)), "apple_bale", true);
    public static final Block CUTTING_BOARD = registerBlock(new CuttingBoardBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), "cutting_board", true);

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register((itemGroup) -> itemGroup.add(APPLE_BALE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register((itemGroup) -> itemGroup.add(CUTTING_BOARD));
    }

    public static Block registerBlock(Block block, String id, boolean shouldRegisterItem) {
        Identifier blockID = new Identifier(GlobalMeals.MODID, id);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, blockID, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockID, block);
    }
}
