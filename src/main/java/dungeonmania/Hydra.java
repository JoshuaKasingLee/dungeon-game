package dungeonmania;

import dungeonmania.util.Position;
import java.util.Random;

public class Hydra extends Enemy {
    public static final int ORIGINAL_HEALTH = 15;
    public static final int HYDRA_ATTACK_DAMAGE = 4;

    public Hydra(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(HYDRA_ATTACK_DAMAGE);
    }

    public Hydra(Position position, Dungeon dungeon, int durability) {
        this(position, dungeon);
        giveArmour(durability);
    }

    /** 
     * updates health of hydra in 1 round of battle with input entity
     * @param other
     */
    @Override
    public void updateHealth(MovingEntity other) {
        if (attackSuccess()) {
            int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 5 );
            this.setHealth(newHealth);
        } else {
            int newHealth = getHealth() + ((other.getHealth() * other.getAttackDamage()) / 5 );
            this.setHealth(newHealth);
        }
    }

    /** 
     * generates a true or false with equal chance
     * @param percentage
     * @return boolean
     */
    public boolean attackSuccess() {
        Random rand = new Random();
        int randN = rand.nextInt(2);
        if (randN == 1) {
            return true;
        }
        return false;
    }


    /** 
     * updates hydra's position for 1 tick
     * moves hydra in random valid direction
     */
    @Override
    public void updatePosition() {
        Random rand = new Random();
        int randN = rand.nextInt();
        if (randN % 4 == 0) {
            moveUp();
        } else if (randN % 4 == 1) {
            moveDown();
        } else if (randN % 4 == 2) {
            moveLeft();
        } else if (randN % 4 == 3) {
            moveRight();
        }
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "hydra";
    }
}
