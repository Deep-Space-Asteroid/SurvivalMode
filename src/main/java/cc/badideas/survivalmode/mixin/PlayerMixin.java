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
    private double oxygenLossRate = 0.01; // Per update (updates were supposed to be fixed-timestep but they aren't, and he removed deltaTime)
    @Unique
    private int health;
    @Unique
    private int maxHealth = 100;
    @Unique
    private float deathOffsetStart = 1.8F;
    @Unique
    private float deathOffset;
    @Unique
    private boolean isDead = false;
    @Unique
    private long lastDamageTimeMS = 0;
    @Unique
    private GameMode gameMode = GameMode.SURVIVAL;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void survivalModeTick(World world, CallbackInfo info) {
        // TODO: Get deltaTime back somehow :(
        // I want to avoid having to use System.getTimeMillis or whatever
        // since this function is called every frame as of CR 0.1.15
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
            takeDamage(5);
        }
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void resetSurvivalModePlayer(CallbackInfo info) {
        isDead = false;
        deathOffset = deathOffsetStart;
        oxygen = maxOxygen;
        health = maxHealth;
    }

    private void die() {
        isDead = true;
        deathOffset = deathOffsetStart;
        boolean cursorCaught = Gdx.input.isCursorCatched();
        Gdx.input.setCursorCatched(false);
        GameState.switchToGameState(new DeathScreen(cursorCaught));
    }

    public double getOxygen() {
        return oxygen;
    }

    public double getMaxOxygen()
    {
        return maxOxygen;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int damage) {
        if (System.currentTimeMillis() - lastDamageTimeMS < 1000) {
            return;
        }

        lastDamageTimeMS = System.currentTimeMillis();
        health -= damage;

        if (health <= 0) {
            health = 0;
            die();
        }
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
