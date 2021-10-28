package dungeonmania;

import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    public static final int ORIGINAL_HEALTH = 10;
    public static final int MERCENARY_ATTACK_DAMAGE = 4;

    public Mercenary(Position position, String id, Dungeon dungeon) {
        super(position, id, "Mercenary", dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(MERCENARY_ATTACK_DAMAGE);
        this.setArmour(50); // assume zombie has 50% chance spawning with armour
        setIsInteractable(true);
    }

    public void updatePosition() {   
    }
    
}
