package dungeonmania;

import dungeonmania.util.Position;

public class Sword extends Item {
    
    // assume a sword can instantly destroy an enemy
    public Sword(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setUsesLeft(3);
        dungeon.addEntity(this);
    }

    @Override
    public String setType() {
        return "Sword";
    }
}
