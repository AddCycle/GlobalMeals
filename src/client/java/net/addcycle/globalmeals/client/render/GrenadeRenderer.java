package net.addcycle.globalmeals.client.render;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.entities.GrenadeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class GrenadeRenderer extends EntityRenderer<GrenadeEntity> {
    private static final Identifier TEXTURE = new Identifier(GlobalMeals.MODID, "textures/entity/diamond.png");
    private final ItemRenderer itemRenderer;

    public GrenadeRenderer(EntityRendererFactory.Context context) {
        super(context);
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(GrenadeEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GrenadeEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vcp, int light) {

        matrices.push();

        matrices.scale(0.5f, 0.5f, 0.5f); // size
        matrices.translate(0, 0.25, 0);

        itemRenderer.renderItem(
                new ItemStack(Items.DIAMOND),
                ModelTransformationMode.GROUND,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vcp,
                entity.getWorld(),
                0
        );

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vcp, light);
    }
}
