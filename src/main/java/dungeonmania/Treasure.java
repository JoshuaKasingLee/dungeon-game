package dungeonmania;

import dungeonmania.util.Position;

public class Treasure extends Item {

    public Treasure (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }
 
    @Override
    public String setType() {
        return "Treasure";
    }
}
