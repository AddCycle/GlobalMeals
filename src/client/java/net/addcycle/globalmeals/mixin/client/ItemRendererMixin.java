package net.addcycle.globalmeals.mixin.client;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.init.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @SuppressWarnings("AmbiguousMixinReference")
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useGlintAppleModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.GLOWING_APPLE) && renderMode == ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).fixme$getModels().getModelManager().getModel(new ModelIdentifier(GlobalMeals.MODID, "glowing_apple_gui", "inventory"));
        }
        return value;
    }
}
