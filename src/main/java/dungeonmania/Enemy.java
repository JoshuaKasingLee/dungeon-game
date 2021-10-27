package dungeonmania;


import dungeonmania.util.Position;
import java.util.Random;

public abstract class Enemy extends MovingEntity {
    private boolean ally;
    private int armour; // num hits left

    public Enemy(Position position, String id, String type) {
        super(position, id, type);
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


    public boolean isAlly() {
        return this.ally;
    }

    /**
     * @param ally the ally to set
     */
    public void setAlly(boolean ally) {
        this.ally = ally;
    }

    public int getArmour() {
        return this.armour;
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
        // ZombieToast zombie = new ZombieToast(new Position(0, 0), "Holly");
        // Mercenary merc = new Mercenary(new Position(0, 0), "Molly");
        // zombie.giveArmour(0);
        // merc.giveArmour(0);
        // System.out.println(zombie.getArmour());
        // System.out.println(merc.getArmour());
        // Random rand = new Random();
        // int randN = rand.nextInt(100);
        // System.out.println(randN);
        // if (randN < 0) {
        //     System.out.println("hello");
        // }
    }



    


}
