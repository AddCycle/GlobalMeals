package net.addcycle.globalmeals.init;

import net.addcycle.globalmeals.GlobalMeals;
import net.addcycle.globalmeals.entities.GrenadeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<GrenadeEntity> GRENADE_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(GlobalMeals.MODID, "grenade"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GrenadeEntity::new).dimensions(EntityDimensions.fixed(1f,1f)).build());

    public static void registerEntities() {
        System.out.println("REGISTERING ENTITIES");
    }
}
