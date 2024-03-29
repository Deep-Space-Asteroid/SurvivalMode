package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.api.GameMode;
import cc.badideas.survivalmode.api.IESMPlayer;
import cc.badideas.survivalmode.util.DrawUtil;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UI.class)
public class UIMixin {
    @Shadow
    public static SpriteBatch batch;
    @Shadow
    private Viewport uiViewport;
    @Shadow
    OrthographicCamera uiCamera;
    @Shadow
    ShapeRenderer shapeRenderer;

    int barMaxWidth = 200;
    int barPadding = 5;
    int barHeight = 40;

    @Inject(method = "render", at = @At("HEAD"))
    private void renderOxygen(CallbackInfo ci) {
        if (UI.renderUI) {
            DrawUtil.switchToDrawingShapes();
            uiViewport.apply(false);

            IESMPlayer player = (IESMPlayer) InGame.getLocalPlayer();
            float screenW = uiViewport.getWorldWidth();
            float screenH = uiViewport.getWorldHeight();
            int screenXOffset = Math.round(screenW / 2);
            int screenYOffset = Math.round(screenH / 2);
            double curOxygen = player.getOxygen();
            double maxOxygen = player.getMaxOxygen();
            double innerOxygenBarWidth = curOxygen / maxOxygen * (barMaxWidth - barPadding * 2);
            int health = player.getHealth();
            int maxHealth = player.getMaxHealth();
            double innerHealthBarWidth = (health / (double) maxHealth) * (barMaxWidth - barPadding * 2);

            if (player.getGameMode() == GameMode.SURVIVAL) {
                shapeRenderer.setProjectionMatrix(uiCamera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                // Draw oxygen bar
                shapeRenderer.setColor(1.0F, 1.0F, 1.0F, 0.5F);
                shapeRenderer.rect(-screenXOffset, screenYOffset - barHeight * 2 - barPadding, barMaxWidth, barHeight);
                shapeRenderer.rect(-screenXOffset + barPadding, screenYOffset - barHeight * 2, Math.round(innerOxygenBarWidth), barHeight - barPadding * 2);
                // Draw health bar
                shapeRenderer.setColor(1.0F, 0.0F, 0.0F, 0.5F);
                shapeRenderer.rect(-screenXOffset, screenYOffset - barHeight, barMaxWidth, barHeight);
                shapeRenderer.setColor(0.0F, 1.0F, 0.0F, 0.5F);
                shapeRenderer.rect(-screenXOffset + barPadding, screenYOffset - barHeight + barPadding, Math.round(innerHealthBarWidth), barHeight - barPadding * 2);
            }

            boolean dead = player.isDead();
            if (dead) {
                shapeRenderer.setColor(1.0F, 0.0F, 0.0F, 0.5F);
                shapeRenderer.rect(-screenXOffset, -screenYOffset, screenW, screenH);
            }

            shapeRenderer.end();

            DrawUtil.switchToDrawingText();
            uiViewport.apply(false);

            batch.setProjectionMatrix(uiCamera.combined);
            batch.begin();
            FontRenderer.fontTexture.bind(0);
            batch.setColor(1, 1, 1, 1);

            if (dead) {
                Vector2 textDim = new Vector2();
                FontRenderer.getTextDimensions(uiViewport, "You are dead.", textDim);
                FontRenderer.drawText(batch, uiViewport, "You are dead.", 0 - textDim.x / 2, -100);
            }

            if (player.getGameMode() == GameMode.SURVIVAL) {
                batch.setColor(0, 0, 0, 1);
                FontRenderer.drawText(batch, uiViewport, String.format("%.1f/%.1f", Math.max(0, curOxygen), maxOxygen), -screenXOffset, screenYOffset - barHeight);
            }

            batch.end();
        }
    }
}
