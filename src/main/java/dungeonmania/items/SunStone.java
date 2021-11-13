package dungeonmania.items;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;


public class SunStone extends Item {

    public SunStone(Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "sun_stone";
    }
    
}
