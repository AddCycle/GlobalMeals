package net.addcycle.globalmeals.init;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.items.ModFoodComponents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {

    public static Item registerItem(Item item, String id) {
        Identifier itemID = new Identifier(GlobalMeals.MODID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item GLOWING_APPLE = registerItem(new Item(new Item.Settings()
            .food(ModFoodComponents.GLOWING_FOOD_COMPONENT)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("itemTooltip.globalmeals.glowing_apple").formatted(Formatting.YELLOW));
        }
    }, "glowing_apple");

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.GLOWING_APPLE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH)
                .register((itemGroup) -> itemGroup.add(ModItems.GLOWING_APPLE));
    }
}