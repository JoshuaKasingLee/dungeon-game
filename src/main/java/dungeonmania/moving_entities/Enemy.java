package dungeonmania.moving_entities;


import dungeonmania.Dungeon;
import dungeonmania.items.Armour;
import dungeonmania.util.Position;
import java.util.Random;

public abstract class Enemy extends MovingEntity {
    private boolean ally;
    private int armour; // indicates num uses left

    public Enemy(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.ally = false;
        this.armour = 0;
    }
    
    /** 
     * updates health of enemy in 1 round of battle with input entity
     * @param other
     */
    public void updateHealth(MovingEntity other) {
        if (this.armour > 0) {
            int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 10 );
            this.setHealth(newHealth);
            this.armour = armour - 1;
        } else {
            int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 5 );
            this.setHealth(newHealth);
        } 
    }

    /** 
     * given a percentage chance the enemy should have armour, randomly sets armour
     * @param percentage
     * @return boolean
     */
    public void setArmour(int percentage) {
        Random rand = new Random();
        int randN = rand.nextInt(100);
        if (randN < percentage) {
            this.armour = Armour.DURABILITY;
        }
    }

    // basic getters and setters
    
    /** 
     * @return boolean
     */
    public boolean isAlly() {
        return ally;
    }

    /**
     * @param ally the ally to set
     */
    public void setAlly(boolean ally) {
        this.ally = ally;
    }

    /** 
     * @return int
     */
    public int getArmour() {
        return armour;
    }
    
    /** 
     * @param numArmour
     */
    public void giveArmour(int numArmour) {
        this.armour = numArmour;
    }

    // abstract functions

    public abstract void updatePosition();

}
