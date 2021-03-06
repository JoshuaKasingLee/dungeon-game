package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Sword extends Item {
    public static final int ATTACK_DAMAGE = 10;

    public Sword(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setUsesLeft(3);
        dungeon.addEntity(this);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "sword";
    }
}
