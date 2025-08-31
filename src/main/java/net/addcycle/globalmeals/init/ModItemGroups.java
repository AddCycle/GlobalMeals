package net.addcycle.globalmeals.init;

import net.addcycle.globalmeals.GlobalMeals;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> FOOD_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(GlobalMeals.MODID, "food_group"));
    public static final ItemGroup FOOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(GlobalMeals.MODID, "food_group"),
            new ItemGroup.Builder(ItemGroup.Row.TOP, 1)
                    .displayName(Text.translatable("itemGroup.globalmeals.food"))
                    .icon(() -> new ItemStack(ModItems.GLOWING_APPLE))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.GLOWING_APPLE);
                    })).build());

    public static void registerItemGroups() {}
}