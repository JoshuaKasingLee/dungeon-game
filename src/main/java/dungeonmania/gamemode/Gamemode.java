package dungeonmania.gamemode;

public abstract class Gamemode {
    private boolean battle;
    private int spawnTimer;
    private int startingHealth;
    private boolean invincible;
    private int hydraSpawnTimer = 0;

    public Gamemode(boolean battle, int spawnTimer, int startingHealth, boolean invincible) {
        this.battle = battle;
        this.spawnTimer = spawnTimer;
        this.startingHealth = startingHealth;   
        this.invincible = invincible;     
    }

    public Gamemode(boolean battle, int spawnTimer, int startingHealth, boolean invincible, int hydraSpawnTimer) {
        this(battle, spawnTimer, startingHealth, invincible);
        this.hydraSpawnTimer = hydraSpawnTimer;
    }

    
    /** 
     * @param isBattle(
     * @return String
     */
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


    /**
     * @return boolean return the invincible
     */
    public boolean isInvincible() {
        return invincible;
    }

    /**
     * @return int return the hydraSpawnTimer
     */
    public int getHydraSpawnTimer() {
        return hydraSpawnTimer;
    }


}
