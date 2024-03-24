package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.api.SurvivalModePlayer;
import cc.badideas.survivalmode.util.DrawUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.items.ItemSlot;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.Hotbar;
import finalforeach.cosmicreach.ui.UI;
import finalforeach.cosmicreach.world.blocks.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hotbar.class)
public class HotbarMixin {
    @Inject(method = "pickBlock", at = @At("HEAD"), cancellable = true)
    private void pickBlockInject(BlockState blockState, CallbackInfo info) {
        if (!((SurvivalModePlayer) InGame.getLocalPlayer()).isCreative()) {
            info.cancel();
        }
    }

    @Inject(method = "drawItems", at = @At("TAIL"))
    private void drawItemsInject(Viewport uiViewport, CallbackInfo info) {
        float screenBottom = uiViewport.getWorldHeight() / 2.0F;
        int numSlots = UI.hotbar.getNumSlots();
        float s = 32.0F;
        float padding = 2.0F;
        float startX = -((s + padding) * (float)numSlots / 2.0F);

        DrawUtil.switchToDrawingText();
        uiViewport.apply(false);

        UI.batch.setProjectionMatrix(((UIAccessor) ((InGameAccessor) GameState.IN_GAME).getUI()).getCamera().combined);
        UI.batch.begin();
        UI.batch.setColor(1, 1, 1, 1);
        FontRenderer.fontTexture.bind(0);

        for (int i = 0; i < numSlots; i++) {
            ItemSlot slot = UI.hotbar.getSlot(i);

            if (slot.itemStack != null && slot.itemStack.amount > 1) {
                float rX = startX + (float)i * (s + padding);
                float rY = screenBottom - s - padding;
                FontRenderer.drawText(UI.batch, uiViewport, String.format("%d", slot.itemStack.amount), rX, rY);
            }
        }

        UI.batch.end();

        Gdx.gl.glClear(256);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
    }
}
