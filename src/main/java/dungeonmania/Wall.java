package dungeonmania;

import dungeonmania.util.Position;

public class Wall extends Entity {

    public Wall(Position position, String id, Dungeon dungeon) {
        super(position, id, "Wall", dungeon);
    }
    
}
