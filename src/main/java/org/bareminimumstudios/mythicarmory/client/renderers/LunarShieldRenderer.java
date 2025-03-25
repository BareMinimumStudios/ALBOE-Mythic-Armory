package org.bareminimumstudios.mythicarmory.client.renderers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.render.CustomLayers;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.render.OrbitingEffectRenderer;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

import java.util.List;

public class LunarShieldRenderer implements CustomModelStatusEffect.Renderer {
    public static final Identifier modelId_base = HelperMethods.identifierOf("effect/lunar_shield");

    private static final RenderLayer BASE_RENDER_LAYER =
            RenderLayer.getItemEntityTranslucentCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

    public void renderEffect(int amplifier, LivingEntity livingEntity, float delta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        matrixStack.push();
        float time = livingEntity.getWorld().getTime() / 40f; // Half-wave / second
        float angle = livingEntity.getYaw() + 180;
        double verticalOffset = HelperMethods.sinInterpol(time, livingEntity.getHeight() / 2.0F, 0.15);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-angle));
        matrixStack.translate(0f, verticalOffset, -1f);
        matrixStack.scale(1, 1, 1);


        CustomModels.render(BASE_RENDER_LAYER, itemRenderer, modelId_base, matrixStack, vertexConsumers, light, livingEntity.getId());

        matrixStack.pop();
    }
}
