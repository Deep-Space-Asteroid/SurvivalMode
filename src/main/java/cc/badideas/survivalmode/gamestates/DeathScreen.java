package cc.badideas.survivalmode.gamestates;

import cc.badideas.survivalmode.mixin.InGameAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.io.ChunkSaver;
import finalforeach.cosmicreach.ui.UIElement;

public class DeathScreen extends GameState {
    private boolean cursorCaught;
    private boolean firstFrame;

    public DeathScreen(boolean cursorCaught) {
        this.cursorCaught = cursorCaught;
    }

    public void create() {
        super.create();

        UIElement respawnButton = new UIElement(0.0F, 100.0F, 250.0F, 50.0F) {
            public void onClick() {
                super.onClick();
                switchToGameState(IN_GAME);
                InGame.getLocalPlayer().respawn(InGame.world);
            }
        };
        respawnButton.setText("Respawn");
        respawnButton.show();
        uiElements.add(respawnButton);
        UIElement quitButton = new UIElement(0.0F, 200.0F, 250.0F, 50.0F) {
            public void onClick() {
                super.onClick();
                ChunkSaver.saveWorld(InGame.world);
                GameState.IN_GAME.unloadWorld();
                GameState.switchToGameState(GameState.MAIN_MENU);
            }
        };
        quitButton.setText("Return To Main Menu");
        quitButton.show();
        uiElements.add(quitButton);

        firstFrame = true;
    }

    public void update() {
        super.update();
        //InGame.getLocalPlayer().updateCamera(InGameAccessor.getRawWorldCamera());
        IN_GAME.update();
        // TODO: Prevent interacting with the world on the death screen
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        IN_GAME.resize(width, height);
    }

    public void render() {
        super.render();
        if (!this.firstFrame && Gdx.input.isKeyJustPressed(111)) {
            switchToGameState(IN_GAME);
            InGame.getLocalPlayer().respawn(InGame.world);
        }

        ScreenUtils.clear(0.1F, 0.1F, 0.2F, 1.0F, true);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        IN_GAME.render();

        drawUIElements();
        firstFrame = false;
    }
}
