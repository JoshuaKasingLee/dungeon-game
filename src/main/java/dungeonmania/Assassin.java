package dungeonmania;

import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    public static final int ORIGINAL_HEALTH = 20;
    public static final int ASSASSIN_ATTACK_DAMAGE = 6;

    public Assassin(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(ASSASSIN_ATTACK_DAMAGE);
    }

    public Assassin(Position position, Dungeon dungeon, int durability, boolean isAlly) {
        this(position, dungeon);
        giveArmour(durability);
        setAlly(isAlly);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "assassin";
    }
    
}
