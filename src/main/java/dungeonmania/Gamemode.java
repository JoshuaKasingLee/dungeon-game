package dungeonmania;

public abstract class Gamemode {
    private boolean battle;
    private int spawnTimer;
    private int startingHealth;
    private boolean invincible;

    public Gamemode(boolean battle, int spawnTimer, int startingHealth, boolean invicible) {
        this.battle = battle;
        this.spawnTimer = spawnTimer;
        this.startingHealth = startingHealth;   
        this.invincible = invicible;     
    }

    public abstract String toString();

    /**
     * @return boolean return the battle
     */
    public boolean isBattle() {
        return battle;
    }

    /**
     * @return int return the spawnTimer
     */
    public int getSpawnTimer() {
        return spawnTimer;
    }

    /**
     * @return int return the startingHealth
     */
    public int getStartingHealth() {
        return startingHealth;
    }

    /**
     * @return boolean return the invincible
     */
    public boolean getInvincible() {
        return invincible;
    }

}
