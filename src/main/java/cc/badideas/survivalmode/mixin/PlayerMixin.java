package cc.badideas.survivalmode.mixin;

import cc.badideas.survivalmode.api.GameMode;
import cc.badideas.survivalmode.api.IESMPlayer;
import cc.badideas.survivalmode.gamestates.DeathScreen;
import com.badlogic.gdx.Gdx;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.entities.Entity;
import finalforeach.cosmicreach.world.entities.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements IESMPlayer {
    @Shadow
    private Entity controlledEntity;

    @Unique
    private double oxygen;

    @Unique
    private double maxOxygen = 100.0;

    @Unique
    private double oxygenLossRate = 0.01; // Per update (updates are fixed-timestep)

    @Unique
    private float deathOffsetStart = 1.8F;

    @Unique
    private float deathOffset;

    @Unique
    private boolean isDead = false;

    @Unique
    private GameMode gameMode = GameMode.SURVIVAL;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void survivalModeTick(World world, CallbackInfo info) {
        if (isDead) {
            controlledEntity.viewPositionOffset.set(0, deathOffset, 0);
            deathOffset = Math.max(0.2F, deathOffset - 0.025F);

            if (!(GameState.currentGameState instanceof DeathScreen)) {
                boolean cursorCaught = Gdx.input.isCursorCatched();
                Gdx.input.setCursorCatched(false);
                GameState.switchToGameState(new DeathScreen(cursorCaught));
            }

            info.cancel();
            return;
        }

        if (gameMode == GameMode.CREATIVE) {
            return;
        }

        if (oxygen > maxOxygen) {
            oxygen = maxOxygen;
        }

        oxygen -= oxygenLossRate;

        if (oxygen <= 0.0) {
            isDead = true;
            deathOffset = deathOffsetStart;
            boolean cursorCaught = Gdx.input.isCursorCatched();
            Gdx.input.setCursorCatched(false);
            GameState.switchToGameState(new DeathScreen(cursorCaught));
        }
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void resetSurvivalModePlayer(CallbackInfo info) {
        isDead = false;
        deathOffset = deathOffsetStart;
        oxygen = maxOxygen;
    }

    public double getOxygen() {
        return oxygen;
    }

    public double getMaxOxygen()
    {
        return maxOxygen;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
