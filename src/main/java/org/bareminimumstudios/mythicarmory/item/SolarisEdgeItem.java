package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

import java.util.Objects;

public class SolarisEdgeItem extends DivineSwordItem {
    public static String formNbt = HelperMethods.identifierOf("form").toString();
    public static int ticksInSun = 0; // I'm gonna change this out for nbt stuffs later

    public SolarisEdgeItem(int attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, settings);
    }

    public static void setForm(ItemStack stack, Form form) {
        stack.getOrCreateNbt().putString(formNbt, form.getStringValue());
    }

    public static boolean isForm(ItemStack stack, Form form) {
        return Objects.equals(stack.getOrCreateNbt().getString(formNbt), form.getStringValue());
    }

    public static Form getForm(ItemStack stack) {
        return Form.get(stack.getOrCreateNbt().getString(formNbt));
    }

    public static void empower(World world, ItemStack stack) {
        setForm(stack, Form.EMPOWERED);

        // To add particle effect
    }

    public static void depower(World world, ItemStack stack) {
        setForm(stack, world.isDay() ? Form.DAY : Form.NIGHT);
        ticksInSun = 0;

        // To add particle effect
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // Swap form
        if(!isForm(stack, Form.EMPOWERED)) {
            setForm(stack, world.isDay() ? Form.DAY : Form.NIGHT);
        }

        // Tick empowerment
        if(selected && world.isDay() && world.isSkyVisible(entity.getBlockPos())) {
            ticksInSun = Math.min(ticksInSun+1, 400);

            if(isForm(stack, Form.DAY) && ticksInSun > 300) {
                empower(world, stack);
            }
        } else {
            ticksInSun = Math.max(ticksInSun-1, 0);
            if(ticksInSun <= 300) {
                depower(world, stack);
            }
        }
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
