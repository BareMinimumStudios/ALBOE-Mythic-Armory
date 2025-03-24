package org.bareminimumstudios.mythicarmory.client.gui;

import io.wispforest.owo.config.ui.ConfigScreen;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.jetbrains.annotations.NotNull;

public class WrapperConfigScreen extends BaseOwoScreen<FlowLayout> {
    public final Screen previous;

    public WrapperConfigScreen(Screen previous) {
        this.previous = previous;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        if(client == null || client.world == null) {
            rootComponent.surface(Surface.BLANK);
        }

        rootComponent.root();

        rootComponent.child(
                Components.label(Text.translatable("text.config.alboe_mythicarmory.title"))
                        .positioning(Positioning.relative(50, 10))
        );

        rootComponent.child(
                Components.button(
                        Text.translatable("text.config.alboe_mythicarmory.button.weapons"),
                        button -> {
                            if(client != null) {
                                client.setScreen(ConfigScreen.create(MythicArmoryMain.WEAPONS_CONFIG, this));
                            }
                        }
                ).sizing(Sizing.fixed(100), Sizing.fixed(25)).margins(Insets.of(3))
        );

        rootComponent.child(
                Components.button(
                        Text.translatable("text.config.alboe_mythicarmory.button.loot"),
                        button -> {
                            if(client != null) {
                                client.setScreen(ConfigScreen.create(MythicArmoryMain.LOOT_CONFIG, this));
                            }
                        }
                ).sizing(Sizing.fixed(100), Sizing.fixed(25)).margins(Insets.of(3))
        );


        // Footer
        rootComponent.child(
                Containers.horizontalFlow(Sizing.content(), Sizing.content())
                        .child(Components.button(
                                Text.translatable("text.config.alboe_mythicarmory.button.done"),
                                button -> {
                                    if(client != null) {
                                        client.setScreen(previous);
                                    }
                                }).sizing(Sizing.fixed(50), Sizing.fixed(20))
                                .margins(Insets.of(3)))
                        .child(Components.button(
                                Text.translatable("text.config.alboe_mythicarmory.button.close"),
                                button -> {
                                    if(client != null) {
                                        client.setScreen(null);
                                    }
                                }).sizing(Sizing.fixed(50), Sizing.fixed(20))
                                .margins(Insets.of(3)))
                        .positioning(Positioning.relative(50, 90))
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }
}
