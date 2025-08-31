package net.addcycle.globalmeals.init;

import net.addcycle.globalmeals.GlobalMeals;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block registerBlock(Block block, String id, boolean shouldRegisterItem) {
        Identifier blockID = new Identifier(GlobalMeals.MODID, id);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, blockID, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockID, block);
    }

    public static final Block APPLE_BALE = registerBlock(new Block(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)), "apple_bale", true);

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.FOOD_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(APPLE_BALE.asItem());
        });
    }
}
