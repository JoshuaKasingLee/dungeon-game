package dungeonmania;

import dungeonmania.util.Position;


public class ZombieToast extends Enemy {
    public static final int ORIGINAL_HEALTH = 7;
    public static final int ZOMBIE_TOAST_ATTACK_DAMAGE = 3;

    public ZombieToast(Position position, String id, Dungeon dungeon) {
        super(position, id, "Zombie", dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(ZOMBIE_TOAST_ATTACK_DAMAGE);
        this.setArmour(25); // assume zombie has 25% chance spawning with armour
    }

    public void updatePosition() {
    }
    
}
