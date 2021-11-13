package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Treasure extends Item {
    public Treasure (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }
 
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "treasure";
    }
}
