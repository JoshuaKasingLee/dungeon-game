package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public abstract class MovingEntity extends Entity {
    private int health;
    private int attackDamage;
    // private Dungeon dungeon;

    public MovingEntity(Position position, String id, String type) {
        super(position, id, type);
        this.health = 5;
        this.attackDamage = 1;
    }

    // basic getters and setters
    
    /**
     * @return int return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return int return the attackDamage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * @param attackDamage the attackDamage to set
     */
    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
}
