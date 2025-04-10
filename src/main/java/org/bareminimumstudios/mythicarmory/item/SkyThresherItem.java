package org.bareminimumstudios.mythicarmory.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.particle.Particles;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.effect.TimerEffect;
import org.bareminimumstudios.mythicarmory.entity.NebulaVortexEntity;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.EntityRegistry;
import org.bareminimumstudios.mythicarmory.registry.ParticleRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;
import org.bareminimumstudios.mythicarmory.util.Styles;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SkyThresherItem extends DivineSwordItem {

    public SkyThresherItem(int attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getWorld().isClient() || !(attacker instanceof PlayerEntity playerAttacker)) return super.postHit(stack, target, attacker);

        if(attacker.getRandom().nextFloat() <= MythicArmoryMain.WEAPONS_CONFIG.mistral.chance()) {
            NebulaVortexEntity summon = new NebulaVortexEntity(EntityRegistry.NEBULA_VORTEX, attacker.getWorld());
            summon.setOwner(playerAttacker);
            summon.setPos(target.getX(), target.getY(), target.getZ());

            attacker.getWorld().spawnEntity(summon);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> attributes = super.getAttributeModifiers(stack, slot);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributes);


        builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(
                UUID.fromString("88aa9607-bed6-45bc-b361-83a24abe2ad6"),
                "Weapon Modifier",
                MythicArmoryMain.WEAPONS_CONFIG.zephyr.speedIncrease(),
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        ));

        return slot == EquipmentSlot.MAINHAND ? builder.build() : attributes;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity.isOnGround() && entity instanceof LivingEntity livingEntity && HelperMethods.isHolding(livingEntity, stack, true)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(
                    EffectRegistry.ZEPHYR_ENERGY,
                    2
            ));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip1").setStyle(Styles.DIVINE_EFFECT.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip2"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip3"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip4"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip5"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip6").setStyle(Styles.DIVINE_EFFECT.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip7"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip8"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip9").setStyle(Styles.DIVINE_EFFECT.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip10"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip11"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip12"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip13"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.sky_thresher.tooltip14"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
