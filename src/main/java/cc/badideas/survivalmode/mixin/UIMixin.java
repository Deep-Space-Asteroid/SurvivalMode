package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.api.SurvivalModePlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    int oxygenBarMaxWidth = 200;
    int oxygenBarPadding = 5;
    int oxygenBarHeight = 40;

    @Inject(method = "render", at = @At("HEAD"))
    private void renderOxygen(CallbackInfo ci) {
        if (UI.renderUI) {
            Gdx.gl.glClear(256);
            Gdx.gl.glEnable(3042);
            Gdx.gl.glDepthFunc(519);
            Gdx.gl.glBlendFunc(770, 771);
            Gdx.gl.glCullFace(1028);
            uiViewport.apply(false);

            int screenXOffset = Math.round(uiViewport.getWorldWidth() / 2);
            int screenYOffset = Math.round(uiViewport.getWorldHeight() / 2);
            double curOxygen = ((SurvivalModePlayer) InGame.getLocalPlayer()).getOxygen();
            double maxOxygen = ((SurvivalModePlayer)InGame.getLocalPlayer()).getMaxOxygen();
            double innerBarWidth = curOxygen / maxOxygen * (oxygenBarMaxWidth - oxygenBarPadding * 2);
            shapeRenderer.setProjectionMatrix(uiCamera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            shapeRenderer.rect(-screenXOffset, screenYOffset - oxygenBarHeight, oxygenBarMaxWidth, oxygenBarHeight);
            shapeRenderer.rect(-screenXOffset + oxygenBarPadding, screenYOffset - oxygenBarHeight + oxygenBarPadding, Math.round(innerBarWidth), oxygenBarHeight - oxygenBarPadding * 2);
            shapeRenderer.end();

            Gdx.gl.glClear(256);
            Gdx.gl.glEnable(2929);
            Gdx.gl.glDepthFunc(513);
            Gdx.gl.glEnable(2884);
            Gdx.gl.glCullFace(1029);
            Gdx.gl.glEnable(3042);
            Gdx.gl.glBlendFunc(770, 771);
            uiViewport.apply(false);

            batch.setProjectionMatrix(uiCamera.combined);
            batch.begin();
            FontRenderer.fontTexture.bind(0);
            // TODO: Render text
            batch.end();
        }
    }
}
