package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Wood extends Item {
    public Wood (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "wood";
    }
}
