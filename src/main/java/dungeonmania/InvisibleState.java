package dungeonmania;

public class InvisibleState implements CharacterState {
    private Player player;
    private int timeLeft;
    private String type;
    public static final int INVISIBLE_TIME_LIMIT = 10;

    public InvisibleState(Player player) {
        this.player = player;
        this.timeLeft = INVISIBLE_TIME_LIMIT;
        this.type = "Invisible";
    }

    public InvisibleState(Player player, int timeLeft) {
        this.player = player;
        this.timeLeft = timeLeft;
        this.type = "Invisible";
    }

    /** 
     * battles do not occur in invisible state
     * @param enemy
     */
    public void battleEnemy(Enemy enemy) {
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
}
