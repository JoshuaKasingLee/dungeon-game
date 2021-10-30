package dungeonmania;

import dungeonmania.util.Position;

public class Arrow extends Item {

    public Arrow (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }
    
    @Override
    public String setType() {
        return "Arrow";
    }
}
