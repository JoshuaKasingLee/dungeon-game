package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Spider extends Enemy {
    public static final int ORIGINAL_HEALTH = 5;
    public static final int SPIDER_ATTACK_DAMAGE = 2;

    public Spider(Position position, String id, Dungeon dungeon) {
        super(position, id, "Spider", dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(SPIDER_ATTACK_DAMAGE);
        // need to consider edge case where spider movement goes off the map - what happens?
    }

    public void updatePosition() {
    }
}
