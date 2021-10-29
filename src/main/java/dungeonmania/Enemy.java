package dungeonmania;


import dungeonmania.util.Position;
import java.util.Random;

public abstract class Enemy extends MovingEntity {
    private boolean ally;
    private int armour; // num hits left

    public Enemy(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.ally = false;
        this.armour = 0;
    }

    public void updateHealth(MovingEntity other) {
        if (this.armour > 0) {
            int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 10 );
            this.setHealth(newHealth);
            this.armour = armour - 1;
        } else {
            System.out.println("hello");
            int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 5 );
            this.setHealth(newHealth);
        } 
    }


    public void setDurability(int durability) {
        armour = durability;  
    }

    public boolean isAlly() {
        return ally;
    }

    /**
     * @param ally the ally to set
     */
    public void setAlly(boolean ally) {
        this.ally = ally;
    }

    public int getArmour() {
        return armour;
    }
    
    /** 
     * given a percentage chance the enemy should have armour, randomly assign armour
     * @param percentage
     * @return boolean
     */
    public void setArmour(int percentage) {
        Random rand = new Random();
        int randN = rand.nextInt(100); // get number between 0 and 99
        if (randN < percentage) {
            this.armour = Armour.DURABILITY;
        }
    }

    // for testing
    public void giveArmour(int numArmour) {
        this.armour = numArmour;
    }

    public abstract void updatePosition();

    public static void main(String[] args) {   
    }  

}
