package org.bareminimumstudios.mythicarmory.client.renderers;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.client.models.InvisibleModel;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class InvisibleRenderer<T extends MobEntity> extends MobEntityRenderer<T, InvisibleModel<T>> {
    public InvisibleRenderer(EntityRendererFactory.Context context) {
        super(context, new InvisibleModel<T>(context.getPart(InvisibleModel.INVISIBLE)), 0f);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        return TexturedModelData.of(modelData, 0, 0);
    }

    @Override
    public Identifier getTexture(T entity) {
        return HelperMethods.identifierOf("textures/entity/invisible.png");
    }
}
