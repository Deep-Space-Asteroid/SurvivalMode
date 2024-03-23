package cc.badideas.survivalmode.mixin;

import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGame.class)
public interface InGameAccessor {
    @Accessor(value = "ui")
    public UI getUI();
}
