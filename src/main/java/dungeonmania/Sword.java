package dungeonmania;

import dungeonmania.util.Position;

public class Sword extends Item {
    
    // assume a sword can instantly destroy an enemy
    public Sword (Position position, String id, Dungeon dungeon) {
        super(position, id, "Sword", dungeon);
        this.setUsesLeft(3);
    }
}
