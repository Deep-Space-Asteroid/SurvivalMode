package cc.badideas.survivalmode.api;

import finalforeach.cosmicreach.world.World;

public interface IESMPlayer {
    public void respawn(World world);
    public double getOxygen();
    public double getMaxOxygen();
    public boolean isDead();
    public GameMode getGameMode();
}
