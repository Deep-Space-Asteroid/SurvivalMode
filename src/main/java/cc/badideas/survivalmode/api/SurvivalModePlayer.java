package cc.badideas.survivalmode.api;

import finalforeach.cosmicreach.world.World;

public interface SurvivalModePlayer {
    public void respawn(World world);
    public double getOxygen();
    public double getMaxOxygen();
    public boolean isDead();
}
