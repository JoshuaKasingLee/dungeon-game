package dungeonmania;

import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    public static final int ORIGINAL_HEALTH = 15;
    public static final int ASSASSIN_ATTACK_DAMAGE = 6;

    public Assassin(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(ASSASSIN_ATTACK_DAMAGE);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "assassin";
    }
    
}
