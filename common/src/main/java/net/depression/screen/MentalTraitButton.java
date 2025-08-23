package net.depression.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.depression.Depression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MentalTraitButton extends ImageButton {
    public static final ResourceLocation FRAME_LOCATION = ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "textures/mental_trait/frame_16.png");
    public static final ResourceLocation WIDGETS_LOCATION = ResourceLocation.withDefaultNamespace("widget/button");

    private final MentalTraitSelectionScreen screen;
    private final int baseY;
    private String id;
    private final int height;
    public final int dis;
    public final int disFrame;
    private final int charHalfSize = 4;
    private final int charCenterOffset;
    private final ResourceLocation iconTexture;

    public MentalTraitButton(MentalTraitSelectionScreen screen, int baseY, String id) {
        super(0, 0, screen.screenHeight - 8, screen.screenHeight / 4,
                createWidgetSprites(id),
                (button) -> ((MentalTraitButton) button).onPressButton(),
                Component.translatable("depression.mental_trait." + id));

        this.baseY = baseY;
        this.screen = screen;
        this.width = screen.screenHeight - 8;
        this.height = screen.screenHeight / 4;
        this.dis = (height - 16) / 2;
        this.disFrame = (height - 24) / 2;
        this.id = id;
        this.charCenterOffset = disFrame + 24 + (this.getWidth() - disFrame - 24) / 2;
        this.iconTexture = ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "textures/mental_trait/" + id + ".png");
    }

    private static WidgetSprites createWidgetSprites(String id) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "textures/mental_trait/" + id + ".png");
        return new WidgetSprites(texture, texture, texture, texture);
    }

    public void onPressButton() {
        Minecraft.getInstance().setScreen(new MentalTraitInfoScreen(screen, id, this.iconTexture));
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        int screenY = (baseY - screen.getSliderValue());
        if (screenY + height > 0 && screenY < screen.screenHeight) {
            setX(screen.baseX);
            setY(screen.baseY + screenY);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();

            if (screenY < 0) {
                if (height + screenY > 1) {
                    guiGraphics.blitSprite(WIDGETS_LOCATION, this.getX(), screen.baseY, this.getWidth(), height + screenY);
                }

                if (screenY + disFrame + 24 > 0) {
                    int frameStart = screenY + disFrame < 0 ? -screenY - disFrame : 0;
                    renderTexture(guiGraphics, FRAME_LOCATION, this.getX() + disFrame, Math.max(this.getY() + disFrame, screen.baseY), 0,
                            frameStart, 24, 24 - frameStart, 24, 24);

                    if (screenY + dis + 16 > 0) {
                        int textureStart = screenY + dis < 0 ? -screenY - dis : 0;
                        renderTexture(guiGraphics, this.iconTexture, this.getX() + dis, Math.max(this.getY() + dis, screen.baseY), 0,
                                textureStart, 16, 16 - textureStart, 16, 16);

                        if (screenY + (height / 2) + charHalfSize > 0) {
                            guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("depression.mental_trait." + id), this.getX() + charCenterOffset, this.getY() + (height / 2) - charHalfSize, 0xFFFFFF);
                        }
                    }
                }
            } else if (screenY + height > screen.screenHeight) {
                if (screen.screenHeight - screenY > 1) {
                    guiGraphics.blitSprite(WIDGETS_LOCATION, this.getX(), screen.baseY + screenY, this.getWidth(), screen.screenHeight - screenY);
                }

                if (screenY + disFrame < screen.screenHeight) {
                    renderTexture(guiGraphics, FRAME_LOCATION, this.getX() + disFrame, this.getY() + disFrame, 0,
                            0, 24, Math.min(24, screen.screenHeight - screenY - disFrame), 24, 24);

                    if (screenY + dis < screen.screenHeight) {
                        renderTexture(guiGraphics, this.iconTexture, this.getX() + dis, this.getY() + dis, 0,
                                0, 16, Math.min(16, screen.screenHeight - screenY - dis), 16, 16);

                        if (screenY + (height / 2) - charHalfSize < screen.screenHeight) {
                            guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("depression.mental_trait." + id), this.getX() + charCenterOffset, this.getY() + (height / 2) - charHalfSize, 0xFFFFFF);
                        }
                    }
                }
            } else {
                guiGraphics.blitSprite(WIDGETS_LOCATION, this.getX(), screen.baseY + screenY, this.getWidth(), height);
                renderTexture(guiGraphics, FRAME_LOCATION, this.getX() + disFrame, this.getY() + disFrame, 0, 0, 24, 24, 24, 24);
                renderTexture(guiGraphics, this.iconTexture, this.getX() + dis, this.getY() + dis, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("depression.mental_trait." + id), this.getX() + charCenterOffset, this.getY() + (height / 2) - charHalfSize, 0xFFFFFF);
            }
        }
    }

    private void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int texX, int texY, int width, int height, int texWidth, int texHeight) {
        guiGraphics.blit(texture, x, y, texX, texY, width, height, texWidth, texHeight);
    }
}