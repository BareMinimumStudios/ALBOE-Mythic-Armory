package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

import java.util.Objects;

public class SolarisEdgeItem extends DivineSwordItem {
    public static Identifier formNbt = HelperMethods.identifierOf("form");
    public int ticksInSun = 0;

    public SolarisEdgeItem(int attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, settings);
    }

    public void setForm(ItemStack stack, Form form) {
        stack.getOrCreateNbt().putString(formNbt.toString(), form.getStringValue());
    }

    public boolean isForm(ItemStack stack, Form form) {
        return Objects.equals(stack.getOrCreateNbt().getString(formNbt.toString()), form.getStringValue());
    }

    public void empower(World world, ItemStack stack) {
        this.setForm(stack, Form.EMPOWERED);

        // To add particle effect
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(world.isClient) {
            return;
        }

        // Swap form
        if(world.isNight()) {
            this.setForm(stack, Form.NIGHT);
        } else {
            if(this.isForm(stack, Form.NIGHT)) {
                this.setForm(stack, Form.DAY);
            }
        }

        // Tick empowerment
        if(selected && world.isDay() && world.isSkyVisible(entity.getBlockPos())) {
            this.ticksInSun = Math.min(this.ticksInSun+1, 400);

            if(this.isForm(stack, Form.DAY) && this.ticksInSun > 300) {
                this.empower(world, stack); // Transform
            }
        } else {
            this.ticksInSun = Math.max(this.ticksInSun-1, 0);
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
    }
}
