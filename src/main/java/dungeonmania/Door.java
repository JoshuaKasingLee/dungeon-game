package dungeonmania;

import dungeonmania.util.Position;

public class Door extends Entity {
    private int key;

    public Door(Position position, String id, Dungeon dungeon, int key) {
        super(position, id, "Door", dungeon);
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
    
    
}
