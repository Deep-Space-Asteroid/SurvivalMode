package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.SurvivalMode;
import cc.badideas.survivalmode.events.BlockEventActionPressurize;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.world.blockevents.BlockEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockGame.class, priority = 999)
public class BlockGameMixin {
    @Inject(method = "create", at = @At("TAIL"))
    private void registerSurvivalModeBlocks(CallbackInfo ci) {
        BlockEvents.registerBlockEventAction(new BlockEventActionPressurize());

        for (String block: SurvivalMode.BLOCKS) {
            BlockBuilderUtils.getBlockFromJson(new Identifier(SurvivalMode.MOD_ID, block));
        }
    }
}
