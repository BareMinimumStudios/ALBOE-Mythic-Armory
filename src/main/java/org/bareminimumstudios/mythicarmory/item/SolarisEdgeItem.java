package org.bareminimumstudios.mythicarmory.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.spell_engine.particle.Particles;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;
import org.bareminimumstudios.mythicarmory.util.Styles;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SolarisEdgeItem extends DivineSwordItem {
    public static String formNbt = HelperMethods.identifierOf("form").toString();
    public static String sunStateNbt = HelperMethods.identifierOf("sun_state").toString();

    public SolarisEdgeItem(int attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> attributes = super.getAttributeModifiers(stack, slot);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributes);


        if(isForm(stack, Form.NIGHT)) {
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(
                    UUID.fromString("ae23de08-08af-4300-a85d-80021272b0ce"),
                    "Weapon Modifier",
                    MythicArmoryMain.WEAPONS_CONFIG.horizonShift.nightDealtDamageReduction() * -1,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            ));
        } else {
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(
                    UUID.fromString("efdef7aa-4c2e-437d-839f-29d660002c90"),
                    "Weapon Modifier",
                    MythicArmoryMain.WEAPONS_CONFIG.horizonShift.dayExtraDamage(),
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            ));
        }

        return slot == EquipmentSlot.MAINHAND ? builder.build() : attributes;
    }

    public static void setForm(World world, boolean serverOnly, ItemStack stack, Form form) {
        if(serverOnly && world.isClient()) return;

        // Do not set if the stack is already this form, or if running on the client.
        if(isForm(stack, form)) return;

        stack.getOrCreateNbt().putString(formNbt, form.getStringValue());
    }

    public static boolean isForm(ItemStack stack, Form form) {
        return Objects.equals(stack.getOrCreateNbt().getString(formNbt), form.getStringValue());
    }

    public static Form getForm(ItemStack stack) {
        return Form.get(stack.getOrCreateNbt().getString(formNbt));
    }

    public static void empower(World world, Entity entity, ItemStack stack) {
        setForm(world, true, stack, Form.EMPOWERED);

        ParticleHelper.spawnHorizontalBurst(
                world,
                Particles.flame_medium_b.particleType,
                entity.getX(), entity.getY(), entity.getZ(),
                0, 360,
                0.25f, 0.1f,
                1, 1);

        ParticleHelper.spawnHorizontalBurst(
                world,
                Particles.flame_medium_a.particleType,
                entity.getX(), entity.getY(), entity.getZ(),
                2.5f, 360,
                0.15f, 0.05f,
                1, 1);

        ParticleHelper.spawnHorizontalBurst(
                world,
                Particles.flame_medium_a.particleType,
                entity.getX(), entity.getY(), entity.getZ(),
                0, 360,
                0.1f, 0.01f,
                1, 1);
    }

    public static void depower(World world, Entity entity, ItemStack stack) {
        setForm(world, true, stack, world.isDay() ? Form.DAY : Form.NIGHT);
        stack.getOrCreateNbt().putInt(sunStateNbt, 0);

        // To add particle effect
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // Swap form
        if(!isForm(stack, Form.EMPOWERED)) {
            setForm(world, true, stack, world.isDay() ? Form.DAY : Form.NIGHT);
        }

        // Tick empowerment
        if(selected && world.isDay() && world.isSkyVisible(entity.getBlockPos())) {
            stack.getOrCreateNbt().putInt(sunStateNbt, Math.min(stack.getOrCreateNbt().getInt(sunStateNbt) +1, MythicArmoryMain.WEAPONS_CONFIG.solarOverload.ticksCanStore()));

            if(isForm(stack, Form.DAY) && stack.getOrCreateNbt().getInt(sunStateNbt) > MythicArmoryMain.WEAPONS_CONFIG.solarOverload.ticksToCharge()) {
                empower(world, entity, stack);
            }
        } else {
            stack.getOrCreateNbt().putInt(sunStateNbt, Math.max(stack.getOrCreateNbt().getInt(sunStateNbt)-1, 0));
            if(isForm(stack, Form.EMPOWERED)) {
                if (stack.getOrCreateNbt().getInt(sunStateNbt) <= MythicArmoryMain.WEAPONS_CONFIG.solarOverload.ticksToCharge()) {
                    depower(world, entity, stack);
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip1").setStyle(Styles.DIVINE_EFFECT.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip2",
                HelperMethods.decimalToPercentage(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.dayExtraDamage())));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip3",
                String.valueOf(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.dayRegenAmount()),
                HelperMethods.ticksToSeconds(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.regenInterval()),
                HelperMethods.decimalToPercentage(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.nightDealtDamageReduction())));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip4"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip5",
                HelperMethods.decimalToPercentage(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.nightTakenDamageReduction()),
                String.valueOf(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.nightRegenAmount()),
                HelperMethods.ticksToSeconds(MythicArmoryMain.WEAPONS_CONFIG.horizonShift.regenInterval())));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip6").setStyle(Styles.DIVINE_EFFECT.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip7"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip8"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip9"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip10"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip11"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip12"));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip13"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip14").setStyle(Styles.DIVINE_EFFECT.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip15",
                HelperMethods.ticksToSeconds(MythicArmoryMain.WEAPONS_CONFIG.solarOverload.ticksToCharge())));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.solaris_edge.tooltip16"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    public enum Form {
        NIGHT("night"),
        DAY("day"),
        EMPOWERED("empowered");

        private final String stringValue;

        Form(String stringValue) {
            this.stringValue = stringValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public static Form get(String stringValue) {
            for(Form form : Form.values()) {
                if(form.getStringValue().equals(stringValue)) {
                    return form;
                }
            }

            return null;
        }
    }
}
