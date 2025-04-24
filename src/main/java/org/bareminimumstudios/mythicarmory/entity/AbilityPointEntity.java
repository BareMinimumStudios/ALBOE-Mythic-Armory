package org.bareminimumstudios.mythicarmory.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class AbilityPointEntity extends PathAwareEntity {
    protected static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(AbilityPointEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public AbilityPointEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER, Optional.empty());
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return super.getHurtSound(source);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source != this.getDamageSources().genericKill()) return false;
        return super.damage(source, amount);
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    public PlayerEntity getOwner() {
        if(this.dataTracker.get(OWNER).isEmpty()) return null;
        return this.getWorld().getPlayerByUuid(this.dataTracker.get(OWNER).get());
    }

    public void setOwner(UUID value) {
        this.getDataTracker().set(OWNER, Optional.of(value), true);
    }

    public void setOwner(PlayerEntity value) {
        this.getDataTracker().set(OWNER, Optional.of(value.getUuid()), true);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if(nbt.contains("Owner")) {
            this.setOwner(nbt.getUuid("Owner"));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if(this.getOwner() != null) {
            nbt.putUuid("Owner", this.getOwner().getUuid());
        }
    }
}
