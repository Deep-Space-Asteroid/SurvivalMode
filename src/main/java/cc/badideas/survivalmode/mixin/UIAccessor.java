package cc.badideas.survivalmode.mixin;

import com.badlogic.gdx.graphics.OrthographicCamera;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(UI.class)
public interface UIAccessor {
    @Accessor(value = "uiCamera")
    public OrthographicCamera getCamera();
}
