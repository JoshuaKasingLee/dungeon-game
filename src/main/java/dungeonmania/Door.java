package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Door extends StaticEntity {
    private boolean locked = true;
    private int key;
    
    public Door(Position position, Dungeon dungeon, int key) {
        super(position, dungeon);
        this.key = key;
    }

    @Override
    public String setType() {
        return "Door";
    }

    @Override
    public void update(Direction direction) {
        if (this.getPosition().equals(this.getPlayerPosition())) {
            this.locked = false;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public int getKey() {
        return this.key;
    }
}
