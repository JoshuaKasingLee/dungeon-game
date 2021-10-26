package dungeonmania;

public class StandardState implements CharacterState {
    private Character character;
    private String type;

    public StandardState(Character character) {
        this.character = character;
        this.type = "Standard";
    }


    public void battleEnemy(Enemy enemy) {
    }

    public void updateState() {
        // remain in standard state
    }

    public String getType() {
        return type;
    }


}
