package net.addcycle.globalmeals.items;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent GLOWING_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .snack()
            .hunger(1)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2 * 20), 1.0f)
            .build();
}
