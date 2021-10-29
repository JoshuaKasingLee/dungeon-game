package dungeonmania;

public abstract class Gamemode {
    private boolean battle;
    private int spawnTimer;
    private int startingHealth;

    public Gamemode(boolean battle, int spawnTimer, int startingHealth) {
        this.battle = battle;
        this.spawnTimer = spawnTimer;
        this.startingHealth = startingHealth;        
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
}
