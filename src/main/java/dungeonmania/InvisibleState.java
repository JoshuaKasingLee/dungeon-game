package dungeonmania;

public class InvisibleState implements CharacterState {
    private Player character;
    // private int time;
    private int timeLeft;
    private String type;

    public static final int INVISIBLE_TIME_LIMIT = 10; // 10 ticks

    public InvisibleState(Player character) {
        this.character = character;
        this.timeLeft = INVISIBLE_TIME_LIMIT;
        this.type = "Invisible";
    }

    public InvisibleState(Player character, int timeLeft) {
        this.character = character;
        this.timeLeft = timeLeft;
        this.type = "Invisible";
    }

    public void battleEnemy(Enemy enemy) {
    }

    public void updateState() {
        // this.time = time + 1;
        timeLeft--;
        if (timeLeft <= 0) {
            character.setCharacterState(new StandardState(character));
        }
    }

    public String getType() {
        return type;
    }
    
}
