package net.addcycle.globalmeals.client;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.init.ModBlocks;
import net.addcycle.globalmeals.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class GlobalMealsDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TagGenerator::new);
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(LangGenerator::new);
        pack.addProvider(RecipeGenerator::new);
        pack.addProvider(BlockLootTableGenerator::new);
        pack.addProvider(AdvancementGenerator::new);
    }

    private static class TagGenerator extends FabricTagProvider.ItemTagProvider {
        private static final TagKey<Item> SMELLY_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(GlobalMeals.MODID, "smelly_items"));

        public TagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(SMELLY_ITEMS)
                    .add(Items.SLIME_BALL)
                    .add(Items.ROTTEN_FLESH)
                    .addOptionalTag(ItemTags.DIRT);
        }
    }

    private static class ModelGenerator extends FabricModelProvider {

        public ModelGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.APPLE_BALE);
            blockStateModelGenerator.registerSimpleState(ModBlocks.CUTTING_BOARD);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(ModItems.GLOWING_APPLE, Models.GENERATED);
            registerTwoLayeredItem(itemModelGenerator, ModItems.GLOWING_APPLE);
            itemModelGenerator.register(ModItems.IRON_KNIFE, Models.HANDHELD);
        }

        private void registerTwoLayeredItem(ItemModelGenerator itemModelGenerator, @SuppressWarnings("SameParameterValue") Item item) {
            String itemName = Registries.ITEM.getId(item).getPath();
            Identifier id = new Identifier(GlobalMeals.MODID, "item/" + itemName + "_gui");
            Identifier layer0 = new Identifier(GlobalMeals.MODID, "item/" + itemName);
            Identifier layer1 = new Identifier(GlobalMeals.MODID, "item/layers/glint");
            Models.GENERATED_TWO_LAYERS.upload(id, TextureMap.layered(layer0, layer1), itemModelGenerator.writer);
        }
    }

    private static class LangGenerator extends FabricLanguageProvider {
        protected LangGenerator(FabricDataOutput dataOutput) {
            super(dataOutput, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add("item.globalmeals.glowing_apple", "Glowing Apple");
            translationBuilder.add("item.globalmeals.iron_knife", "Iron Knife");

            translationBuilder.add("block.globalmeals.apple_bale", "Apple Bale");
            translationBuilder.add("block.globalmeals.cutting_board", "Cutting Board");

            translationBuilder.add("itemTooltip.globalmeals.glowing_apple", "I see you...");
            translationBuilder.add("itemGroup.globalmeals.food", "Additional Foodstuff");
            translationBuilder.add("itemGroup.globalmeals.tools", "Kitchen Utensils");

            translationBuilder.add("advancement.globalmeals.glowing_apple.title", "Moving Glowstone");
            translationBuilder.add("advancement.globalmeals.glowing_apple.description", "Find or craft a Glowing Apple");
            translationBuilder.add("advancement.globalmeals.got_knife.title", "Got yourself a great tool don't ya ?!");
            translationBuilder.add("advancement.globalmeals.got_knife.description", "Find or craft an Iron Knife");
        }
    }

    private static class RecipeGenerator extends FabricRecipeProvider {
        public RecipeGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.IRON_KNIFE, 1)
                    .pattern("  i")
                    .pattern(" i ")
                    .pattern("s  ")
                    .input('i', Items.IRON_INGOT)
                    .input('s', Items.STICK)
                    .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                    .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                    .offerTo(exporter);
            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GLOWING_APPLE, 1)
                    .pattern("ggg")
                    .pattern("gag")
                    .pattern("ggg")
                    .input('g', Items.GLOWSTONE_DUST)
                    .input('a', Items.APPLE)
                    .criterion(hasItem(Items.APPLE), conditionsFromItem(Items.APPLE))
                    .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                    .offerTo(exporter);
            offerReversibleCompactingRecipes(exporter, RecipeCategory.FOOD, Items.APPLE, RecipeCategory.FOOD, ModBlocks.APPLE_BALE);
            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModBlocks.CUTTING_BOARD, 1)
                    .pattern("sp")
                    .input('s', Items.STICK)
                    .input('p', ItemTags.PLANKS)
                    .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                    .criterion(hasTag(ItemTags.PLANKS), conditionsFromTag(ItemTags.PLANKS))
                    .offerTo(exporter);
        }

        @SuppressWarnings("SameParameterValue")
        private static String hasTag(TagKey<Item> key) {
            return "has_" + key.toString();
        }
    }

    private static class BlockLootTableGenerator extends FabricBlockLootTableProvider {
        protected BlockLootTableGenerator(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.APPLE_BALE);
            addDrop(ModBlocks.CUTTING_BOARD);
        }
    }

    private static class AdvancementGenerator extends FabricAdvancementProvider {

        protected AdvancementGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
            Advancement getGlowingApple = Advancement.Builder.create()
                    .display(
                            ModItems.GLOWING_APPLE,
                            Text.translatable("advancement.globalmeals.glowing_apple.title"),
                            Text.translatable("advancement.globalmeals.glowing_apple.description"),
                            Identifier.of("minecraft", "textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("got_glowing_apple", InventoryChangedCriterion.Conditions.items(ModItems.GLOWING_APPLE))
                    .build(consumer, GlobalMeals.MODID + "/glowing_apple");

            @SuppressWarnings("unused")
            Advancement craftOrFindKnife = Advancement.Builder.create()
                    .parent(getGlowingApple)
                    .display(
                            ModItems.IRON_KNIFE,
                            Text.translatable("advancement.globalmeals.got_knife.title"),
                            Text.translatable("advancement.globalmeals.got_knife.description"),
                            null,
                            AdvancementFrame.GOAL,
                            true,
                            true,
                            false
                    )
                    .criterion("got_knife", InventoryChangedCriterion.Conditions.items(ModItems.IRON_KNIFE))
                    .build(consumer, GlobalMeals.MODID + "/got_knife");
        }
    }
}
