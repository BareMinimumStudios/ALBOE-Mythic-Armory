package org.bareminimumstudios.mythicarmory.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.ParticleRegistry;
import org.bareminimumstudios.mythicarmory.registry.SoundRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;

import java.util.OptionalInt;

public class NexusEntity extends AbilityPointEntity {

    protected static final TrackedData<OptionalInt> SIZE = DataTracker.registerData(NexusEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);
    protected static final TrackedData<Boolean> LAUNCHED = DataTracker.registerData(NexusEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public NexusEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SIZE, OptionalInt.of(20));
        this.dataTracker.startTracking(LAUNCHED, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if(nbt.contains("Size")) {
            this.setSize(nbt.getInt("Size"));
        }

        if(nbt.contains("Launched")) {
            this.setLaunched(nbt.getBoolean("Launched"));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("Size", this.getSize());
        nbt.putBoolean("Launched", this.hasLaunched());
    }

    public boolean hasLaunched() {
        return this.dataTracker.get(LAUNCHED);
    }

    public void setLaunched(boolean launched) {
        this.dataTracker.set(LAUNCHED, launched);
    }

    public void setSize(int size) {
        this.dataTracker.set(SIZE, OptionalInt.of(size));
    }

    public int getSize() {
        if(this.dataTracker.get(SIZE).isPresent()) {
            return this.dataTracker.get(SIZE).getAsInt();
        }

        return 0;
    }

    @Override
    public boolean updateMovementInFluid(TagKey<Fluid> tag, double speed) {
        return false;
    }

    ///  Taken from the vanilla <code>entity::isInsideWall</code>, with the no-clip check removed
    public boolean isInBlock() {
        float f = this.getDimensions(getPose()).width * 0.8F;
        Box box = Box.of(this.getEyePos(), f, 1.0E-6, f);
        return BlockPos.stream(box).anyMatch((pos) -> {
            BlockState blockState = this.getWorld().getBlockState(pos);
            return !blockState.isAir()
                    && blockState.shouldSuffocate(this.getWorld(), pos)
                    && VoxelShapes.matchesAnywhere(blockState.getCollisionShape(this.getWorld(), pos)
                    .offset(pos.getX(), pos.getY(), pos.getZ()), VoxelShapes.cuboid(box), BooleanBiFunction.AND);
        });
    }

    @Override
    public void tick() {
        super.tick();
        float sizePercentage = this.getSize() / 50f;

        // Particles
        ParticleHelper.spawnHorizontalBurst(this.getWorld(), ParticleRegistry.SPHERICAL_NEBULA,
                this.getX(), this.getY(), this.getZ(),
                0, 360, 2.4 * sizePercentage, 1, 30, 0
        );

        ParticleHelper.spawnHorizontalBurst(this.getWorld(), ParticleRegistry.SPHERICAL_NEBULA,
                this.getX(), this.getY(), this.getZ(),
                0, 360, 2.4 * sizePercentage, -1, 30, 0
        );

        ParticleHelper.spawnHorizontalBurst(this.getWorld(), ParticleRegistry.SPHERICAL_NEBULA,
                this.getX(), this.getY(), this.getZ(),
                0, 360,
                4.5 * sizePercentage, this.getRandom().nextFloat() - 0.5f,
                120, 0
        );

        ParticleHelper.spawnHorizontalBurst(this.getWorld(), ParticleRegistry.SPHERICAL_NEBULA,
                this.getX(), this.getY(), this.getZ(),
                0, 360, 5f * sizePercentage, 0, 30, 0
        );

        // Charging and Launching
        if(this.hasLaunched()) {

            // Sounds
            if(this.age % 40 == 5) {
                this.getWorld().playSoundFromEntity(null, this, SoundRegistry.NEBULA_STORM, this.getSoundCategory(), 1, 1);
            }

            // Grab
            double length = sizePercentage * 4.5f;
            Box box = new Box(
                    this.getX() - length,
                    this.getY() - length,
                    this.getZ() - length,
                    this.getX() + length,
                    this.getY() + length,
                    this.getZ() + length
            );

            for (LivingEntity target : this.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
                if (target == this) continue;
                if (this.getOwner() != null && (target.isTeammate(this.getOwner()) || target == this.getOwner()))
                    continue;

                if(target.age % 20 == 0) {
                    target.damage(this.getMagicDamageSource(),
                            MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.trappedDamage()
                                    * sizePercentage
                                    * HelperMethods.getScale(this.getOwner(), 0.15f, SpellSchools.ARCANE));
                }

                target.setVelocity(this.getVelocity().multiply(1.2d).add(new Vec3d(0, 0.1d, 0)));
                target.velocityModified = true;
            }

            if (this.age >= MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.duration() - 20) {
                // Explode
                explode(this.age >= MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.duration());

                // Play sound on first exploding frame
                if(this.age == MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.duration() - 20) {
                    this.getWorld().playSoundAtBlockCenter(this.getBlockPos(), SoundRegistry.NEBULA_EXPLOSION, this.getSoundCategory(), 1f, 1, true);
                }

            } else {
                if (this.isInBlock()) {
                    this.age = Math.max(age, MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.duration() - 20);
                }
            }
        } else {
            // Charge
            PlayerEntity owner = this.getOwner();
            if(owner != null) {
                // Look offset of the orb
                double pitchR = Math.toRadians(-owner.getPitch());
                double yawR = Math.toRadians(owner.getYaw() + 90);
                Vec3d normalisedOffset = new Vec3d(
                        Math.cos(pitchR) * Math.cos(yawR),
                        Math.sin(pitchR),
                        Math.cos(pitchR) * Math.sin(yawR)
                );

                if (owner.hasStatusEffect(EffectRegistry.NEXUS_STORM)) {
                    this.setSize(Math.min(this.getSize() + 1, 50));
                    Vec3d newPos = new Vec3d(owner.getX(), owner.getEyeY(), owner.getZ());

                    newPos = newPos.add(normalisedOffset.multiply(2 + (this.getSize() / 10f)));
                    this.setPos(newPos.getX(), newPos.getY(), newPos.getZ());
                } else {
                    this.setLaunched(true);
                    this.getWorld().playSoundAtBlockCenter(this.getBlockPos(), SoundRegistry.NEBULA_STORM, this.getSoundCategory(), 1f, 1, true);
                    this.setVelocity(normalisedOffset.multiply(MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.speed()));
                }
            }
        }
    }

    public DamageSource getMagicDamageSource() {
        if(this.getOwner() == null) return this.getDamageSources().magic();

        return this.getOwner().getDamageSources().magic();
    }

    @Override
    public boolean hasNoDrag() {
        return true;
    }

    public void explode(boolean isFinal) {
        this.setVelocity(new Vec3d(0, 0, 0));

        // Particles
        if(this.getWorld() instanceof ServerWorld world) {
            for(ServerPlayerEntity player : PlayerLookup.tracking(this)) {
                world.spawnParticles(player, ParticleTypes.END_ROD, true,
                        this.getX(), this.getY(), this.getZ(),
                        100, 0, 0, 0, 1);


                world.spawnParticles(player, ParticleRegistry.SHORT_NEBULA, true,
                        this.getX(), this.getY(), this.getZ(),
                        40, 0, 0, 0, 1);
            }
        }

        // Damage
        float sizePercentage = this.getSize() / 50f;
        double length = sizePercentage * 6f;
        Box box = new Box(
                this.getX() - length,
                this.getY() - length,
                this.getZ() - length,
                this.getX() + length,
                this.getY() + length,
                this.getZ() + length
        );

        for (LivingEntity target : this.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
            if (target == this) continue;
            if (this.getOwner() != null && (target.isTeammate(this.getOwner()) || target == this.getOwner()))
                continue;

            target.damage(this.getMagicDamageSource(),
                    MythicArmoryMain.WEAPONS_CONFIG.nebulaStorm.explosionDamage()
                            * sizePercentage
                            * HelperMethods.getScale(this.getOwner(), 0.15f, SpellSchools.ARCANE));

            target.takeKnockback(2.5, this.getX() + this.getRandom().nextFloat() - 0.5, this.getZ() + this.getRandom().nextFloat() - 0.5);
        }

        if(isFinal) {
            this.discard();
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
    }
}
