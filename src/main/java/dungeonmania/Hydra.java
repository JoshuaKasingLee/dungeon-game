package dungeonmania;

import dungeonmania.util.Position;
import java.util.Random;

public class Hydra extends Enemy {
    public static final int ORIGINAL_HEALTH = 7;
    public static final int HYDRA_ATTACK_DAMAGE = 4;

    public Hydra(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(HYDRA_ATTACK_DAMAGE);
    }

    @Override
    public void updateHealth(MovingEntity other) {
    }

    public void updatePosition() {
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "hydra";
    }

    
}
