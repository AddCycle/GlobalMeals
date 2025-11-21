package net.addcycle.globalmeals.client;

import net.addcycle.globalmeals.client.blocks.render.CuttingBoardBlockEntityRenderer;
import net.addcycle.globalmeals.client.render.GrenadeRenderer;
import net.addcycle.globalmeals.init.ModBlockEntities;
import net.addcycle.globalmeals.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class GlobalMealsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.CUTTING_BOARD_ENTITY, CuttingBoardBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRENADE_ENTITY, GrenadeRenderer::new);
    }
}