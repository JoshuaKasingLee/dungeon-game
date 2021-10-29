package dungeonmania;

public class InvincibleState implements CharacterState {
    private Player character;
    private String type;
    private int timeLeft;
    public static final int INVINCIBLE_TIME_LIMIT = 10; // 10 ticks


    public InvincibleState(Player character) {
        this.character = character;
        this.timeLeft = INVINCIBLE_TIME_LIMIT;
        this.type = "Invincible";
    }

    public InvincibleState(Player character, int timeLeft) {
        this.character = character;
        this.timeLeft = timeLeft;
        this.type = "Invincible";
    }

    // assume that since they are already invincible, no need to use armour/shields in this state
    // assume character's health cannot decrease (spec doesn't explicitly say this)
    public void battleEnemy(Enemy enemy) {
        enemy.setHealth(0);
        // remove enemy
    }

    public void updateState() {
        timeLeft--;
        if (timeLeft <= 0) {
            character.setCharacterState(new StandardState(character));
        }
    }

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
