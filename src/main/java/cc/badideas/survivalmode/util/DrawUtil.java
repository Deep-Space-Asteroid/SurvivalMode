package cc.badideas.survivalmode.util;

import com.badlogic.gdx.Gdx;

public class DrawUtil {
    public static void switchToDrawingText() {
        Gdx.gl.glClear(256);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
    }

    public static void switchToDrawingShapes() {
        Gdx.gl.glClear(256);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glDepthFunc(519);
        Gdx.gl.glBlendFunc(770, 771);
        Gdx.gl.glCullFace(1028);
    }
}
