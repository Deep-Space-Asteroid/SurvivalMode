package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.api.SurvivalModePlayer;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.entities.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements SurvivalModePlayer {
    @Unique
    private double oxygen;

    @Unique
    private double maxOxygen = 25.0;

    @Unique
    private double oxygenLossRate = 1.0;

    @Inject(method = "update", at = @At("HEAD"))
    private void tickOxygenDown(World world, double deltaTime, CallbackInfo info) {
        if (oxygen > maxOxygen) oxygen = maxOxygen;
        oxygen -= oxygenLossRate * deltaTime;
        if (oxygen <= 0.0) {
            this.respawn(world);
        }
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void resetOxygen(CallbackInfo info) {
        oxygen = maxOxygen;
    }

    public double getOxygen() {
        return oxygen;
    }

    public double getMaxOxygen()
    {
        return maxOxygen;
    }
}
