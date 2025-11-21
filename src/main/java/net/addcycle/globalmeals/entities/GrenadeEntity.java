package net.addcycle.globalmeals.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrenadeEntity extends Entity {
    private int bounces;
    private int maxBounces = 5;

    public GrenadeEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d vel = this.getVelocity().add(0, -0.04, 0);

        double incomingY = vel.y;

        this.move(MovementType.SELF, vel);

        // FIXME : also ignore the player
        for (Entity e : this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.1))) {
            if (e == this) continue; // ignore self

            this.explode();
            return;
        }

        if (this.isOnGround() && incomingY < -0.08) {
            bounces++;
            if (bounces >= maxBounces) {
                this.explode();
                return;
            }

            this.setVelocity(vel.x, -incomingY, vel.z);
        } else {
            this.setVelocity(vel);
        }
    }

    private void explode() {
        this.getWorld().createExplosion(
                this,
                this.getX(), this.getY(), this.getZ(),
                2.0f,      // explosion power
                World.ExplosionSourceType.MOB
        );
        this.discard();
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}