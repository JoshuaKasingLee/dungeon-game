package dungeonmania;

public class InvincibleState implements CharacterState {
    private Player character;
    private int time;
    private String type;
    public static final int INVINCIBLE_TIME_LIMIT = 10; // 10 ticks


    public InvincibleState(Player character) {
        this.character = character;
        this.time = 0;
        this.type = "Invincible";
    }

    // assume that since they are already invincible, no need to use armour/shields in this state
    // assume character's health cannot decrease (spec doesn't explicitly say this)
    public void battleEnemy(Enemy enemy) {
        enemy.setHealth(0);
        // remove enemy
    }

    public void updateState() {
        this.time = time + 1;
        if (time >= INVINCIBLE_TIME_LIMIT) {
            character.setCharacterState(new StandardState(character));
        }
    }

    public String getType() {
        return type;
    }
    
}
