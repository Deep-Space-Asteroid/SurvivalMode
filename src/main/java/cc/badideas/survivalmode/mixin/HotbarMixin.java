package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.api.SurvivalModePlayer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.ui.Hotbar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hotbar.class)
public class HotbarMixin {
    int oxygenBarMaxWidth = 200;
    int oxygenBarPadding = 5;
    int oxygenBarHeight = 40;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderOxygenBar(Viewport uiViewport, ShapeRenderer shapeRenderer, CallbackInfo info) {
        //Matrix4 projMatrix = ((UIAccessor) ((InGameAccessor) GameState.IN_GAME).getUI()).getCamera().combined;

        int screenXOffset = Math.round(uiViewport.getWorldWidth() / 2);
        int screenYOffset = Math.round(uiViewport.getWorldHeight() / 2);
        double curOxygen = ((SurvivalModePlayer)InGame.getLocalPlayer()).getOxygen();
        double maxOxygen = ((SurvivalModePlayer)InGame.getLocalPlayer()).getMaxOxygen();
        double innerBarWidth = curOxygen / maxOxygen * (oxygenBarMaxWidth - oxygenBarPadding * 2);
        shapeRenderer.setColor(1.0F, 1.0F, 1.0F, 0.5F);
        shapeRenderer.rect(-screenXOffset, screenYOffset - oxygenBarHeight, oxygenBarMaxWidth, oxygenBarHeight);
        shapeRenderer.rect(-screenXOffset + oxygenBarPadding, screenYOffset - oxygenBarHeight + oxygenBarPadding, Math.round(innerBarWidth), oxygenBarHeight - oxygenBarPadding * 2);
        /*UI.batch.setProjectionMatrix(projMatrix);
        UI.batch.begin();
        FontRenderer.fontTexture.bind(0);
        FontRenderer.drawText(UI.batch, uiViewport, String.format("%d/%d", Math.round(curOxygen), Math.round(maxOxygen)), -screenXOffset + oxygenBarPadding, screenYOffset + oxygenBarPadding);
        UI.batch.end();*/
    }
}
