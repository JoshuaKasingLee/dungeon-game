package dungeonmania;

public class InvisibleState implements CharacterState {
    private Character character;
    private int time;
    private String type;

    public static final int INVISIBLE_TIME_LIMIT = 10; // 10 ticks

    public InvisibleState(Character character) {
        this.character = character;
        this.time = 0;
        this.type = "Invisible";
    }

    public void battleEnemy(Enemy enemy) {
    }

    public void updateState() {
        this.time = time + 1;
        if (time >= INVISIBLE_TIME_LIMIT) {
            character.setCharacterState(new StandardState(character));
        }
    }

    public String getType() {
        return type;
    }
    
}
