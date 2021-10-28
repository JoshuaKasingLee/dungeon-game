package dungeonmania;

public abstract class Gamemode {
    private boolean battle;
    private int spawnTimer;
    private int startingHealth;

    public Gamemode() {
        this.battle = findBattle();
        this.spawnTimer = findSpawnTimer();
        this.startingHealth = findStartingHealth();
    }


    public abstract boolean findBattle();
    public abstract int findSpawnTimer();
    public abstract int findStartingHealth();

    /**
     * @return boolean return the battle
     */
    public boolean isBattle() {
        return battle;
    }

    /**
     * @param battle the battle to set
     */
    public void setBattle(boolean battle) {
        this.battle = battle;
    }

    /**
     * @return int return the spawnTimer
     */
    public int getSpawnTimer() {
        return spawnTimer;
    }

    /**
     * @param spawnTimer the spawnTimer to set
     */
    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    /**
     * @return int return the startingHealth
     */
    public int getStartingHealth() {
        return startingHealth;
    }

    /**
     * @param startingHealth the startingHealth to set
     */
    public void setStartingHealth(int startingHealth) {
        this.startingHealth = startingHealth;
    }

}
