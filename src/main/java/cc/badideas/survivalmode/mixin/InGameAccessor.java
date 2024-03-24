package cc.badideas.survivalmode.mixin;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGame.class)
public interface InGameAccessor {
    @Accessor(value = "ui")
    public UI getUI();

    @Accessor(value = "rawWorldCamera")
    public static PerspectiveCamera getRawWorldCamera() {
        throw new AssertionError();
    }
}
