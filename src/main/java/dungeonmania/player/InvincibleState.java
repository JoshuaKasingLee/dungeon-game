package dungeonmania.player;

import dungeonmania.moving_entities.Enemy;

public class InvincibleState implements PlayerState {
    private Player player;
    private String type;
    private int timeLeft;
    public static final int INVINCIBLE_TIME_LIMIT = 10; // 10 ticks

    public InvincibleState(Player player) {
        this.player = player;
        this.timeLeft = INVINCIBLE_TIME_LIMIT;
        this.type = "Invincible";
    }

    public InvincibleState(Player player, int timeLeft) {
        this.player = player;
        this.timeLeft = timeLeft;
        this.type = "Invincible";
    }

    /** 
     * enemy automatically loses if battling invincible player
     * @param enemy
     */
    public void battleEnemy(Enemy enemy) {
        enemy.setHealth(0);
    }

    /** 
     * updates the timer of how long player has left in state
     */
    public void updateState() {
        timeLeft--;
        if (timeLeft <= 0) {
            player.setCharacterState(new StandardState(player));
        }
    }

    // basic getters and setters
    
    /** 
     * @return String
     */
    public String getType() {
        return type;
    }
   
    /**
     * @return int return the timeLeft
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * @param timeLeft the timeLeft to set
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

}
