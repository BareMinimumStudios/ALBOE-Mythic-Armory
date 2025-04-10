package org.bareminimumstudios.mythicarmory.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.bareminimumstudios.mythicarmory.client.models.InvisibleModel;
import org.bareminimumstudios.mythicarmory.client.renderers.InvisibleRenderer;
import org.bareminimumstudios.mythicarmory.entity.NebulaVortexEntity;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class EntityRegistry {
    public static final EntityType<NebulaVortexEntity> NEBULA_VORTEX = register(
            "nebula_vortex",
            EntityType.Builder.create(NebulaVortexEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .setDimensions(0f, 0f)
                    .build(HelperMethods.identifierOf("nebula_vortex").toString())
    );




    public static void register() {
        FabricDefaultAttributeRegistry.register(NEBULA_VORTEX, NebulaVortexEntity.createAttributes());
    }

    public static <T extends Entity> EntityType<T> register(String id, EntityType<T> entity) {
        Registry.register(Registries.ENTITY_TYPE, HelperMethods.identifierOf(id), entity);
        return entity;
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(NEBULA_VORTEX, InvisibleRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(InvisibleModel.INVISIBLE, InvisibleModel::getTexturedModelData);
    }
}
