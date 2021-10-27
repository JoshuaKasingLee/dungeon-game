package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final int ORIGINAL_HEALTH = 7;
    public static final int ZOMBIE_TOAST_ATTACK_DAMAGE = 3;

    public ZombieToast(Position position, String id) {
        super(position, id, "Zombie");
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(ZOMBIE_TOAST_ATTACK_DAMAGE);
    }

    public void updatePosition() {
    }
    
}
