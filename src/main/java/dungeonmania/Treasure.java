package dungeonmania;

import dungeonmania.util.Position;

public class Treasure extends Item {

    public Treasure (Position position, String id, Dungeon dungeon) {
        super(position, id, "Treasure", dungeon);
    }
    
}
