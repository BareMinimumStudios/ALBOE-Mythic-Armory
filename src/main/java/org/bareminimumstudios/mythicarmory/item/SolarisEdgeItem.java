package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

import java.util.Objects;

public class SolarisEdgeItem extends DivineSwordItem {
    public static String formNbt = HelperMethods.identifierOf("form").toString();
    public int ticksInSun = 0;

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

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // Swap form
        if(world.isNight()) {
            setForm(stack, Form.NIGHT);
        } else {
            if(isForm(stack, Form.NIGHT)) {
                setForm(stack, Form.DAY);
            }
        }

        // Tick empowerment
        if(selected && world.isDay() && world.isSkyVisible(entity.getBlockPos())) {
            this.ticksInSun = Math.min(this.ticksInSun+1, 400);

            if(isForm(stack, Form.DAY) && this.ticksInSun > 300) {
                empower(world, stack);
            }
        } else {
            this.ticksInSun = Math.max(this.ticksInSun-1, 0);
            if(this.ticksInSun <= 300) {
                setForm(stack, world.isDay() ? Form.DAY : Form.NIGHT); // De-power
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
