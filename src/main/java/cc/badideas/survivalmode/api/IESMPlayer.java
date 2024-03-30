package cc.badideas.survivalmode.api;

import finalforeach.cosmicreach.world.World;

public interface IESMPlayer {
    public void respawn(World world);
    public double getOxygen();
    public double getMaxOxygen();
    public int getHealth();
    public int getMaxHealth();
    public void takeDamage(int damage);
    public long getTimeSinceLastDamageMS();
    public boolean isDead();
    public GameMode getGameMode();
}
