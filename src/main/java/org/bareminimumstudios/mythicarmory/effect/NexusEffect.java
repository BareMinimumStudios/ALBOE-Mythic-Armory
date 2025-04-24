package org.bareminimumstudios.mythicarmory.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.bareminimumstudios.mythicarmory.entity.NexusEntity;
import org.bareminimumstudios.mythicarmory.registry.EntityRegistry;

public class NexusEffect extends StatusEffect {
    public NexusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        if(!(entity instanceof PlayerEntity player)) return;

        Box box = new Box(
                entity.getX() + 50,
                entity.getY() + 50,
                entity.getZ() + 50,
                entity.getX() - 50,
                entity.getY() - 50,
                entity.getZ() - 50
        );

        if(entity.getWorld().getNonSpectatingEntities(NexusEntity.class, box)
                .stream().noneMatch(nexus -> nexus.getOwner() == entity && !nexus.hasLaunched())) {

            NexusEntity nexusEntity = new NexusEntity(EntityRegistry.NEBULA_NEXUS, entity.getWorld());
            nexusEntity.setPos(entity.getX(), entity.getY(), entity.getZ());
            nexusEntity.setSize(1);
            nexusEntity.setOwner(player);

            entity.getWorld().spawnEntity(nexusEntity);
        }

        // Spell engine can only give an effect for a minimum of 1 second, we shave off 16 ticks (.8s) here
//        if(entity.getStatusEffect(this).getDuration() <= 16) entity.removeStatusEffect(this);
    }
}
