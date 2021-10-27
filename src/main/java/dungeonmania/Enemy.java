package dungeonmania;


import dungeonmania.util.Position;

public abstract class Enemy extends MovingEntity {
    private boolean ally;

    public Enemy(Position position, String id, String type) {
        super(position, id, type);
        this.ally = false;
    }

    public void updateHealth(MovingEntity other) {
        int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 5 );
        this.setHealth(newHealth);
    }

    public boolean isAlly() {
        return this.ally;
    }

    public abstract void updatePosition();
    
}
